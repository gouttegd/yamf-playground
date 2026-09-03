This directory is intended to contain the development version of the LinkML
schema that formally represents the Light Microscopy (LiMi) model – at least for
the time being (at some point this will likely be moved to a dedicated
repository).

# Notes

## Terminology

Within this document and within the LinkML files themselves, “LiMi Schema” or
“the Schema” (uppercase S) refers to the entire LinkML representation of the
LiMi model, regardless of how many files that representation is spread into. The
word “schema” (lowercase S) refers to an individual LinkML schema (a single
`.yaml` file) that may only contain a part of the overall LiMi Schema.

In other words:

- The Schema: The set of all the `.yaml` files in this directory.
- A schema: A single `.yaml` file in this directory.

## Source of truth

The Schema is currently designed based on the version of the LiMi model that is
available on
<https://github.com/WU-BIMAC/NBOMicroscopyMetadataSpecs/tree/master/Model/stable%20version/v02-01>,
which – to the best of my knowledge – is the latest “released” version.

# Design considerations

## Splitting the Schema

Given the sheer size of the LiMi model, it would be impractical to make a single
large, monolithic LinkML schema to represent the model in its entirety. We
therefore need to break the model into parts that can each by represents in its
own, reasonably self-contained schema.

There are several ways to split the model:

**(A)** Along the “Core”, “Basic”, “Advanced”, and “Confocal” modules. The LiMi
model is _already_ divided in “modules” (called “extensions” by the LiMi
authors, though we will refrain from using that term here) which are organized
in a concentric manner:

- the “Core” module sits at the center;
- the “Basic” module builds on top (extends) the “Core” module;
- the “Advanced” module likewise builds on of the “Basic” module;
- and the “Confocal” module likewise builds on top of the “Advanced” module.

**(B)** Along the “tiers”. The LiMi model is _also_ already divided in three
different “tiers”, simply called “tier 1”, “tier 2”, and “tier 3”.

**(C)** Along “logical areas”, e.g. “hardware specifications” on one hand and
“image acquisition settings” on another hand.

All those subdivisions intersect with each other, e.g. all modules contain both
stuff about hardware specifications and about image acquisition settings, and
contain stuff from each of the three tiers.

For the practical division of the Schema into individual schemas, we will mostly
follow **(A)**. Division along the “logical areas” would not allow to split the
model into enough parts to make the split Schema much more manageable than a
monolithic schema, and the “tiers” system is more intended to be useful at the
instance level, as a way to assess the “richness” of the available metadata.

The Schema will therefore be divided into the following files:

- `limi-core.yaml`: All the definitions from the “Core” module.
- `limi-basic`: All the definitions from the “Basic” module. This will import
  `limi-core` and add the definitions that are specific to the ”Basic” module.
- `limi-advanced`: All the definitions from the “Advanced” module. Likewise,
  this will import `limi-basic` and add the definitions that are specific to the
  “Advanced” module.
- `limi-confocal`: All the definitions from the “Confocal” module. Likewise,
  this will import `limi-advanced` and add the definitions that are specific to
  the “Confocal” module.

This organisation means that an implementation that is only interested in
supporting, say, the “Basic” module (that is, “Core+Basic”) can simply use the
`limi-basic.yaml` schema and ignore the `limi-advanced.yaml` and
`limi-confocal.yaml` schemas.

Some modules _may_ be further broken down into more “logical areas” if we feel
this is needed to prevent a single module file from becoming too large to be
practical to maintain. For example, currently the “Core” module is broken down
into:

- `limi-core-units.yaml`: All the enumerations representing units.
- `limi-core-hardware.yaml`: All the classes representing the “Hardware
  specifications” part of the “Core module”. (including any supporting enums as
  needed).
- `limi-core.yaml`: The “Core” module itself. This imports
  `limi-core-units.yaml` and `limi-core-hardware.yaml`.

Importantly, “subschemas” such as `limi-core-hardware.yaml` are _not_ intended
to be usable on their own, as independent schemas. They exist merely to make the
schemas more manageable by keeping the size of each schema under some reasonable
limits. Only the “top-level” schemas (such as `limi-core.yaml` or
`limi-basic.yaml`) are intended to be used directly.

## Formalizing the model in LinkML

The LiMi model has clearly been initially designed with XML/XSD in mind. This is
very apparent from the terminology it uses (things such as “element” or
“substitution group”) and from the way “elements” are expected to have
“sub-elements”. This likely reflects the fact that the LiMi model was initially
expected to completely supersede the OME model (which was similarly designed
with/for XML/XSD).

### Elements to LinkML

As a general rule, a LiMi “element” is formalized in the LinkML LiMi Schema as a
LinkML _class_.

The name of the LinkML class is the name of the LiMi element (in the original
PascalCase) prefixed with `LiMi` (for example, a _TransmittanceRange_ element
becomes the _LiMiTransmittanceRange_ class). The rationale behind the `LiMi`
prefix is because LinkML does not support namespacing for class names. Adding a
schema-specific prefix will make it possible for a third-party wishing to reuse
all or parts of the LiMi Schema to import the LiMi Schema in their own schema(s)
without fearing possible name conflicts.

#### class with attributes defined in several modules

A few elements in the LiMi model have a definition that is spread over several
modules. That is, the element is initially defined in either the “Core” or
“Basic” modules, but its expected content is refined in a higher-level module
(adding more sub-elements).

For example, the _Instrument_ element is initially defined in the “Core” module,
but several sub-elements are added in the “Basic” and “Advanced” modules.

To formalize such an element in LinkML, the attributes are defined in
module-specific “mixins”, where each mixin only defines the attributes that
belong to a given module. The actual class representing the element is then
assembled simply by composing all the module-specific mixins together.

#### Representing “Abstract Parent Element” classes

The LiMi model abundantly uses what it calls “Abstract Parent Element” classes
or **APEs**.

APEs are represented in LinkML using **type-designated classes**. That is, the
APE is represented using a class that has a type designator. Members of the
“substitution group” (which are expected to be used in place of the APE) are
defined as subclasses of the APE class.

### Fields to LinkML

The “fields” that make up a LinkML element become the _slots_, or _attributes_,
of the corresponding LinkML class.

Of note, we favour class-specific _attributes_ over schema-wide _slots_, to
avoid cluttering the schema namespace with lots of slot names. We always use an
attribute in the first instance, and only promote the attribute into a slot if
the same attribute would be used, with exactly the same meaning, in more than
one class.

Following LinkML’s own recommendations, we use `snake_case` for the name of
slots or attributes. For example, a `PeakWavelength` field becomes a
`peak_wavelength` attribute.

#### Cardinality and requirements/recommendations

If a field is supposed to be possibly used more than once, this is naturally
formalized with `multivalued: true` metaslot in the attribute definition.

If the model mandates that the field must be filled, this is formalized with a
`required: true` metaslot. If the model recommends the field to be filled, this
is formalized with a `recommended: true` metaslot.

We do not (yet!) specify how to represent the _tier_ the field belongs to.

#### Fields expecting a controlled list of values

If a field expects a controlled list of values, the list of values is formalized
as a LinkML _enumeration_. The enumeration is named following the same
convention as for classes: as `LiMi` followed by a PascalCased name.

The enumeration should be defined in the same schema in which it is used. If the
enumeration is used at several places in the model (especially if it used across
module boundaries), consider putting it into a dedicated schema that can then be
imported wherever needed.

#### Fields expecting a value with a unit

Fields expecting a value with an associated unit are formalized with **two**
attributes: one to contain the actual value, and one to contain the
corresponding unit. The second attribute has the same name as the first one with
an added `_unit` suffix. The range of the second attribute should be one of the
enumerations in the `limi-core-units.yaml` schema.

#### Fields expecting a complex “sub-element”

If a field expects a value that is itself an element, that element is in turn
formalized as an independent LinkML class, and the field is formalized as an
attribute whose range is set to the class.

There is no obligation for the name of the class and the name of the attribute
to be linked (e.g., be derived from the same root) in any way. For example, in
the _LiMiInstrument_ class, the attribute holding a _LiMiMicroscopeStand_ object
does _not_ have to be named `microscope_stand` – it can very well be named
`stand`, if this is deemed non-ambiguous enough in the context of a
_LiMiInstrument_ object. Of note, for serialisation purposes, it is the name of
the _attribute_ that matters, not the name of the class. The name of the class
is never expected to appear in serialised data (except in the case of
type-designated classes, used to render Abstract Parent Elements as described
above) – which is in fact why we can prepend the `LiMi` prefix in front of every
class name – this is without any consequence on how the data is serialised.
