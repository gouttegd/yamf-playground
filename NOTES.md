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
* a “transveral” extension, that adds one (typically small) piece of
  data to many (maybe all) elements of the base model.

The addition of a new light source type to the OME model would be an
example of a focalised extension, as it would only affect the
`Instrument`.

A transveral extension could be, for example, an extension that adds to
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
