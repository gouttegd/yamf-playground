# Making OME-Zarr extensible

## Introduction
This document outlines a proposal to bring “manageable extensibility”
to the [OME-Zarr](https://ngff.openmicroscopy.org/) format.

It attempts to fulfil the aims set forth in the accompanying
[extensibility manifesto document](EXTENSIBILITY_MANIFESTO.md).

It builds on the [generic extensibility scheme](../EXTENSIBILITY.md),
also found in this repository and which may be consulted for more
details. This document is about how the extensibility scheme can be
applied specifically to the case of OME-Zarr.

## General description
The proposal comprises three components: (i) an “extensions manifest”,
and two different mechanisms for extensions to inject extended metadata
into a OME-Zarr `zarr.json` file: (ii) “natural extensions” and (iii)
“generic class extensions”.

### The extensions manifest
The extensions manifest is intended to fulfil the aim of making
extensions manageable as first-class entities. As
[described](../EXTENSIBILITY.md#extension-management-layer) in the
generic extensibility scheme document, the idea is simply to add a
top-level `extension_manifest` key that provides a list of all the
extensions being used in the current OME-Zarr file.

> In the context of this document, “top-level” actually means “under
> the `ome` key”, _not_ “under the root of the JSON document”. This is
> a OME-Zarr-specific proposal, so for all purposes we consider that the
> `ome` key **is** the root – we are not concerned about anything that
> may exist above it.

An extension manifest would like as follows:

```yaml
ome:
  extension_manifest:
    - id: https://example.com/my-ngff-extension/
      version: 1.0
      schemas:
        - type: LinkML
          url: https://schemas.example.com/my-ngff-extension/extension.yaml
        - type: JSONSchema
          url: https://schemas.example.com/my-ngff-extension/extension.json
      homepage: https://example.com/my-ngff-extension/
    - id: https://example.net/another-extension/
      version: 1.1
      schemas:
        - type: JSONSchema
          url: https://example.net/another-extension/schema.json
      homepage: https://example.net/another-extension/overview.html
```

> All examples in this document will be provided in YAML rather than
> JSON, for readability.

That manifest lists two extensions, identified by the IDs
`https://example.com/my-ngff-extension/` and
`https://example.net/another-ngff-extension/`.

> This proposal **strongly** recommends that all extensions are
> identified by a URI-based scheme, which will ensure (assuming
> that extension developers will only use URIs in a namespace that they
> have control of) that independently developed extensions cannot
> interfere with each other.

Of note, the details of what may (or should, or must) be provided in an
extension manifest still need to be fully fleshed out. But the general
idea outlined above should suffice to understand the proposal.

### Natural extensions
A “natural extension” is an extension that plugs itself in at a place
where the OME-Zarr specification is already leaving room for
extensibility.

In effect, every place in the OME-Zarr specification where

* a dictionary is expected to contain a `type` attribute,
* that attribute accepts a fixed set of values,
* the value of the `type` attribute dictates how the dictionary is to
  be interpreted,

may qualify as a natural extension point. It is _de facto_ a natural
extension point if the specification explicitly states that “the `type`
field may accept more values in the future” (or any similar formulation
to the same effect).

The present scheme proposes that at any natural extension point, an
extension can add its own type with its own interpretation. The only 
requirement is that the type MUST be identified with a value that is
derived from the extension’s identifier (as provided in the extension
manifest). That is, if the extension has the ID
`https://example.com/my-ngff-extension/`,
then the new type added by the extension MUST be something like
`https://example.com/my-ngff-extension/my-new-type`.

### Generic class extension
A “generic class extension” is an extension that plugs itself in at a
place where the OME-Zarr specification does not have provision for
extensibility. It is intended to allow any extension to add data at
basically any place under the `ome` tree.

The principle is that any dictionary under `ome` (including `ome`
itself) can accept an additional `extensions` key, which is itself a
dictionary whose keys are object identifiers derived from extension
IDs, and whose values are a dictionary containing anything an extension
might decide.

For example:

```yaml
ome:
  extensions:
    https://example.com/my-ngff-extension/foo:
      extension_specific_attribute_1: some value
      extension_specific_attribute_2: some other value
    https://example.net/another-ngff-extension/bar:
      extension_specific_attribute_1: yet another value
```

That example extends the top-level `ome` dictionary with extended data
coming from the `https://example.com/my-ngff-extension/` extension and
from the `https://example.net/another-ngff-extension/` extension. Data
from each extension is enclosed in its own subdictionary whose key is
specifically derived from the extension’s identifier, guaranteeing that
two independently developed extensions cannot interfere with each other
even if they are both used simultaneously within the same OME-Zarr file.

## Examples

### RFC-4 (“Axis Orientation”)
Briefly, [RFC-4](https://ngff.openmicroscopy.org/rfc/4/index.html) wants
to add an `orientation` field to the [`axis` object](https://ngff.openmicroscopy.org/specifications/dev/index.html#axes-metadata).

Should we want to represent that as an extension conformant with this
proposed extensibility scheme (to which we would tentatively give the ID
`https://ngff.openmicroscopy.org/rfc4/` in this document), this could be
done in two different ways.

> In this document, we use existing proposed RFCs to illustrate how the
> proposed extensibility scheme would work. This does _not_ imply that
> all RFCs should necessarily be turned into extensions (rather than
> being “built-in” into the specification), though that is clearly a
> possibility.

#### As a generic class extension
As a generic class extension, RFC-4 metadata could be added to the
`axis` object as follows:

```yaml
axes:
  - name: x
    type: space
    unit: millimeter
    extensions:
      https://ngff.openmicroscopy.org/rfc4/orientation:
        orientation:
          type: anatomical
          value: right-to-left
```

#### As a “natural extension”
Alternatively, a natural extension could also be used, since the `axis`
object qualifies as a natural extension point. Indeed, the specification
very much foresees that the accepted list of axis types could be
extended in the future:

> [the `field` type] SHOULD be one of the strings `array`, `space`,
> `time`, `channel`, `coordinate`, or `displacement` but MAY take other
> string values for custom axis types that are not part of this
> specification yet.

Therefore, as per the natural extension mechanism, a RFC-4 extension
could add its own type of axis, which would be expected to contain the
additional `orientation` key:

```yaml
axes:
  - name: x
    type: https://ngff.openmicroscopy.org/rfc4/orientedSpatialAxis
    unit: millimeter
    orientation:
      type: anatomical
      value: right-to-left
```

#### Making RFC-4 itself extensible
About the `type: anatomical` field in the `orientation` object, the text
of RFC-4 says:

> The `orientation` field […] MUST have a `type` field that specifies
> the orientation domain (e.g. “anatomical”) […]. Valid `type` strings
> are defined in this document – currently only “anatomical”.

This is clearly another natural extension point. Therefore, and
regardless of which of the two approaches above is chosen to implement
RFC-4 as an extension (as a “generic class extension” or as a “natural
extension”), the extension is itself naturally extensible, allowing
anyone to create another extension that adds a new type of orientation.

To make things more consistent, it would be recommended that the RFC-4
extension itself uses a URI-based `type` value to identify its own
type of orientation, instead of the `orientation` keyword – the use of
keywords to identify types should be reserved to the core specification.

So the base RFC-4 extension would now look like this:

```yaml
axes:
  - name: x
    type: https://ngff.openmicroscopy.org/rfc4/orientedSpatialAxis
    unit: millimeter
    orientation:
      type: https://ngff.openmicroscopy.org/rfc4/anatomicalOrientation
      value: right-to-left
```

instead of, as initially envisioned by the authors of RFC-4:

```yaml
axes:
  - name: x
    type: https://ngff.openmicroscopy.org/rfc4/orientedSpatialAxis
    unit: millimeter
    orientation:
      type: anatomical
      value: right-to-left
```

(that is, the `anatomical` keyword is replaced by an extension-specific
URI-based identifier).

> Or, if implemented as a class extension instead:
> 
> ```yaml
> axes:
>   - name: x
>     type: space
>     unit: millimeter
>     extensions:
>       https://ngff.openmicroscopy.org/rfc4/orientation:
>         orientation:
>           type: https://ngff.openmicroscopy.org/rfc4/anatomicalOrientation
>           value: right-to-left
> ```

Now let’s say someone wants to create an extension to add a `geological`
orientation. That extension (let’s call it
`https://example.org/ngff-for-geology/`) could then define its own type
of `orientation`, which could be used as follow:

```yaml
axes:
  - name: x
    type: https://ngff.openmicroscopy.org/rfc4/orientedSpatialAxis
    unit: millimeter
    orientation:
      type: https://example.org/ngff-for-geology/geologicalOrientation
      plunge: 227
      trend: 87
```

> Or, again if RFC-4 itself is implemented as a class extension instead:
> 
> ```yaml
> axes:
>   - name: x
>     type: space
>     unit: millimeter
>     extensions:
>       https://ngff.openmicroscopy.org/rfc4/orientation:
>         orientation:
>           type: https://example.org/ngff-for-geology/geologicalOrientation
>           plunge: 227
>           trend: 87
> ```

### Coordinate transformations
[Coordinate transformations](https://ngff.openmicroscopy.org/specifications/dev/index.html#coordinatetransformations-metadata)
are another clear extension point.

The current specification describes 12 different types of coordinate
transformations (`identity`, `translation`, `scale`, etc.), which all
have a common set of keys (`type`, `name`, `input`, and `output`), with
each specific type potentially having its own specific additional keys.

Under this scheme, any extension could define its own type of
transformation, provided that it is identified by a value that is
derived from the extension’s identifier as provided in the extension
manifest.

For example, the `https://example.org/my-ngff-extension/` could define a
`https://example.org/my-ngff-extension/NDCoordinate` type, specifying
that under that type, the coordinate transformation object is expected
to contain a key whose value is a list of _N_ monotonically increasing
arrays (one for each dimension, where the _i_ th value represents the
coordinate value for the _i_ th point of the array in that dimension).
That new coordinate transformation type could then be used whenever a
coordinate transformation object is expected.

> This includes the case where a type of transformation wraps another
> transformation (or set of transformations): the type newly added by
> the extension would be usable as one of the wrapped transformations.
>
> For example, the new type could be used in the value of the
> `forward` and/or `inverse` keys of the [`bijection`](https://ngff.openmicroscopy.org/specifications/dev/index.html#bijection-md)
> type.

### RFC-8 (“Collections”)
[RFC-8](https://ngff.openmicroscopy.org/rfc/8/index.html) proposes to
add a mechanism for storing collections of objects in a OME-Zarr
metadata file.

The core concept of RFC-8 is that of “nodes”, where a node is an object
that has, at a minimum, a _type_ and a _name_, optionally an _ID_ and a
generic _attributes_ dictionary. Nodes can be of different types, each
type being intended for a specific purpose and being characterized by
its own specific additional fields beyond the aforementioned four fields
that are common to all nodes.

The RFC-8 proposes a pre-defined set of three node types: `Collection`,
`Singlescale`, and `Multiscale`. The `Collection` type is arguably the
“key” type for the purpose of RFC-8; it has an additional `nodes` field
intended to store a list (a “collection”) of nodes of arbitrary types
(including other `Collection`-typed nodes).

Importantly, RFC-8 proposes that the top-level `ome` object may itself
be a node of any type.

> The text of RFC-8 says: “A Node object **may** be used as the root
> object of the `ome` key” (emphasis mine). But in fact, my
> understanding of the RFC is that this is not merely a _possibility_,
> but an _obligation_. Unless I have missed something, the RFC does not
> propose any other place in the existing structure of the `ome` object
> (as of the current version 0.6) where a node (or a collection of
> nodes) could be introduced. All examples given within the RFC use a
> node as the root `ome` object.

So, in effect, the RFC turns the `ome` object into an arbitrary node,
which can either be a “simple” node such as `Singlescale`, or a
“complex” node that can itself contain an arbitrary list of nodes (like
`Multiscale` or `Collection`).

#### Node types as a natural extension point
Node types undoubtedly qualify as a “natural extension point”. The RFC
is explicit that more types (beyond the aforementioned three types
pre-defined by the RFC) could be added by future RFCs.

Therefore, under the proposed extensibility scheme, any extension could
add its own type of node, identified by a URI built from the extension’s
own identifier as described [above](#natural-extensions) (e.g., an
extension with the ID `https://example.com/my-ngff-extension/` could add
a new node type `https://example.com/my-ngff-extension/FrobnicatorNode`).

> This could also apply to the `Path` object – another type of object
> introduced by the RFC, and which is used by by all three proposed
> types of nodes. The `Path` object also has a `type` field that
> dictates how the object is to be interpreted, with for now two
> possible values and an explicit mention that “future RFCs may propose
> additional path types” – this is another candidate for a natural
> extension point.

#### RFC-8 as an extension
RFC-8 could be made as an extension (under the currently proposed
extensibility scheme) to the core specification, similarly to what we
have shown [above for RFC-4](#rfc-4-axis-orientation).

Contrary to RFC-4 though, this would require a preliminary amendment to
the specification (RFC-4-as-an-extension would not require anything
special, and could be done from the current state of the specification).
Specifically, we would first need to explicitly turn the root `ome`
object into a natural extension point, by (i) giving it an explicit
`type` field and (ii) warning implementations that they can expect
various types of `ome` objects.

> Making the top-level `ome` object a natural extension point would
> undoubtedly be a breaking change, but this is what RFC-8 proposes to
> do anyway: if RFC-8 is adopted – as part of the core specification
> rather than as an extension –, the top-level `ome` object will
> become _de facto_ an extension point. The path proposed here does
> things in a different order (we make the top-level object an extension
> point _first_, which can then be exploited by the RFC-8 extension),
> but the end result is the same.

For backwards compatibility, we would consider that the contents
currently expected under the `ome` key (e.g. fields such as `scene`,
`plate`, `multiscales`, etc.) define the “default” type for the
top-level object, and that an `ome` object that does _not_ contain an
explicit `type` field should be assumed to be of that “default” type.

Then, once the top-level object is a natural extension point, our
hypothetical `https://ngff.openmicroscopy.org/rfc8/` extension would be
free to add its own types that can be used as the top-level object:

* `https://ngff.openmicroscopy.org/rfc8/Collection`,
* `https://ngff.openmicroscopy.org/rfc8/Multiscale`,
* `https://ngff.openmicroscopy.org/rfc8/Singlescale`.

> There would be several possible variations of this path. Notably,
> the `Multiscale` and `Singlescale` types would not necessarily need
> to be part of the same extension as the `Collection` type – they could
> be defined by their own dedicated extensions.

#### RFC-8’s own extensibility mechanisms
Beyond defining collections, RFC-8 also proposes its own extensibility
mechanisms.

First, it explicitly defines extension points such as node types, path
types, coordinate transformation types, etc, where a third-party can
define its own custom types.

Second, it provides each node type with an `attributes` dictionary that
may contain arbitrary keys, including custom keys that can be defined by
third-parties.

Both mechanisms are very similar to what is proposed in this very
document. A “custom type” is almost identical to what I call a
[“natural extension class“](#natural-extensions), and the `attributes`
dictionary can fulfil a role similar to that of the `extensions`
dictionary used to store the [“generic class extensions“](#generic-class-extension).

The main difference, and the main concern, is that RFC-8 proposes that
custom types and custom keys in the `attributes` dictionary be
identified by simple prefixes. As
[discussed](../EXTENSIBILITY.md#why-not-use-prefixed-names-for-class-extensions)
in the accompanying generic extensibility document, prefixed names do
_not_ constitute a proper scoping mechanism – at least not without a
namespace management system, that does not exist in native JSON. Under a
scheme of prefixed names, the only protection against two independently
developed extensions inadvertently (or even purposefully) using the same
prefix would reside in the RFC-8-proposed centralized registry of
extensions (whose use is recommended - SHOULD – but not mandatory). I
believe this is not enough. 

I would like to suggest that RFC-8 follow the recommendation of this
document, in that

* a custom type (be a node type, a path type, or whatever) should be
  identified by a **URI** that is derived from the identifier of the
  extension that defines it (extension that should itself be declared
  in the [extension manifest](#the-extensions-manifest));

* all custom keys defined by an extension should be enclosed in an
  extension-specific dictionary that is itself identified by a **URI**
  key that is again derived from the extension’s own identifier.

#### Other concerns
I have two further concerns with RFC-8’s use of the `attributes` field.
They are not really related to extensibility, but they could both be
fixed by the proper application of the extensibility scheme proposed in
this document, so I will discuss them here.

The first issue is that the `attributes` field, as currently used by
RFC-8, is intended to store both metadata defined by the specification,
and additional metadata defined by any third-party (with only the
presence or absence of a prefix distinguishing the two cases). I believe
this dual-use is a needless complication. It would in particular make it
more difficult to write formal schemas to describe and validate such
objects.

The second issue is that, when dealing with a `collection`-typed node,
an implementation has to peek into the contents of its `attributes`
dictionary to figure out what the collection actually represents. For
example, if it finds a `plate` attribute, then the collection represents
a plate; if it finds a `well` attribute, then the collection represents
a well. This again makes things needlessly more complicated, and is an
under-utilization of the `type` field, which does not, as it is
currently used, _fully_ identify the type of node we are dealing with.

I would suggest to fix both issues at once by (i) defining as many
subclasses of `collection` as needed to represent all possible types
of collection (e.g. a `plate` type, a `well` type, etc.) and (ii) for
each of those subclasses, moving their specific attributes out of the
`attributes` dictionary and into the node itself.

That is, a plate would _not_ be represented as follows:

```yaml
ome:
  version: "0.x",
  type: collection
  name: hcs-plate-001
  attributes:
    plate:
      acquisitions: ...
      columns: ...
      rows: ...
  nodes: ...
```

but rather like this:

```yaml
ome:
  version: "0.x"
  type: plate
  name: hcs-plate-001
  plate:
    acquisitions: ...
    columns: ...
    rows: ...
  nodes: ...
```

where `plate` is defined by the RFC-8 as a subclass of `collection` that
MUST contain a `plate` field directly in the node itself (_not_ in the
`attributes` dictionary, which would be reserved for third-party
metadata – and which could then possibly be replaced by the generic
`extensions` key envisioned in this document, so as to avoid having two
different dictionaries both intended to store third-party metadata).

## Discussions

### About identifiers for elements of the “core” specification
This proposal deviates somewhat from the [generic extensibility scheme](../EXTENSIBILITY.md),
which would [recommend](../EXTENSIBILITY.md#on-the-use-of-uris-as-identifiers)
that _all_ type identifiers (regardless of whether they identify a type
from the core specification or from an extension) should be URI-based.
For example, it would recommend that the `space` keyword (used to
identify a type of axis) should be replaced by something like
`https://ngff.openmicroscopy.org/core/spatialAxis`).

This would avoid having a “mix” of non-URI, keyword-style identifiers
(for things coming from the core specification) and URI-based
identifiers (for things coming from extensions). But this would then
necessarily be a breaking change from the current version of the
specification, which may not be worth the trouble (though that is of
course debatable).

Conceptually, the use of keyword-style identifiers for elements of the
core specification can be thought of as if there was a kind of “default”
extension that uses `https://ngff.openmicroscopy.org/core/` as its ID,
and all identifiers that do not start by a base URI are interpreted as
if they were prefixed with that base URI, thereby marking them as
belonging to that “default extension”.

### Representing (most of) the specification as extensions?
The extensibility scheme envisioned here is primarily intended to
represent, well, “extensions” to the OME-Zarr specification. That is,
parts of the content of an OME-Zarr file (or rather, of the contents of
the `ome` object within a `zarr.json` file) that is not defined by the
specification, but is instead defined by a “third party”.

However, the “third party” could very well be the maintainers of the
OME-Zarr specification themselves, according to two different scenarios.

In the first scenario, any addition to the specification after the 1.0
version would have to take the form of an extension – even if the
addition is proposed by some maintainers of the specification and is
accepted (likely through the standard RFC pathway) by the OME-Zarr
community as a whole.

Under that scenario, OME-Zarr 1.0, in whatever form it will have when it
will exist, would be “frozen”, and any subsequent minor version (e.g.
1.1) would merely consist of a list of “extensions” that would be
considered important enough and well accepted enough that
implementations SHOULD support them the same way they would support
them if they were defined in the specification (maybe even “MUST”
support them, though the exact requirement level may be decided on a
case-by-case basis for each extension).

A second, more “radical” scenario would be to formally describe most,
possibly _all_ features of the 1.0 specification, as “extensions”.

Under that scenario, the OME-Zarr 1.0 specification would consist in a
minimal framework (mostly describing the extension system itself) and a
list of “core extensions”.

> Of note, should we choose to go that route, we would probably need to
> use another name as “extension”, which could rapidly become confusing.
> I would suggest a name like “featureset” or similar. We would thus
> have “core featuresets” (part of the OME-Zarr 1.0 spec), and “external
> featuresets” (actual “extensions” in the sense that they extend the
> specification; but formally they would not be any different from core
> featuresets).

Of note, just because a featureset is a “core” featureset would not mean
that it is mandatory to implement (the same way that, just because a
given feature is currently described in the spec, it does not mean it is
necessarily marked as a “MUST” feature). Each featureset can have its
own requirement level.
