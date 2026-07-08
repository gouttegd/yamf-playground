# Designing an extensibility scheme

## Preliminary notes

### JSON vs YAML
The extensibility scheme we are concerned about is primarily intended to
deal with JSON-formatted data. However in this document, all examples
will be given in YAML, which is considered (at least by the author of
those lines) to be somewhat easier to read and write than JSON. The use
of YAML rather than JSON should not affect the extensibility scheme in
any meaningful way.

### Objects, models, schemas
This document will talk a lot about “objects”. In this context, an
object is basically a JSON dictionary containing pre-determined keys.

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


## Goals

### Runtime data interoperability
The key aim of this extensibility scheme is to allow for runtime data
interoperability across implementations that support a different set of
extensions (including the empty set, no extensions) of a same “base”
model.

Given a base model _M_ and two extensions _E1_ and _E2_ (which would
ideally al be expected to be represented as schemas in an appropriate
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

### Guaranteeing independently developped extensions cannot clash
This extensibility scheme is expressly designed to ensure that, even if
two extensions, which are being developed separately without any kind of
coordination between their developers, extend the base model in an
identical fashion (for example by adding a field with the same name, but
possibly with a different meaning, to the same class), it is still
possible to use both extensions at the same time in the same data,
without any possible confusion.

### Making extensions manageable
A secondary goal of this extensibility scheme is to make extensions
manageable as first-class entities.

This notably means that, when a data file is making use of one or more
extensions to the base model, an application (even an application that
is not aware of any of the extensions being used) can know

* that some extensions are being used;
* what those extensions are;
* which part of the data “belongs” to which extension.

Extensions as first-class entities is also intended to provide
applications (and users) with additional informations about the
extensions, such as who is responsible for a given extension or where
can more informations be found about the extension.

Notably, “more informations about the extension” can include a schema
that formally describes the extension (primarily in LinkML, though the
scheme may allow the use of other schema definition languages as well),
allowing an implementation that does not explicitly support the
extension to still be able to at least _validate_ the extended data
against their reference schema.

> Of note, the “extension manageability” part of this extensibility
> scheme remains to be fully defined!


## Non-goals

### Controlling who can extend the base model
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

Simply put, an extension cannot _change_ the base model in a way that
would make data conformant to the base model not conformant with the
extended model. So, an extension is really about literally _extending_
the base model – the extended model should be a strict superset of the
base model.

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

## Awareness of an extension
This document has already mentioned several times the notion of an
implementation “aware of an extension” or “aware of an extended model”.
It is necessary to explain what is exactly meant by that.

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

When LinkML schemas are used to formally describe the base model and its
extension(s), compile-time awareness notably means that developers can
rely on LinkML to automatically generate at least part of the code
needed to read and manipulate the (extended) data.

**Runtime awareness** of an extension is when an application has been
developed _without_ explicit support for the extension (either because
the developers did not know about that extension, or because they made a
conscious decision not to implement it for whatever reason), but is able
to dynamically learn about the extension at runtime. This requires that
(1) a formal schema describing the extension can be obtained somehow
(this should be the role of the “extension manageability” layer
envisioned in a [previous section](#making-extensions-manageable)) and
(2) that the application is able to exploit said schema.

This level of awareness should allow an application to at least
_validate_ the extended data, even if it cannot make explicit use of it.
Depending on the “richness” of the provided schema, it could also
allow the application to display the extended data to the user in a more
meaningful way.

When LinkML schemas are used, this level of awareness does _not_ allow
to use any feature that depend on LinkML-powered code generation! By
definition, the application is already running at that point, so code
generation is no longer an option.

> Some programming languages or frameworks may in fact allow the dynamic
> generation and loading of code at runtime (for example the
> `System.Reflection.Emit` system in the .NET framework). We do _not_
> consider such cases here.

**Unawareness** of an extension is when an application has been
developed without explicit support for the extension _and_ is unable
to dynamically learn about the extension at runtime – either because a
schema describing the extension cannot be obtained, or because the
application is not equipped to exploit the schema (for example, if the
extension comes with a LinkML schema, the application must be able to
understand LinkML schemas, which could be very difficult if the
application is written in a language for which no LinkML runtime library
is available).

This level of “awareness” (or rather this absence of awareness)
obviously drastically limits what an application can do with the
extended data it may find. However, as stated in a previous section, an
application that is compatible with this extensibility scheme, even if
it is completely unaware of a particular extension, should still
minimally be able to recognise the presence of extended data and ensure
that it is fully preserved.

## Basic principles

### Separating type designation from extension manageability
An extensibility scheme has two main roles to fulfil:

(a) Allowing an application to recognise the parts of the data that
“belong” (or are conformant) to an extension, rather than to the base
model; this is hereafter called “type designation”, because the
extensibility scheme informs the application of what the real type of an
object is (is it ① an object that is entirely described by the base
model, ② an object that is entirely described by an extension, or ③ an
object that is originally described by the base model but that also
contains fields added by one or more extensions?).

> FIXME: “Type designation” is not really a good name for this role. It
> stems from the fact that its principles are directly inspired by the
> concept that LinkML calls “type designator” (which is how this role is
> to be implemented in a LinkML-defined model).

(b) Allowing the application to “manage” extensions, as envisioned in
the [corresponding section](#making-extensions-manageable).

This scheme posits that the two roles can and should be separated, for
several reasons.

Firstly, type designation is much more critical, with regard to
interoperability, than extension management. An application parser MUST
be able to detect the effective type of an object in order to
deserialise that object correctly – in the worst-case scenario, if the
application is unaware of an extension, it must at least recognise that
the object is not an object from the base model (or is an object from
the base model with additional fields), and deal with it accordingly.
Extension manageability is certainly a desirable feature, but is not
_required_ for runtime data interoperability. Separating type
designation from extension management allows to minimally implement the
former while the latter can remain optional.

Secondly, type designation and extension management operate at
different level. Type designation must happen for every single object in
the data. By contrast, extension management can (and arguably should) be
a file-level thing.

Thirdly, a single extension can affect (extend) many classes in the
original model. If an extension _Foo_ extends both the _Bar_ class and
the _Baz_ class, then objects of both type will need their own type
designation, but we are still dealing with only one extension, which
only needs to provide one schema describing the additions to both
classes. This would be more difficult to achieve if type designation and
extension management were tightly coupled.

### Extending a class
Let’s start with an example.

Let _Person_ be a class defined in the base model, with two fields
`name` and `email` such that the following object is a valid instance of
the _Person_ class:

```yaml
name: Alice
email: alice@example.org
```

Now let _Foo_ be an extension of the base model that purports to add a
`age` field to the _Person_ class. This extensibility scheme proposes to
represent a valid instance of that extended class as follows:

```yaml
name: Alice
email: alice@example.org
nodes:
  https://example.org/foo/FooPersonExtension:
    age: 43
```

The principle is that any class intended to be extensible has an
implicit field (here called `nodes`, but an implementation of this
scheme could very well choose any other name) specifically intended to
store all the fields added by extensions.

That field is itself a dictionary where each key corresponds to a
particular extension, and the value is an “object fragment” containing
the fields added to the base class by the extension (in this example,
the `age` field).

This principle naturally ensures that independently developed extensions
cannot interfere with each other. If another extension (say _Bar_) also
wanted to add its own `age` field, along with maybe other fields of its
own, this would look like this:

```yaml
name: Alice
email: alice@example.org
nodes:
  https://example.org/foo/FooPersonExtension:
    age: 43
  https://example.com/bar/BarPersonExtension:
    age: 43
    dob: 1983-01-01
```

(Of course, we would likely want to avoid having different extensions to
represent the same thing; but preventing this situation from happening
is out of reach of an extensibility scheme, if only because it is mostly
a _social_ problem – basically getting different people to agree on
working together – rather than purely a _technical_ one. The role of the
extensibility scheme here is merely to minimize the impact of such a
situation, not to avoid it.)

#### Why using URIs to identify the extensions?
This is strictly speaking not mandatory, but at least _very strongly_
recommended.

The implicit assumptions here are that ① the developers of a given
extension have control over the base URI they choose, and ② other
extension developers will be well-behaved enough not to use a base URI
that they do _not_ control.

If those two assumptions are satisfied, then the scheme guarantees that
data from several independently developed extensions can always co-exist
within the same data file.

#### Why not using prefixed names?
That is, why not doing something like this:

```yaml
name: Alice
email: alice@example.org
foo:age: 43
bar:age: 43
bar:dob: 1983-01-01
```

The author of this document strongly believes that such attempts to
“transpose” XML-like qualified names (QNAME) to the JSON world are
ill-inspired.

Qualified names work in XML because _XML has built-in support for them_!
The concepts of qualified names and of namespaces are a core part of the
XML specification, and any XML-compliant library must support them. JSON
does _not_ have any such concept (and neither does YAML), which means
that applications must either hack their own namespace management
feature, or accept that such “qualified names” do not in fact constitute
a proper scoping mechanism.

“Hacking a namespace management feature” could look like this:

```yaml
namespaces:
  foo: https://example.org/foo/
  bar: https://example.com/bar/
name: Alice
email: alice@example.org
foo:age: 43
bar:age: 43
bar:dob: 1983-01-01
```

but this will never be directly supported by a JSON parsing library,
meaning that applications will need to implement all the namespace
management on their own.

Without a custom namespace management, then “qualified names” are in
fact just slightly longer names in a single, global, flat namespace. The
use of a colon does not automagically transform the global namespace
into a tree of scoped namespaces. This is in fact no different than
this:

```yaml
name: Alice
email: alice@example.org
foo_age: 43
bar_age: 43
bar_dob: 1983-01-01
```

which does _not_ enclose the extension fields in their own namespace, it
merely gives them longer names – with no intrinsic guarantee that no
other extension developer will ever want to use the same “prefixes”
`foo_` or `bar_`.
