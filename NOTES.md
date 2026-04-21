# Notes

## Terminology
Unless otherwise stated, the following disambiguation or explanatory
notes applies throughout this repository.

### “Model” vs “Schema”
“Model” refers to an _abstract_ model of a data structure. That model is
independent on any concrete representation of the structure in the
memory of a computer or within a file. The model is typically described
only in an informal manner (e.g. as plain English prose or even as a
drawing on a napkin), through more formal descriptions are possible.

By contrast, “schema” refers to a _formal description_ of a _concrete_
data structure. That description uses a dedicated _Schema Definition
Language_ (SDL), such as XML Schema, JSON-Schema, LinkML, etc.

The same data structure may be represented by different schemas in
different schema definition languages.

## Extensibility
“Extensibility”, in the context of NGMF, refers to the possibility,
given a data model _M_, of representing / storing / manipulating data
for which there are no provisions in _M_.

### Built-in extensibility vs model extensions
It is important to distinguish two completely different forms of
extensibility:

#### Extensibility through the model’s built-in features
The original data model _M_ may be designed in such a way that it allows
the representation of arbitrary pieces of data.

A typical example is the OME model with its _Annotations_ feature. The
SSSOM model with its _extension slots_ is another example.

Instance data that make use of such features remain entirely compliant
with the base model, and can be processed by software that support the
base model without requiring any change to the software.

#### Extensibility by extending the model (and subsequent schema(s))
This is the case where the original model, either does not have any
built-in extensibility feature, or has extensibility features that are
deemed insufficient to represent the extra pieces of data one may need
to represent.

The original model may then be _extended_ to add the missing elements
that one may need.

Instance data that make use of such a model extension would typically
_not_ be compliant with the base model anymore, and would not be usable
with software that has not been explicitly updated to take into account
the extension to the model.

One can further distinguish whether the extension is added to a
_natural extension point_ or anywhere else. A _natural extension point_
(whether it is described as such in the base model or not) is a place
where the base model is already designed to allow some flexibility, and
where the extension merely adds a new option where several options were
already possible. For example, light sources in the OME model are a
natural extension point, because the model already has classes for
several types of light sources.

(Note that just because there is a natural extension point does not
necessarily imply that the model _allows_ an extension at this point!)

### Types of model extension
One can distinguish between

* a “focalised” extension, that adds one (possibly large) piece of data
  at one place within the base model;
* a “transversal” extension, that adds one (typically small) piece of
  data to many (maybe all) elements of the base model.

The addition of a new light source type to the OME model would be an
example of a focalised extension, as it would only affect the
`Instrument`.

A transversal extension could be, for example, an extension that adds to
every single class representing a hardware component (`Instrument`,
`Objective`, `Detector`, etc.) a new field intended to store the date
of the last maintenance operation performed on the component.

### Evolution vs Extension
It may also be necessary to distinguish between:

* making a model evolve by creating a new version of the model;
* extending a model.

Those two things may be very similar, but the difference lies in who is
involved and what the consequences are. Creating a new version of the
model would typically be done by the same people (or organisations) who
created the base model in the first place, and that are responsible for
it. An extension, on the other hand, would typically be made by a third
party.

### Extensibility and compatibility/interoperability
Extending a model immediately raises the question of the compatibility
between the base model and the extended model.

Assuming:

* a base model _M_;
* an extension _E_ to the base model, yielding an extended model _ME_;
* an application _A(M)_ that was developed against the base model _M_;
* and an application _A(ME)_ that was developed against the extended
  model _ME_;

we can define the following compatibility scenarios:

#### No compatibility required
The application _A(M)_ is only ever expected to manipulate data
conformant to the base model _M_. The application _A(ME)_ is only ever
expected to manipulate data conformant to the extended model _ME_.

This is the simplest case. It should go without saying that it is also
the least useful one. There is no possible interoperability between
_A(M)_ and _A(ME)_.

#### Forward compatibility required
The application _A(M)_ is only ever expected to manipulate data
conformant to _M_. The application _A(ME)_, however, is expected to be
able to transparently manipulate data that is conformant to either _M_
or _ME_.

This provides “unidirectional” interoperability in that _A(ME)_ can
manipulate data produced by _A(M)_, but _A(M)_ cannot manipulate data
produced by _A(ME)_.

Of note, this does not preclude the possibility for _A(ME)_ to offer
some kind of “export mode“ in which it explicitly produces data that is
conformant to _M_ so that it can be manipulated by _A(M)_.

#### Forward and backward compatibility required
Compared to the previous scenario, here the application _A(M)_ is
expected to be able to transparently manipulate data that is conformant
to either _M_ or _ME_. (_A(ME)_ is still also expected to be able to
transparently manipulate both.)

This provides “bidirectional” interoperability in that either
application can manipulate data produced by the other, without requiring
the _A(ME)_ application to export its data in “compatibility mode” for
the benefit of the _A(M)_ application.

Because _A(M)_ is, by definition, not aware of the _E_ extension, what
it can do with _ME_ data will necessarily be limited; it will most
likely not be able to make use of the part of the data that is specific
to the _E_ extension. However, it may offer various degrees of support:

* at the very least, it MUST _preserve_ the extended data entirely (so
  that a file initially created by _A(ME)_ that is then opened and saved
  by _A(M)_ can be read again by _A(ME)_ with all the extended data
  unchanged);
* it may (should?) _expose_ the extended data to the user, so that the
  user can make sense of the extended data for herself;
* if (1) the _E_ extension is defined through a formal schema, and (2)
  the schema (or at least a link to it) is embedded within the data,
  the application can use the schema to offer slightly better support
  for the extended data, such as validating it against the schema and/or
  exposing it to the user with human-friendly labels (assuming the schema
  would provide such labels).

**This bidirectional interoperability is the scenario explicitly aimed
for by the NGMF project.** It will be a balancing act between
_extensibility_ and _interoperability_, because the more extensible the
metadata model will be, the more difficult it will be to ensure
bidirectional interoperability.

At the extremes, we can have 0% extensibility and 100% interoperability
(a model that disallows any kind of extension but will guarantee perfect
interoperability), or 100% extensibility and 0% interoperability (a
model that anyone can extend without any restriction but where, as a
result, every implementation has its own version that is incompatible
with all the other implementations). We want a middle ground where
extensibility is permitted and even encouraged, but within some defined
limits so to as to guarantee bidirectional interoperability.

### Compatibility
We can define several types of compatibility depending on the kind of
work that the developers of an application _A(M)_ (that supports only the
base model _M_) have to do in order to support an extension _E_.

* The developers have to write code to explicitly support _E_ (thereby
  turning _A(M)_, an application that only supports _M_, into an
  application _A(ME)_ that supports the extended model _ME_). This is
  the most common scenario.
* The part of the code that depends on the model _M_ is always
  automatically generated from the schema that formally describes _M_,
  so all the developers of _A(M)_ have to do to support _E_ is to
  re-generate and re-compile their code starting from the extended
  model _ME_ rather than the base model _E_ – no “manual” adapation is
  required. This is _compile-time compatibility_.
* The developers do not have anything special to do (not even recompile
  the application). This is _runtime compatibility_.

### Extending an extension?
Open question: should it be possible to extend an extension?

Let’s say we have a base model _M_ and an extension _E_ (yielding an
extended model _ME_). The extension _E_ becomes so widely used that
people start wanting to build their own extension on top of it.

Should that be possible? I.e. can one define an extension _F_ that
extends, not the base model _M_, but the (already) extended model _ME_,
thereby creating a _MEF_ model?
All the while preserving the required backward compatibility that is a
core feature of NGMF, e.g.:

* an application that only knows about _M_ can nonetheless process data
  that is conformant to either _ME_ or _MEF_, while preserving the data
  that is specific to the _E_ or _F_ extensions;
* an application that only knows about _ME_ can nonetheless process data
  that is conformant to _MEF_, while preserving the data that is
  specific to the _F_ extension.

I personally think this _should_ be possible, and NGMF should be
designed to allow such a case. Ideally in such a way that there are no
arbitrary limitation as to the “depth” of extensions.

### Extension composability
Let us consider a slightly different scenario:

We have a base model _M_ and two _independent_ extensions _E_ and _G_,
yielding two different extended models _ME_ and _MG_.

In this situation:

* an application that only knows about _ME_ can nonetheless process data
  that is conformant to _MG_, while preserving the data that is specific
  to the _G_ extension;
* conversely, an application that only knows about _MG_ can nonetheless
  process data that is conformant to _ME_, while preserving the data
  that is specific to the _E_ extension.

## Imaging-PHD, NGMF, and OME-Zarr
The concrete goal of the Imaging-PHD project, seen from a high level, is
to have a way to store and exchange highly detailed formalized
descriptions of microscopy hardware configurations.

The “Next Generation Metadata Framework” (NGMF) is the generic metadata
framework that will be used to represent Imaging-PHD data. Imaging-PHD
can be considered a “client”, or a “user“, of NGMF – the _primary_ user
for now, though of course it is hoped that, once NGMF has been validated
for Imaging-PHD, it will be reused in broader contexts.

Imaging-PHD/NGMF are linked to OME-Zarr on two points:

* at some point we will want to store Imaging-PHD metadata directly
  within the metadata of a OME-Zarr file;
* we will likely want to reuse at least some of the NGMF concepts for
  other parts of OME-Zarr metadata, beyond Imaging-PHD.

## Basic guidelines for an extensible model

### To LinkML or not to LinkML?
Within the Imaging-PHD project, it is extremely likely that LinkML will
be used as the schema definition language to create the formal,
machine-exploitable version of the metadata model.

In OME-Zarr context, on the other hand, we will likely merely
_recommend_ that metadata extensions be defined with LinkML, without
making it a hard requirement. Authors of OME-Zarr metadata extensions
will be free to use whatever schema definition language they fancy 
– including no schema definition language at all, for example for an
extension that is so simple that the use of a SDL to define it could
arguably be considered “overkill“.

Of note, when LinkML is not used, it will be the responsibility of the
extension’s authors to define the rules to represent the extension’s
data in RDF (something that would be automatically taken care of by the
use of LinkML).

Whether the extension is defined in LinkML or not, there will be
constraints on the design of the extension, aimed at achieving the
balancing act mentioned above between “extensibility“ and
“interoperability”.

### No duck-typed properties
At any time, the type of the value expected to be found in a “property”
(a “slot”, in LinkML parlance; when the data is serialised in JSON that
would be a “key” in a JSON dictionary) MUST be fully determined by
either the name of the property alone or by the name of the property and
the context where the property is found. It MUST NOT be required to
“peek” inside the value of the property to infer its type.

That is, an example like the following (coming from an early version of
the SSSOM specification) must be avoided:

> The `curie_map` slot is either a dictionary associating prefix names
> to prefix IRIs, as follows:
>
> ```yaml
> curie_map:
>   FBbt: http://purl.obolibrary.org/obo/FBbt_
>   UBERON: http://purl.obolibrary.org/obo/UBERON_
> ```
>
> or a URL pointing to an online location where such a dictionary can
> be found, as in:
>
> ```yaml
> curie_map: http://example.org/my_curie_map.yaml
> ```

In this situation, you should instead use two distinct properties, which
could be named for example `local_curie_map` and `remote_curie_map`,
each with a fixed type (a dictionary for the former, a URL for the
latter).

(Of note: We expect that this particular situation, where one might want
to either define some data locally, or refer to an external resource,
will arise quote often. Therefore, NGMF will include a mechanism to
explicitly support it. That mechanism remains to be fully defined.)

### “Type designators“ / “internally tagged enums”
The constraint against “duck-typed” properties does _not_ preclude the
use of “type designator slots” (LinkML parlance) or other similar
constructs such as Serde’s “internally tagged enums”, when one needs to
provide the exact subtype of an object. In fact the use of such
constructs will be _recommended_.

That is, if the model defines, say, a `Component` class and several
subclasses (each representing a different type of component), the
`Component` class can have a “type designator slot” whose value will
unambiguously indicate the exact type of component is present in a
given instance data. Whenever a parser is expecting a value of type
`Component`, it may then look at the value of the “type designator slot”
to determine which subclass of `Component` should be instantiated.

This does not violate the “no duck-typed properties” constraint
because, even without looking at the value of the “type designator
slot”, the parser already knows that the object it is looking at can
only be an instance of `Component`, even if it does not know yet the
exact subtype.

### Dual local/remote references
If it is desirable to be able to represent an object either as an
“embedded” object or as merely a pointer to an external resource, the
recommended way will be along the following lines:

1. Ensure the class defining the object has an identifier slot.
2. Wherever the object is required, use a non-inlined reference.
3. Have another slot somewhere in the schema to store an inlined version
   of the object, to be used when embedding the object is preferred.
   Conversely, when it is preferred to use a remote resource, that slot
   should be left empty, and the non-inlined reference in (2) should
   point to the remote resource.

## Implementation notes

### Python
Extensible classes in Python are easily achievable because ultimately,
all instances of a class in Python have at their core a dictionary whose
keys are the “fields” of the class.

Assuming the base model _M_ defines a class `Foo` and an extension _E_
defines a derived class `ExtendedFoo` containing additional slots, an
implementation that is only aware of _M_ (and therefore only knows about
the `Foo` class, not about the `ExtendedFoo` class) should easily be
able to deserialize an instance of `ExtendedFoo` into an instance of
`Foo`, while preserving the slots it does not know about (slots that are
specific to `ExtendedFoo`) in its internal dictionary.

### Java
Supporting extensible classes in Java can easily be achieved by making
sure that all classes supposed to be extensible have an internal
`Map<String,Object>` hashtable intended to store the values of any
unknown slot.

An application that is only aware of _M_ (and therefore only knows about
the `Foo` class) can then easily deserialise an instance of
`ExtendedFoo`, by assigning all known properties to the corresponding
slots of the base `Foo` class, while preserving the slots it does not
know about in the dedicated hashtable.
