# Proposal for a generic extensibility scheme

## Preliminary notes

### Genericity
As its title implies, this document describes an extensibility scheme
that is intended to be “generic“, meaning that it is _not_ tied to any
particular data model or application.

The scheme was designed with two specific applications in mind: the
[OME-Zarr imaging file format](https://ngff.openmicroscopy.org) and an
upcoming imaging metadata specification to be developed as part of the
[Imaging-PHD project](https://persistent-hardware-descriptor-project.github.io/imaging-phd/).

However, the scheme should be applicable to any other data model that
one wishes to make extensible with the same aims than those stated
[below](#goals).

### JSON vs YAML
The extensibility scheme proposed here is primarily intended to deal
with JSON-formatted data. However in this document, all examples will be
given in YAML, which is considered (at least by the author of those
lines) to be somewhat easier to read and write than JSON. The use of
YAML rather than JSON should not affect the extensibility scheme in any
meaningful way.

### Objects, models, schemas
This document will talk a lot about “objects”. In this context, an
object is basically a JSON (or YAML, see above) dictionary containing
pre-determined keys.

The keys that an object can contain are defined by the object’s “model”,
also referred to as the _class_ of the object. Those keys are
indistinctly called the _fields_, the _attributes_, or the _slots_ of
the class.

The word “model”, used alone, can also refer to the entire set of
classes that describe all the objects that can be found in a given JSON
document.

In the context of this document, a “schema” is a _formal_
representation, using some specialised _schema definition language_
(SDL), of a model or of a fragment of a model.

### LinkML
[LinkML](https://linkml.io/) is, among other things, a schema definition
language.

The extensibility scheme described in this document has been designed,
among other criteria, to be compatible with the use of LinkML as the
schema definition language to represent the model that one wants to be
extensible. Furthermore, some aspects of the scheme have been designed
to explicitly benefit from the use of LinkML, notably in that those
aspects would be easy to implement using LinkML – provided that your
programming language of choice has decent support for LinkML (which
unfortunately is not the case of many languages beyond Python, for now).

That being said, the extensibility scheme is _not_ dependent on LinkML.
Its principles stand on their own, and it should be possible to
implement it without ever using LinkML.


## Full example
The following example will be used throughout this document to
illustrate how the proposed extensibility scheme works. It is loosely
based on the OME model’s “Instrument” branch, that describes a
microscope.

```yaml
manufacturer: Zeiss
model: LSM-880
light_source:
  object_type: https://example.org/ome/ArcLightSource
  power: 45
  lamp_type: Hg
```

Here is what that example could look like, with an extension aiming
to describe the kind of microscopes that one could find a long time ago,
in a galaxy far, far away:

```yaml
manufacturer: Imperial Microscope Builders
model: K2SO
light_source:
  object_type: https://example.com/swm/KyberLightSource
  power: 900
  crystal_color: red
extensions:
  https://example.org/com/MicroscopeExtension:
    planet: Coruscant
extension_manifest:
  - id: https://example.com/swm/
    version: 1.0
    schemas:
      - type: LinkML
        url: https://schemas.example.com/swm/swm.yaml
      - type: JSONSchema
        url: https://schemas.example/com/swm/swm.json
    homepage: https://example.com/swm/
```

This example shows a single extension (`https://example.com/swm`, also
referred to in the rest of this document as “the SWM extension”) making
use of the two types of extension mechanisms supported by this scheme: a
_natural extension class_ (`https://example.com/swm/KyberLightSource`)
and a _class extension_ (`https://example.com/swm/`). Read on for
details.    


## Goals
This extensibility scheme aims to achieve the following three goals:

* guaranteeing runtime data interoperability,
* guaranteeing that independently developed extensions cannot interfere
  with each other;
* making extensions manageable.

### Runtime data interoperability
The scheme is designed to allow for runtime data interoperability across
implementations that support a different set of extensions (including the
empty set, i.e. an application that supports no extensions) of a same
“base” model.

Given a base model _M_ and two extensions _E1_ and _E2_ (which would
ideally all be expected to be represented as schemas in an appropriate
schema definition language, though strictly speaking this is not
necessary), we can define the following derived models:

* _M(E1)_, the base model extended with extension _E1_;
* _M(E2)_, the base model extended with extension _E2_;
* _M(E1, E2)_, the base model extended with both extensions.

The basic goal of “runtime data interoperability” is that an application
that is only aware of the base model _M_ can still read and manipulate
data conformant to either of the three derived models, while (1)
preserving any extended data it does not understand (the part of the
data that is conformant with one of the extensions), (2) recognising
the presence of extended data, and (3) offering the users some ways of
accessing (and possibly manipulating) the extended data.

Likewise, an application that is only aware of the _M(E1)_ model (base
model + extension _E1_) should still be able to read and manipulate data
that is conformant with all other models, under the same conditions.

This is of course not limited to only two extensions: the principle is
generalizable to an arbitrary number of extensions.

#### Awareness of an extension
To fully understand the goal of runtime data interoperability, it is
necessary to explain what is exactly meant by “an application aware of
an extension” or “aware of an extended model”.

In this scheme, we consider three “levels” of awareness:

* compile-time awareness;
* runtime awareness;
* unawareness.

**Compile-time awareness** of an extension (or of an extended model) is
when the developers of the application know about the extension during
the development of the application, and write code to explicitly deal
with data conformant with the extended model. Typically, this level of
awareness will not only allow to read and manipulate the extended data,
but also to offer specific features that explicitly make use of said
extended data.

When schemas (such as LinkML) are used to formally describe the base
model and its extension(s), compile-time awareness notably means that
developers can rely on any code generation feature enabled by the type
of schema used to automatically generate at least part of the code
needed to read and manipulate the (extended) data.

> Despite its name, the notion of “compile-time” is _not_ reserved to
> applications written in compiled languages (e.g. C, Java, Rust…). It
> applies to interpreted languages (e.g. Python, Javascript…) as well,
> though for such languages it is, strictly speaking, improper (it would
> be more appropriate to talk about “development-time”; however,
> “compile-time” is the commonly accepted term).

**Runtime awareness** of an extension is when an application has been
developed _without_ explicit support for the extension (either because
the developers did not know about that extension, or because they made a
conscious decision not to implement it for whatever reason), but is able
to dynamically learn about the extension at runtime. This requires that
(1) a formal schema describing the extension can be obtained somehow
(this should be the role of the “extension manageability” layer
envisioned [further below](#making-extensions-manageable)) and (2) that
the application is able to exploit said schema.

This level of awareness should allow an application to at least
_validate_ the extended data, even if it cannot make explicit use of it.
Depending on the “richness” of the provided schema, it could also
allow the application to display the extended data to the user in a more
meaningful way.

When schemas are used, this level of awareness does _not_ allow to use
schema-powered code generation! By definition, the application is
already running at that point, so code generation is no longer an
option.

> Some programming languages or frameworks may in fact allow the dynamic
> generation and loading of code at runtime (for example the
> `System.Reflection.Emit` system in the .NET framework). We do _not_
> consider such cases here.

**Unawareness** of an extension is when an application has been
developed without explicit support for the extension _and_ is unable
to dynamically learn about the extension at runtime – either because a
schema describing the extension cannot be obtained, or because the
application is not equipped to exploit the schema (for example, if the
extension comes with a LinkML schema, the application must have
developed so that it can understand and exploit LinkML schemas).

This level of “awareness” (or rather this absence of awareness)
obviously drastically limits what an application can do with the
extended data it may find. However, an application that is compatible
with this extensibility scheme, even if it is completely unaware of a
particular extension, should still minimally be able to recognise the
presence of extended data and ensure that it is fully preserved.

### Guaranteeing independently developed extensions cannot clash
This extensibility scheme is expressly designed to ensure that, even if
two extensions, which are being developed separately without any kind of
coordination between their developers, extend the base model in an
identical fashion (for example by adding a field with the same name, but
possibly with a different meaning, to the same class), it is still
possible to use both extensions at the same time in the same data,
without any possible confusion or, worse, data loss.

### Making extensions manageable
This extensibility scheme makes extensions manageable as first-class
entities.

This notably means that, when a data file is making use of one or more
extensions to the base model, an application (even an application that
is not compile-time aware of any of the extensions being used) can know

* that some extensions are being used;
* what those extensions are;
* which part of the data “belongs” to which extension.

Extensions as first-class entities is also intended to provide
applications (and users) with additional informations about the
extensions, such as who is responsible for a given extension or where
can more informations be found about the extension.

Notably, “more informations about the extension” can include schemas
that formally describe the extension, allowing an implementation that
does not explicitly support the extension at compile-time to learn about
the extension at runtime and to at least _validate_ the extended data
against a reference schema.


### Non-goals

#### Controlling who can extend the base model
This extensibility scheme is not concerned about who is allowed to
create an extension _E?_ to a base model _M_. As far as this scheme is
concerned, _anyone_ can create an extension (and the scheme is
explicitly designed with such a scenario in mind). Adding (and
enforcing) restrictions about the source of an extension (for
example, “only the authors of the base model can create an extension”,
or “an extension can only be used after it has been formally approved by
the International Steering Committee for the Base Model in one of its
annual plenary session”) can easily be done if needed by an additional
layer on top of this extensibility scheme.


## Constraints on extensions
The requirement for data runtime interoperability across implementations
with varying extension support creates some fundamental constraints on
what an extension can do – about how the base model can be extended.

Simply put, an extension cannot change the base model in a way that
would make data conformant to the extented model not conformant with the
base model. So, an extension is really about literally _extending_ the
base model – the extended model should be a strict superset of the base
model.

Therefore, an extension can:

* _add_ new classes to the model;
* _add_ new attributes to existing classes;
* _tighten_ existing constraints (for example, if the base model states
  that a given attribute in a class expects an integer between 0 and
  100, an extension can tighten that constraint to require an integer
  between 0 and 50; any data conforming to the extended model would
  necessarily be still compliant with the base model).

An extension cannot:

* _remove_ any class or attribute;
* _change_ the type of an attribute (e.g. if the base model states that
  a given attribute expects an integer, an extension cannot change that
  to an attribute that expects a string);
* _change_ the meaning of any class or attribute;
* _loosen_ existing constraints (for example, if the base model states
  that a given attribute is _required_ in any instance of a class, an
  extension cannot make that attribute _optional_).


## Basic principles

### Mechanisms for extending a model
This scheme distinguishes two mechanisms by which a base model can be
extended:

* natural extension classes;
* class extensions.

They are not mutually exclusive: the same extension can extend
the same model at different places using any of the two mechanisms at
each place where it needs to extend the model.

#### Natural extension classes
A natural extension class exploits what this scheme calls a “natural
extension point” in the base model. Basically, a natural extension point
is a place in the base model where the model is already set up to allow
for some variability.

To illustrate, let us consider again our base microscope example:

```yaml
manufacturer: Zeiss
model: LSM-880
light_source:
  object_type: https://example.org/ome/ArcLightSource
  power: 45
  lamp_type: Hg
```

We are interested in the `light_source` field, which expects an object
that can be an instance of slightly different classes, each class
representing a different type of “light source”. In the example above,
the object is an instance of the `https://example.org/ome/ArcLightSource`
class, representing a light source that is some kind of arc lamp.

Here is another example with a different light source object:

```yaml
manufacturer: Zeiss
model: LSM-880
light_source:
  object_type: https://example.org/ome/LaserLightSource
  power: 200
  wavelength: 488
  laser_type: HeNe
```

The light source object in this case is an instance of the
`https://example.org/ome/LaserLightSource` class, which has the same
`power` field than the `https://example.org/ome/ArcLightSource` class
but has also some specific fields of its own (`wavelength` and
`laser_type`).

The `object_type` field, which is also common to both classes, is what
allows an application to recognise which type of light source it is
dealing with, and therefore which fields to expect in the rest of the
object.

> In LinkML, the `object_type` field would be known as the
> _type designator_ slot for all the classes representing light sources.
>
> In the Rust language with the [Serde library](https://serde.rs), the
> `light_source` field would be said to accept an
> [internally tagged enum](https://serde.rs/enum-representations.html),
> with the `object_type` field being the “tag”.
>
> Other (de)serialisation libraries might refer to the same concept
> under other names. In this document, we will be using the LinkML
> terminology.

The important point here is that, even when just using the base model
(before adding any extension to the mix), an application using the base
model must already be prepared to deal with the facts that (i) there are
several types (“classes”) of light source objects, and (ii) the exact
type of light source used in a given _Microscope_ object is not known in
advance, but has to be discovered at runtime (by looking up the content
of the `object_type` field). Therefore, it would be reasonably trivial
to add new type of light source to the pre-existing list of types. This
is why we say that the model is _naturally extensible_ at the
`light_source` point (or that `light_source` is a “natural extension
point”).

This scheme strongly recommends that model designers create natural
extension points in their models whenever possible, as it is the
simplest way to extend a model. All that is required to create a
natural extension point is a base class (in the example above, the
base class does not explicitly appear, but that is the class from which
both the `https://example.org/ome/ArcLightSource` and
`https://example.org/ome/LaserLightSource` are derived) with a type
designator field.

When a natural extension point is available, a third-party can then
extend the model simply by deriving new classes from the base class.

This is what our example “SWM extension” extension shown earlier in this
document is doing. It creates a new light source class (which is a
“natural extension class”) called
`https://example.com/swm/KyberLightSource`, which can be used wherever a
light source is expected:

```yaml
light_source:
  object_type: https://example.com/swm/KyberLightSource
  power: 900
  crystal_color: red
```

Upon encountering a _Microscope_ object containing such a light source:

* an application that is aware of the SWM extension at compile-time by
  definition already knows about the new class, so for that application
  nothing special would need to happen;
* an application that becomes aware of the extension at runtime would
  initially the contents of the `light_source` field as a generic light
  source, but could then use the schema describing the extension to
  learn about any field that is specific to the
  `https://example.com/swm/KyberLightSource` class;
* an application that is (and remains) unaware of the extension would
  still recognise the contents of the `light_source` field as a generic
  light source (because it knows that the `light_source` field cannot
  contain anything else than a light source, whatever its exact type
  is), and would at least preserve any additional field that it does not
  know about.

#### Class extensions
The second extensibility mechanism allowed by this scheme is intended to
allow adding new fields to a pre-existing class.

> As noted in the [section about constraints](#constraints-on-extensions),
> an extension could also tighten constraints on existing fields of a
> class. However this kind of modification has no impact on how the data
> is serialised, so it does not require any particular attention here.
> Informing applications of the new, tightened constraints would be the
> role of the schema(s) formally describing the extension, to be
> provided by the [extension management layer](#extension-management-layer).

In our running example, the SWM extension needs to add a `planet` field
to the _Microscope_ object (presumably representing the planet where the
microscope has been manufactured). This is done as follows:

```yaml
manufacturer: Imperial Microscope Builders
model: K2SO
extensions:
  https://example.com/swn/MicroscopeExtension:
    planet: Coruscant
```

The principle is that any class intended to be extensible has an
implicit field named `extensions` specifically intended to store all the
fields added by extensions.

That field is itself a dictionary where each key corresponds to a
particular extension, and the value is an _object fragment_ containing
the fields added to the class by the extension (in this example, the
`planet` field).

This principle naturally ensures that independently developed extensions
cannot interfere with each other, even when used within the same
instance data. If another extension also wanted to add its own `planet`
field, along with maybe other fields of its own, this would look like
this:

```yaml
manufacturer: Imperial Microscope Builders
model: K2SO
extensions:
  https://example.com/swm/MicroscopeExtension:
    planet: Coruscant
  https://example.net/stm/MicroscopeExtension:
    planet: Vulcan
    user: Spock
```

> Of course, we would likely want to _avoid_ having different extensions
> to represent the same thing; but preventing this situation from
> happening is out of reach of any extensibility scheme, if only because
> it is mostly a _social_ problem – basically getting different people
> to agree on working together – rather than purely a technical one. The
> role of the extensibility scheme in that regard is merely to minimize
> the impact of such a situation, not to avoid it.

Upon encountering data where a _Microscope_ instance contains a
`https://example.com/swm/MicroscopeExtension` object fragment:

* a parser that is aware of the SWM extension at compile-time could
  virtually “move” the `planet` field out of the extension object
  fragment and into the _Microscope_ object directly (allowing client
  code to behave as if the `planet` field is intrinsically part of the
  _Microscope_ class, without having to worry about where that field had
  effectively been stored in the serialised data), or more generally
  provide any interface it deems useful to expose the `planet` field to
  client code (this scheme does not mandate any mechanism for that,
  especially since such mechanisms are likely to vary depending on the
  programming language used);
* a parser than becomes dynamically aware of the SWM extension at
  runtime would keep the extension object fragment as it is, but could
  use the schema describing the extension to validate its contents and
  provide additional informations to client code;
* a parser that is and remains unaware of the SWM extension would keep
  the extension object fragment as it is, and simply offers to client
  code possibility to explore the contents of the extension objects.

## Extension management layer
The “extension management” part of this proposed scheme is intended, as
its name implies, to fulfil the goal of making extensions manageable as
first-class entities.

It relies on a simple data structure (hereafter called the
_extension definition_ structure) that can be used to describe an
extension. Such a structure can then be used in any context where
extensions need to be managed. Notably, this scheme proposes that the
top-level object of a data file should include a `extension_manifest`
key containing a list of extension definitions for every extension
effectively used anywhere else in the data file (thereby providing
implementations with a single place to look at in order to determine
which extensions are needed to fully understand the entire file).

The same _extension definition_ structure could also serve as the basis
for a hypothetical, centrally managed “extension registry”, should such
a registry be needed.

As currently envisioned, the extension definition structure would
contain the following fields:

* `id`: the unique identifier for the extension (in our running
  example, this is `https://example.com/swm/`);
* `version`: the version of the extension used;
* `schemas`: a list of schemas that formally describe the extension;
* `homepage`: a link to a page providing human-readable informations
  about the extension (contrary to `schemas` which is intended to
  provide **machine-readable** informations).

Each item in the `schemas` list is a simple, two-fields structure like
this:

```yaml
type: LinkML
url: https://schemas.example.com/swm/swm.yaml
```

where the `type` field identifies the type of the schema, and `url` is
a link to the schema itself.

The scheme allows specifying schemas of different types (e.g. `LinkML`,
`JSONSchema`, etc.) to avoid binding itself to a particular schema
definition language. This does not mean that (i) all extensions should
always provide a schema for each of the available schema definition
languages (though providing more than one type of schema would increase
the likelihood that an application can understand at least one of the
schemas), and that (ii) applications should support all possible types
of schemas (though supporting more than one type would again increase
the likelihood that the application can understand at least one of the
schemas provided by an extension).


## Discussions

### Customizations
As stated in a preliminary note, the scheme discussed here is intended
to be generic.

Among other things, this means that some aspects of it might be
“customized” when the scheme is actually applied to a data model.

In particular, the scheme involves several special attributes that must
be added to the data model that one wishes to make extensible, namely:

* the “type designator attribute”, used to identify the exact type of an
  object assigned to a [natural extension point](#natural-extension-classes);
* the attribute holding the “extension object fragments” used for
  [class extensions](#class-extensions);
* and the attribute holding the “extension manifest” as described in the
  section about [extension management](#extension-management-layer).

In this document, those attributes are named `object_type`,
`extensions`,and `extension_manifest`, respectively.

Those names may or may not be sensible when the scheme is applied to a
particular data model. If they are deemed not to be sensible, it is up
to the model designers to pick alternative names as needed.

One may also consider prefixing all those names with some special
character (e.g. `~object_type` or `%extensions`) to highlight the
special roles of those attributes and help distinguish them from all the
other, non-special attributes present in the data model.

### On the use of URIs as identifiers
This scheme requires three different types of identifiers:

* identifiers for [natural extension classes](#natural-extension-classes);
* identifiers for [class extensions](#class-extensions);
* identifiers for [extension definitions](#extension-management-layer)
  in the “extensions manifest”.

As shown in the running example used in this document, this scheme
proposes that all identifiers follow a URI-based scheme, where (i) an
extension is identified by some base URI, and (ii) any natural extension
class or class extension defined by the extension is defined by a URI
that is built on top of the extension’s base URI.

For example, the SWM extension is identified by the base URI
`https://example.com/swm/`, its “KyberLightSource” natural extension
class is identified by `https://example.com/swm/KyberLightSource`, and
its class extension for the _Microscope_ class is identified by
`https://example.com/swm/MicroscopeExtension`.

Such a ID scheme automatically ensures uniqueness of identifiers across
all extensions, which is critical to ensure that independently developed
extensions cannot interfere with each other.

> The implicit assumption here is that extension developers will be well
> behaved and will not use URIs that they do not control.

It also offers a straightforward way to distinguish between “official”
extensions and “third-party” extensions (should such a distinction be
desired – this scheme is neutral on that aspect), simply by declaring
that all extensions whose base URI is in a given domain are “official”,
and all extensions whose base URI is outside of that domain are
third-party extensions. The people responsible for managing the data
model can then control who should be allowed to create official
extensions (again, should such a control be desired) by controlling who
can allocate URIs in the “official domain”.

### Why not using prefixed names for “class extensions”?
That is, why not doing something like this:

```yaml
manufacturer: Imperial Microscope Builders
model: K2SO
swm:planet: Coruscant
stm:planet: Vulcan
stm:user: Spock
```

The author of this document strongly believes that such attempts to
mimic XML-style qualified names in JSON or YAML are ill-inspired.

Qualified names work in XML because _XML has built-in support for them_!
The concepts of qualified names and of namespaces are a core part of the
XML specification, and any XML-compliant library must support them. JSON
does _not_ have any such concept (and neither does YAML), which means
that applications would either need to hack their own namespace
management feature, or accept that such “qualified names” do not in fact
constitute a proper scoping mechanism.

“Hacking a namespace management feature” could look like thisL

```yaml
namespaces:
  swm: https://example.com/swm/
  stm: https://example.net/stm/
manufacturer: Imperial Microscope Builders
model: K2SO
swm:planet: Coruscant
stm:planet: Vulcan
stm:user: Spock
```

but this will never be directly supported by a JSON or YAML parsing
library, meaning that applications will need to implement all the
namespace management on their own.

Without a custom namespace management system, then “qualified names” are
in fact just slightly longer names in a single, global, flat namespace.
The presence of a colon does not automagically transform the global
namespace into a tree of scoped namespaces. This is in fact no different
than this:

```yaml
namespaces:
  swm: https://example.com/swm/
  stm: https://example.net/stm/
manufacturer: Imperial Microscope Builders
model: K2SO
swm_planet: Coruscant
stm_planet: Vulcan
stm_user: Spock
```

which does _not_ enclose the extension fields in their own namespace, it
merely gives them longer names – with no intrinsic guarantee that no
other extension developer will ever want to use the same “prefixes”
`swm_` or `stm_`.


## Implementation in LinkML
This section illustrates how the scheme can be implemented in a data
model that is formally defined using LinkML.

This is informative only. The scheme does not _need_ LinkML to be
implemented, and even when LinkML is used it could possibly be done
differently than what is proposed here.

### Base model

#### Creating natural extension points
To create a [“natural extension point”](#natural-extension-classes) in a
LinkML schema:

1. Give a class a “type designator” slot;
2. Explicitly configure the class to “allow extra slots”, and make sure
   it is not _abstract_.
3. Any slot/attribute, anywhere in the schema, whose range is set to
   that class becomes a _de facto_ natural extension point.

The type designator SHOULD be URI- or CURIE-typed. This scheme currently
favours URI-typed designators, since they dispense from having to
manage CURIE prefixes. `uriorcurie`-typed designators SHOULD be avoided.

Some limitations currently exists in LinkML-Py, that implementers should
be aware of.

The most important is that, for now, instance data containing a type
designator value that does not correspond to a known subclass of the
class carrying the type designator will be considered _invalid_. This
means that an application that is only aware of the base schema will
always reject data containing a “natural extension” – basically making
the entire concept of “natural extension” void! Work is in progress with
the LinkML community to fix that issue.

Another issue (which may in fact be fixed at the same time as the
previous one) is that, when generating Python code, the generated code
will not include what is needed to properly recognise type designators
when the schema does not contain at least one subclass of the class
carrying the type designator (basically, the code generator assumes
that, since there are no subclasses, there is no need for a way to
designate the effective type at runtime).

Lastly (but much less importantly – it’s more an annoyance than anything
else), because of another bug in some components of LinkML-Py, type
designators for now should always be defined as global, schema-wide
_slots_, rather than as class-specific _attributes_, even if the slot is
only ever used in one class.

##### Example
The “light source” extension point in the running example of this
document can be implemented in LinkML as follows:

```yaml
slots:

  object_type:
    description: >-
      The type designator for natural extension points.
    range: uri
    designates_type: uri


classes:

  LightSource:
    description: >-
      The base class for all light source objects.
    slots:
      - object_type
    attributes:
      power:
        range: integer
      # All other attributes common to all light source types
      # ...
    extra_slots:
      allowed: true

  ArcLightSource:
    description: >-
      Represents a light source that is specifically an arc lamp.
    is_a: LightSource
    attributes:
      # Attributes specific to this type of light source
      # ...

  LaserLightSource:
    description: >-
      Represents a light source that is specifically a laser.
    is_a: LightSource
    attributes:
      # Attributes specific to a laser-based light source
      # ...

  Microscope:
    attributes:
      light_source:
        description: >-
          The light source available on this microscope.

          This is the actual extension point.
        range: LightSource
      # Other attributes for the Microscope class
      # ...
```

#### Allowing class extensions
To allow classes from the base schema to be extended according to the
[mechanism described in this scheme](#class-extensions), the base
schema must:

1. define a base class (hereafter called `ExtensionNode`, but the name
   does not really matter) that all extensions shall derive from (that
   class must have a type designator);
2. ensure that any class that is intended to be extensible has a
   dedicated `extensions` field to store the extension object fragments.

Here is a possible implementation:

```yaml
slots:

  extension_type:
    description: >-
      The type designator for all extension nodes.
    range: uri
    designates_type: true
    key: true
    alias: type


classes:

  IsExtensibleMixin:
    description: >-
      An object that can carry arbitrary extension object fragments.

      Reuse this mixin in any class to allow the class to be
      extended.
    mixin: true
    attributes:
      extensions:
        description: The collection of extension object fragments.
        range: ExtensionNode
        multivalued: true
        inlined: true
        inlined_as_list: false

  ExtensionNode:
    description: >-
      Represents a fragment of an object that is defined by an extension
      rather than by the base schema.
    slots:
      - extension_type
    extra_slots:
      allowed: true
```

You may notice at this point that the `extensions` attribute looks
similar to a natural extension point, since its range is set to a class
that has a type designator. This is because it _is_ indeed a natural
extension point! Conceptually, the way this extensibility scheme allows
to extend classes is by giving each class (or at least, each class
reusing the `IsExtensibleMixin` mixin) a “generic” natural extension
point.

Applying this to our running example, when we define the _Microscope_
class we only need to make it inherit from the `IsExtensibleMixin`,
which will give it the `extensions` field intended to store the
extension object fragments:

```yaml
classes:

  Microscope:
    mixins:
      - IsExtensibleMixin
    attributes:
      # all the attributes for the Microscope class defined in the
      # base model...
```

### Extended model

#### Creating a natural extension
If the base model has a natural extension point (such as the
`light_source` field in the `Microscope` class we have seen above),
then creating a natural extension intended to be used at that point is
simply a matter of:

1. importing the base schema;
2. creating a new subclass of the appropriate base class.

For example, to create the `KyberLightSource` extension:

```yaml
classes:

  KyberLightSource:
    description: >-
      A light source that uses a Kyber crystal; allows to see things
      through the Force, but can only be used by Jedi microscopists.
    is_a: LightSource
    attributes:
      # Attributes that specifically describes this type of light source
      # ...
```

Of note, the class does not need to derive directly from the base, it
can derive from another subclass if it happens to be more appropriate.

#### Creating a class extension
This is how the `https://example.com/swm/MicroscopeExtension` extension
object fragment (which aims to add a `planet` field to the base
_Microscope_ class) could be implemented:

```yaml
classes:

  MicroscopeExtensionMixin:
    description: >-
      A mixin that contains the attributes we want to extend the
      Microscope class with.

      This is a mixin in order to facilitate extension composability.
    mixin: true
    attributes:
      planet:
        description: The planet where the microscope was manufactured.
  
  MicroscopeExtension:
    description: >-
      The actual extension object. This (i) inherits from ExtensionNode
      (so that it is recognized as an extension object fragment) and
      (ii) reuses the MicroscopeExtensionMixin above.
    is_a: ExtensionNode
    mixins:
      - MicroscopeExtensionMixin

  ExtendedMicroscope:
    description: >-
      This is a subclass of the base Microscope class with the added
      MicroscopeExtensionMixin.

      This class is not, strictly speaking, needed for the extension
      scheme described in this document, but its presence will allow
      an application to support the PersonExtension “natively”, by
      using this class whenever the use of the Person class is
      expected.
    is_a: Microscope
    mixins:
      - MicroscopeExtensionMixin
```
