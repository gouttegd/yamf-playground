 This directory is intended to contain the development version of the
 LinkML schema that formally represents the Light Microscopy (LiMi)
 model – at least for the time being (at some point this will likely be
 moved to a dedicated repository).

# Notes

## Terminology
Within this document and within the LinkML files themselves, “LiMi
Schema” or “the Schema” (uppercase S) refers to the entire LinkML
representation of the LiMi model, regardless of how many files that
representation is spread into. The word “schema” (lowercase S) refers
to an individual LinkML schema (a single `.yaml` file) that may only
contain a part of the overall LiMi Schema.

In other words:

* The Schema: The set of all the `.yaml` files in this directory.
* A schema: A single `.yaml` file in this directory.

## Source of truth
The Schema is currently designed based on the version of the LiMi model
that is available on <https://github.com/WU-BIMAC/NBOMicroscopyMetadataSpecs/tree/master/Model/stable%20version/v02-01>,
which – to the best of my knowledge – is the latest “released” version.

# Design considerations

## Splitting the Schema
Given the sheer size of the LiMi model, it would be impractical to make
a  single large, monolithic LinkML schema to represent the model in its
entirety. We therefore need to break the model into parts that can each
by represents in its own, reasonably self-contained schema.

There are several ways to split the model:

**(A)** Along the “Core”, “Basic”, “Advanced”, and “Confocal” modules.
The LiMi model is _already_ divided in “modules” (called “extensions” by
the LiMi authors, though we will refrain from using that term here)
which are organized in a concentric manner:

* the “Core” module sits at the center;
* the “Basic” module builds on top (extends) the “Core” module;
* the “Advanced” module likewise builds on of the “Basic” module;
* and the “Confocal” module likewise builds on top of the “Advanced”
  module.

**(B)** Along the “tiers”. The LiMi model is _also_ already divided in
three different “tiers”, simply called “tier 1”, “tier 2”, and “tier 3”.

**(C)** Along “logical areas”, e.g. “hardware specifications” on one
hand and “image acquisition settings” on another hand.

All those subdivisions intersect with each other, e.g. all modules
contain both stuff about hardware specifications and about image
acquisition settings, and contain stuff from each of the three tiers.

For the practical division of the Schema into individual schemas, we
will mostly follow **(A)**. Division along the “logical areas” would not
allow to split the model into enough parts to make the split Schema much
more manageable than a monolithic schema, and the “tiers” system is more
intended to be useful at the instance level, as a way to assess the
“richness” of the available metadata.

The Schema will therefore be divided into the following files:

* `limi-base.yaml`: Fundamental stuff that may be used everywhere else
  (e.g., some commonly used enumerations or basic types). This does
  _not_ correspond to a LiMi-defined “module”, this is purely a LinkML
  artefact.
* `limi-core.yaml`: All the definitions from the “Core” module. This
  will import `limi-base`.
* `limi-basic`: All the definitions from the “Basic” module. This will
  import `limi-core` and add the definitions that are specific to the
  ”Basic” module.
* `limi-advanced`: All the definitions from the “Advanced” module.
  Likewise, this will import `limi-basic` and add the definitions that
  are specific to the “Advanced” module.
* `limi-confocal`: All the definitions from the “Confocal” module.
  Likewise, this will import `limi-advanced` and add the definitions
  that are specific to the “Confocal” module.

This organisation means that an implementation that is only interested
in supporting, say, the “Basic” module (that is, “Core+Basic”) can
simply use the `limi-basic.yaml` schema and ignore the
`limi-advanced.yaml` and `limi-confocal.yaml` schemas.

Some modules _may_ be further broken down into more “logical areas” if
we feel this is needed to prevent a single module file from becoming too
large to be practical to maintain. For example, the “Basic” module _may_
be broken down into `limi-basic-hardware.yaml` and
`limi-basic-settings.yaml`. In this case, we will still have a
`limi-basic.yaml` minimal schema that will import all the
`limi-basic-*.yaml` schemas, so that implementations do not need to
worry about such subdivisions (they can just use `limi-basic.yaml`
without even having to know that the content of this module is spread
into two other “sub-schemas”).

## Naming conventions
For now, we will as much as possible follow the LinkML conventions:

* class and enumeration names will use `PascalCase`;
* slot/attribute names will use `snake_case`.

Of note, class/enumeration names are not, in fact, really important in
a LinkML schema, because they almost never appear in _instance data_
(the only exception is when “type designators” are used). By contrast,
slot/attribute names are the names that will end up being used in JSON-
(or YAML-) formatted instance data.

Because LinkML does not support namespacing for class or enumeration
names, all class and enumeration names will be prefixed with a small
keyword identifying the “module” that defines them. For example, the
class representing the “Microscope Stand” object from the “Core” module
will be named `LiMiCoreMicroscopeStand`; the class representing the same
object from the “Advanced” module (with all the additional attributes
defined in that module) will be named `LiMiAdvMicroscopeStand`.

## XML-to-LinkML notes
The LiMi model was clearly initially designed with XML in mind (likely
because it started with the aim of extending the OME model). This
“XML-oriented” design has some implications for how the model is to be
formalised in LinkML.

TODO: explain the differences between XML/XSD and JSON/LinkML,
especially regarding how objects are explicitly typed and the role of
the name of an element (XML) or class (LinkML). Then explain the role
of type designators in LinkML and how they are used to represent what
the LiMi model calls “Abstract Parent Element“ (APE).
