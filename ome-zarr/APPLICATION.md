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

is _de facto_ a natural extension point.

The present scheme proposes that at any such point, an extension can add
its own type with its own interpretation. The only requirement is that
the type MUST be identified with a value that is derived from the
extension’s identifier (as provided in the extension manifest). That is,
if the extension has the ID `https://example.com/my-ngff-extension/`,
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
to add a `orientation` field to the [`axis` object](https://ngff.openmicroscopy.org/specifications/dev/index.html#axes-metadata).

Should we want to represent that as an extension conformant with this
proposed extensibility scheme (to which we would tentatively give the ID
`https://ngff.openmicroscopy.org/rfc4/` in this document), this could be
done in two different ways.

### As a generic class extension
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

### As a “natural extension”
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

### Making RFC-4 itself extensible
About the `type: anatomical` field in the `orientation` object, the text
of RFC-4 says:

> The `orientation` field […] MUST have a `type` field that specifies
> the orientation domain (e.g. “anatomical”) […]. Valid `type` strings
> are defined in this document – currently only “anatomical”.

This is clearly another natural extension point. Therefore, and
regardless of which of the two approaches above is choosen to implement
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
> the extension would be usable as one of the wrapped transformation.
>
> For example, the new type could be used in the value of the
> `forward` and/or `inverse` keys of the [`bijection`](https://ngff.openmicroscopy.org/specifications/dev/index.html#bijection-md)
> type.

## Discussions

### About identifiers for elements of the “core” specification
This proposal deviates somewhat from the [generic extensibility scheme](../EXTENSIBILITY.md),
which would [recommend](../EXTENSIBILITY.md#on-the-use-of-uris-as-identifiers)
that _all_ type identifiers (regardless of whether they identify a type
from the core specification or from an extension) should be URI-based.
For example, it would recommend that the `space` keyword (used to
identify a type of axis) should be replaced by something like
`https://ngff.openmicroscopy.org/core/spatialAxis`).

This would avoid haxing a “mix” of non-URI, keyword-style identifiers
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
