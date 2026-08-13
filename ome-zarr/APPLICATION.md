# Applying the extensibility scheme to OME-Zarr

This document outlines some scenarios for applying the extensibility
scheme described in [EXTENSIBILITY.md](../EXTENSIBILITY.md) to the
[OME-Zarr](https://ngff.openmicroscopy.org) format.

## Preliminary step
Regardless of which path is adopted to apply the extensibility scheme,
a common preliminary step will be to add the 
[“extension manifest”](../EXTENSIBILITY.md#extension-management-layer)
envisioned in the aforementioned document, intended to allow a
`zarr.json` file to self-declare which extensions it is using.

That manifest should be located in the top-level `ome` object, where it
can immediately be found.

Therefore, in the rest of this document we are assuming that the
OME-Zarr specification is prealably amended to allow a
`extension_manifest` key in the `ome` object, as in the following
example:

```yaml
ome:
  extension_manifest:
    - id: https://example.com/extension1/
    - id: https://example.org/extension2/
```

(all examples in this document will be provided in YAML rather than
JSON, for readability) where `https://example.com/extension1` and
`https://example.org/extension2/` are possible extensions.

> The manifest can, and ideally should, contain more informations about
> an extension than just its ID – in particular, it should provide at
> least one link to a machine-readable schema formally describing the
> extension. But the details for that do not really matter at the moment
> and can be added later.

Another preliminary step is that most objects under the `ome` key
(including the `ome` object itself) must be made to accept the implicit
`extensions` key, intended to hold the [class extension objects](../EXTENSIBILITY.md#class-extensions).

## Practical considerations
Unless otherwise specified, the use cases in the following sections all
assume that we would want to apply the extensibility scheme to OME-Zarr
in a “minimally invasive” way – that is, in a way that changes the
existing specification (in its current 0.6 version) as little as
possible.

In particular, this means that in many cases, we explicitly deviate from
the strong recommendation, set forth in the
[generic extensibility scheme document](../EXTENSIBILITY.md#on-the-use-of-uris-as-identifiers),
that all identifiers should be URIs. We still strongly recommend that
identifiers for _extensions_ (all things that are _not_ already in the
specification) should be URI-based (and all examples given this document
will be); but identifiers for classes that already exist in the
specification (even if they may not be formally described as “classes”
yet) retain their current, non-URI-based identifiers.

## RFC-4 (“Axis Orientation”)
Briefly, [RFC-4](https://ngff.openmicroscopy.org/rfc/4/index.html) wants
to add a `orientation` field to the [`axis` object](https://ngff.openmicroscopy.org/specifications/dev/index.html#axes-metadata).

Should we want to represent that as extension conformant with the
proposed extensibility scheme (to which we would tentatively give the ID
`https://ngff.openmicroscopy.org/rfc4/` in this document), this could be
done in two different ways.

### As a “class extension”
Following the [class extension mechanism](../EXTENSIBILITY.md#class-extensions),
adding the `orientation` field to the `axis` object would look like this:

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

(It would then be expected that the schema formally describing the RFC4
extension provide a description of the
`https://ngff.openmicroscopy.org/rfc4/orientation`) object, specifying
that it accepts two keys `type` and `value` (and which values are
accepted for those two keys).

### As a “natural extension”
Alternatively, the `axes` key in the current OME-Zarr specification
could be turned into a natural extension point. In fact, the
specification very much foresees that the accepted list of axis types
could be extended in the future:

> [the `field` type] SHOULD be one of the strings `array`, `space`,
> `time`, `channel`, `coordinate`, or `displacement` but MAY take other
> string values for custom axis types that are not part of this
> specification yet.

This is clearly intended as an extension point, so we could make it a
[“natural extension point”](../EXTENSIBILITY.md#natural-extension-classes)
as per the extensibility scheme by:

* formally defining a base `axis` class, that would contain all the
  fields common to all types of axis as currently defined by the
  specification (`name`, `type`, `unit`, `longname`, and `discrete`),
  with the `type` field acting as the “type designator” field;
* defining one subclass of `Axis` for each of the currently recognized
  types:
    * `array`,
    * `space`,
    * `time`,
    * `channel`,
    * `coordinate`,
    * `displacement`.

> This is where we deviate from the generic extensibility scheme, which
> would recommend that those classes are identified by URI-based
> identifiers (e.g. something like
`https://ngff.openmicroscopy.org/core/array`) rather than such short
> names. But doing so would be breaking change with the current version
> of the specification, which may not be worth the trouble (though that
> is of course debatable).
>
> Conceptually, this can be thought of as if there was a kind of
> “default” extension that uses `https://ngff.openmicroscopy.org/core/`
> as its ID, and all identifiers that do not start by a base URI are
> interpreted as if they were prefixed with that base URI, thereby
> marking them as beloning to that “default extension”.

So far, this does not change anything about what the data would look
like in a OME-Zarr fileset. A file that does not use the
“RFC-4 extension“ could look like this:

```yaml
axes:
  - name: x
    object_type: space
    unit: millimeter
```

which is exactly how it would look like under the current version 0.6 of
the specification. The changes above are only about describing the
`axis` object and what it can contain in a more formal manner.

With such a basis, our hypothetical RFC-4 extension would then simply
need to define its own subclass of the `space` class (tentatively named
`https://ngff.openmicroscopy.org/rfc4/orientedSpatialAxis`), containing
the additional `orientation` field. (Of note, it should subclass the
`space` class rather than the base `axis` class because RFC-4 explicitly
intends for the `orientation` field to be application _only_ for spatial
axis.) In a OME-Zarr file, this could look like this:

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

This is a prime candidate for a “natural extension point“.

Therefore, and regardless of which of the two approaches above is chosen
to implement RFC-4 as an extension (as a “class extension” or as a
“natural extension class”), we can make it so that the extension itself
is naturally extensible (allowing anyone to create another extension
that adds a new type of orientation).

All the extension would need to do is to

* define a base `https://ngff.openmicroscopy.org/rfc4/orientation` base
  class with a type designator field;
* specify that the `orientation` field must contain an instance of that
  class (which implies that it can contain an instance of any of its
  subclasses);
* define a `https://ngff.openmicroscopy.org/rfc4/anatomicalOrientation`
  class representing what is currently the only supported type of
  orientation.

Here is what it would look like with just the base RFC-4 extension:

```yaml
axes:
  - name: x
    type: https://ngff.openmicroscopy.org/rfc4/orientedSpatialAxis
    unit: millimeter
    orientation:
      type: https://ngff.openmicroscopy.org/rfc4/anatomicalOrientation
      value: right-to-left
```

> Or, if RFC-4 itself is implemented as a class extension instead:
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
orientation. All they would need to do in their extension (let’s call it
`https://example.org/ngff-for-geology/`) is to define their own subclass
of `https://ngff.openmicroscopy.org/rfc4/orientation`, which could then
be used as follows:

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

## Coordinate transformations
[Coordinate transformations](https://ngff.openmicroscopy.org/specifications/dev/index.html#coordinatetransformations-metadata)
are another prime candidate for a natural extension point.

The current specification describes 12 different types of coordinate
transformations (`identity`, `translation`, `scale`, etc.), which all
have a common set of keys (`type`, `name`, `input`, and `output`), with
each specific type potentially having its own specific additional keys.

To transform that into a natural extension point as per the envisioned
extensibility mechanism, one would formally define a base
`coordinateTransformation` class, with the same `name`, `type`,
`input`, and `output` field as in the current specification (`type`
being the type designator for the class). Then, we can define one
subclass for each specific type of transformation as needed, e.g.:

* a `mapAxis` class, with an additional `mapAxis` field;
* a `projectAxis` class, with additional `createdOutputs` and
  `droppedInputs` fields;
* a `translation` class, with an additional `translation` field;
* a `bijection` class, with additional `forward` and `inverse` fields;
* etc.

As with the [previous example](#rfc-4-axis-orientation), so far this
does not change anything about how coordinate transformations metadata
are expected to be written on disk. The above merely formalizes the
current text of the spec in terms of “classes” and “subclasses”.

Any extension could then define its own subclass of
`coordinateTransformation` to represent an additional way of specifying
coordinate transformations. For example, one could create a
`https://example.org/my-ngff-extension/` that defines a
`https://example.org/my-ngff-extension/NDCoordinate` class that can
store a list of _N_ monotonically increasing arrays (one for each
dimension, where the _i_ th value represents the coordinate value for
the _i_ th point of the array in that dimension). That new coordinate
transformation type could then be used whenever a coordinate
transformation object is expected.

> In cases where a type of transformation wraps another transformation
> (or set of transformations), the type newly added by the extension
> would automatically be usable as one of the wrapped transformation.
>
> For example, the [`bijection`](https://ngff.openmicroscopy.org/specifications/dev/index.html#bijection-md)
> class would be defined in such a way that its `forward` and `inverse`
> fields both accept an instance of the base `coordinateTransformation`
> class, so that they would automatically accept any of the defined
> subclasses (including subclasses that do not exist in the core
> specification but that would be added by an extension).
