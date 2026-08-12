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

## RFC-4 (“Axis Orientation”)
Briefly, [RFC-4](https://ngff.openmicroscopy.org/rfc/4/index.html) wants
to add a `orientation` field to the [`axis` object](https://ngff.openmicroscopy.org/specifications/dev/index.html#axes-metadata).

Should we want to represent that as extension conformant with the
proposed extensibility scheme (to which we would tentatively give the ID
`https://ngff.openmicroscopy.org/rfc4/` in this document), this could be
done in two different ways.

### As a “class extension”
This is the most straightforward (but not necessarily the most
_elegant_, though that is eminently subjective) way. This would be a
“pure” extension, in that it would be usable with the current OME-Zarr
format almost as it currently is (as of version 0.6) – the only changes
needed to the base format would be the changes required to allow the use
of the extensibiltity system itself, as outlined
[in the first section of this document](#preliminary-step).

Following the [class extension mechanism](../EXTENSIBILITY.md#class-extensions),
adding the `orientation` field to the `axis` object would be like this:
```yaml
axes:
  - name: x
    type: space
    unit: millimeter
    extensions:
      https://ngff.openmicroscopy.org/rfc4/Orientation:
        orientation:
          type: anatomical
          value: right-to-left
```

(It would then be expected that the schema formally describing the RFC4
extension provide a description of the
`https://ngff.openmicroscopy.org/rfc4/Orientation`) object, specifying
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
by:

* making the `axes` field accept a list of
  `https://ngff.openmicroscopy.org/core/Axis` objects;
* defining the `https://ngff.openmicroscopy.org/core/Axis` object as
  containing all the fields common to all types of axis as currently
  defined by the specification (`name`, `unit`, `longname`, `discrete`)
  plus a `object_type` field acting as the “type designator”
* defining one subclass of `https://ngff.openmicroscopy.org/core/Axis`
  for each of the currently recognized types (e.g.
  `https://ngff.openmicroscopy.org/core/ArrayAxis`,
  `https://ngff.openmicroscopy.org/core/SpaceAxis`, etc).

With those change, a file conformant to the base specification (before
adding the RFC-4 extension) could look like this:

```yaml
axes:
  - name: x
    object_type: https://ngff.openmicroscopy.org/core/SpaceAxis
    unit: millimeter
```

With such a basis, our hypothetical RFC-4 extension would then simply
need to define its own subclass of
`https://ngff.openmicroscopy.org/core/SpaceAxis` containing the
additional `orientation` field. (It should subclass the 
`https://ngff.openmicroscopy.org/core/SpaceAxis`
class rather than the base `https://ngff.openmicroscopy.org/core/Axis`
class because RFC-4 explicitly intends for the `orientation` field to be
applicable _only_ for spatial axis.) In a OME-Zarr file, this could look
like this:

```yaml
axes:
  - name: x
    object_type: https://ngff.openmicroscopy.org/rfc4/OrientedSpatialAxis
    unit: millimeter
    orientation:
      type: anatomical
      value: right-to-left
```

### Making RFC-4 itself extensible
About the `type: anatomical` field in the `orientation` object, the text
text of RFC-4 says:

> The `orientation` field […] MUST have a `type` field that specifies
> the orientation domain (e.g. “anatomical”) […]. Valid `type` strings
> are defined in this document – currently only “anatomical”.

This is a prime candidate for a “natural extension point“.

Therefore, and regardless of which of the two approaches above is chosen
to implement RFC-4 as an extension (as a “class extension” or as a
“natural extension class”), we can make it so that the extension itself
is naturally extensible (allowing anyone to create another extension
that adds a new type of orientation).

All that would be needed is to

* define a base `https://ngff.openmicroscopy.org/rfc4/Orientation` class
  with a type designator (`object_type`) field;
* define a `https://ngff.openmicroscopy.org/rfc4/AnatomicalOrientation`
  class representing the only currently supported type of orientation.

Here is what it would look like with just the base RFC-4 extension:

```yaml
axes:
  - name: x
    object_type: https://ngff.openmicroscopy.org/rfc4/OrientedSpatialAxis
    unit: millimeter
    orientation:
      object_type: https://ngff.openmicroscopy.org/rfc4/AnatomicalOrientation
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
>       https://ngff.openmicroscopy.org/rfc4/Orientation:
>         orientation:
>           object_type: https://ngff.openmicroscopy.org/rfc4/AnatomicalOrientation
>           value: right-to-left
> ```

Now let’s say someone wants to create an extension to add a `geological`
orientation. All they would need to do in their extension (let’s call it
`https://example.org/ngff-for-geology/`) is to define their own subclass
of `https://ngff.openmicroscopy.org/rfc4/Orientation`, which could then
be used as follows:

```yaml
axes:
  - name: x
    object_type: https://ngff.openmicroscopy.org/rfc4/OrientedSpatialAxis
    unit: millimeter
    orientation:
      object_type: https://example.org/ngff-for-geology/GeologicalOrientation
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
>       https://ngff.openmicroscopy.org/rfc4/Orientation:
>         orientation:
>           object_type: https://example.org/ngff-for-geology/GeologicalOrientation
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
extensibility mechanism, one would define a base class with a name like
`https://ngff.openmicroscopy.org/core/CoordinateTransformation`, with
the same `name`, `input`, and `output` fields as in the current
specification, and the `type` field being replace with `object_type`
(the type designator for the class). Then, we can define one subclass
for each specific type of transformation as needed, e.g.:

* a `https://ngff.openmicroscopy.org/core/MapAxisCoordinateTransformation`,
  with an additional `mapAxis` field;
* a `https://ngff.openmicroscopy.org/core/ProjectAxisCoordinateTransformation`,
  with additional `createdOutputs` and `droppedInputs` fields;
* a `https://ngff.openmicroscopy.org/core/TranslationCoordinateTransformation`,
  with an additional `translation` field;
* etc.

Once coordinate transformations are turned into an extension point, then
any extension could define its own subclass of `https://ngff.openmicroscopy.org/core/CoordinateTransformation`.
For example, one could create a `https://example.org/my-ngff-extension/NDCoordinateTransformation`
class that can store a list of _N_ monotonically increasing arrays (one
for each dimension, where the _i_ th value represents the coordinate
value for the _i_ th point of the array in that dimension). That new
coordinate transformation type could then be used whenever a coordinate
transformation object is expected.

> Importantly, this transformation into a natural extension would
> recursively apply to the types of transformations that wrap other
> transformations.
> 
> For example, the `https://ngff.openmicroscopy.org/core/BijectionCoordinateTransformation`
> class, which would represent the current [bijection](https://ngff.openmicroscopy.org/specifications/dev/index.html#bijection-md)
> type of transformation, would be defined in such a way that its
> `forward` and `inverse` fields both accept a `https://ngff.openmicroscopy.org/core/CoordinateTransformation`
> object, so that they would accept an object of any of the defined
> subclasses (including subclasses that do not exist in the core
> specification but that would be added by an extension).
