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

* `limi-core.yaml`: All the definitions from the “Core” module.
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
large to be practical to maintain. For example, currently the “Core”
module is broken down into:

* `limi-core-units.yaml`: All the enumerations representing units.
* `limi-core-light-sources.yaml`: All the classes representing light
  sources (including any supporting enums as needed).
* `limi-core.yaml`: Everything else from the “Core” module. This
  imports `limi-core-units.yaml` and `limi-core-light-sources.yaml`.

Importantly, “subschemas” such as `limi-core-light-sources.yaml` are
_not_ intended to be usable on their own, as independent schemas. They
exist merely to make the schemas more manageable by keeping the size of
each schema under some reasonable limits. Only the “top-level” schemas
(such as `limi-core.yaml` or `limi-basic.yaml`) are intended to be used
directly.

## Naming conventions
For now, we will as much as possible follow the LinkML conventions:

* class and enumeration names will use `PascalCase`;
* slot/attribute names will use `snake_case`.

Of note, class/enumeration names are not, in fact, really important in
a LinkML schema, because they almost never appear in _instance data_
(the only exception is when “type designators” are used). By contrast,
slot/attribute names are the names that will end up being used in JSON-
(or YAML-) formatted instance data.

All class and enumeration names will be prefixed with the `LiMi`
keyword, e.g. the “MicroscopeStand” class will be named
`LiMiMicroscopeStand`. This is because LinkML does not support
namespacing for class or enumeration names. Adding a schema-specific
prefix will make it possible for a third-party wishing to reuse all or
parts of the LiMi Schema to import the LiMi Schema in their own schemas
without fearing possible name conflicts.

## Class design

### class with attributes defined in several modules
A few “elements” in the LiMi model have a definition that is spread over
several modules. That is, the element is initially defined in either the
“Core” or “Basic” modules, but its expected content is refined in a
higher-level module (adding more sub-elements).

For example, the _Instrument_ element is initially defined in the “Core”
module, but several sub-elements are added in the “Basic” and “Advanced”
modules.

To formalize such an element in LinkML, the attributes are defined in
module-specific “mixins”, where each mixin only defines the attributes
that belong to a given module. The actual class representing the element
is then assembled simply by composing all the module-specific mixins
together.

### Representing “Abstract Parent Element” classes
The LiMi model abundantly uses what it calls “Abstract Parent Element”
classes or **APEs**. (As many other things in the model, the name
reflects the XML origin of the model.)

APEs are represented in LinkML using **type-designated classes**. That
is, the APE is represented using a class that has a type designator.
Members of the “substitution group” (which are expected to be used in
place of the APE) are defined as subclasses of the APE class.
