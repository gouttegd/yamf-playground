from __future__ import annotations

import re
import sys
from datetime import (
    date,
    datetime,
    time
)
from decimal import Decimal
from enum import Enum
from typing import (
    Any,
    ClassVar,
    Literal,
    Optional,
    Union
)

from pydantic import (
    BaseModel,
    ConfigDict,
    Field,
    RootModel,
    SerializationInfo,
    SerializerFunctionWrapHandler,
    field_validator,
    model_serializer
)


metamodel_version = "None"
version = "None"


class ConfiguredBaseModel(BaseModel):
    model_config = ConfigDict(
        serialize_by_alias = True,
        validate_by_name = True,
        validate_assignment = True,
        validate_default = True,
        extra = "allow",
        arbitrary_types_allowed = True,
        use_enum_values = True,
        strict = False,
    )





class LinkMLMeta(RootModel):
    root: dict[str, Any] = {}
    model_config = ConfigDict(frozen=True)

    def __getattr__(self, key:str):
        return getattr(self.root, key)

    def __getitem__(self, key:str):
        return self.root[key]

    def __setitem__(self, key:str, value):
        self.root[key] = value

    def __contains__(self, key:str) -> bool:
        return key in self.root


linkml_meta = LinkMLMeta({'default_prefix': 'pidinst',
     'default_range': 'string',
     'id': 'https://example.org/pidinst-base',
     'imports': ['linkml:types'],
     'name': 'pidinst',
     'prefixes': {'linkml': {'prefix_prefix': 'linkml',
                             'prefix_reference': 'https://w3id.org/linkml/'},
                  'pidinst': {'prefix_prefix': 'pidinst',
                              'prefix_reference': 'https://example.org/pidinst-base/'}},
     'source_file': '../../../../../linkml/base.yaml'} )

class DateType(str, Enum):
    Commissioned = "Commissioned"
    DeCommissioned = "DeCommissioned"


class RelatedIdentifierType(str, Enum):
    ARK = "ARK"
    arXiv = "arXiv"
    bibcode = "bibcode"
    DOI = "DOI"
    EAN13 = "EAN13"
    EISSN = "EISSN"
    Handle = "Handle"
    IGSN = "IGSN"
    ISBN = "ISBN"
    ISSN = "ISSN"
    ISTC = "ISTC"
    LISSN = "LISSN"
    PMID = "PMID"
    PURL = "PURL"
    RAiD = "RAiD"
    RRID = "RRID"
    UPC = "UPC"
    URL = "URL"
    URN = "URN"
    w3id = "w3id"


class RelationType(str, Enum):
    IsDescribedBy = "IsDescribedBy"
    IsNewVersionOf = "IsNewVersionOf"
    IsPreviousVersionOf = "IsPreviousVersionOf"
    HasComponent = "HasComponent"
    IsComponentOf = "IsComponentOf"
    References = "References"
    HasMetadata = "HasMetadata"
    WasUsedIn = "WasUsedIn"
    IsIdenticalTo = "IsIdenticalTo"
    IsAttachedTo = "IsAttachedTo"



class Instrument(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    identifier: Identifier = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument', 'Identifier']} })
    schemaVersion: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    landingPage: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    name: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    owners: list[Owner] = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    manufacturers: list[Manufacturer] = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    model: Optional[Model] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    description: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    instrumentTypes: Optional[list[InstrumentType]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    measuredVariables: Optional[list[str]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    dates: Optional[list[Date]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    relatedIdentifiers: Optional[list[RelatedIdentifier]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    alternateIdentifiers: Optional[list[AlternateIdentifier]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })


class Identifier(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    identifier: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument', 'Identifier']} })
    identifierType: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Identifier']} })


class Owner(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    ownerName: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Owner']} })
    ownerContact: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Owner']} })
    ownerIdentifier: Optional[OwnerIdentifier] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Owner', 'OwnerIdentifier']} })


class OwnerIdentifier(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    ownerIdentifier: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Owner', 'OwnerIdentifier']} })
    ownerIdentifierType: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['OwnerIdentifier']} })


class Manufacturer(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    manufacturerName: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Manufacturer']} })
    manufacturerIdentifier: Optional[ManufacturerIdentifier] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Manufacturer', 'ManufacturerIdentifier']} })


class ManufacturerIdentifier(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    manufacturerIdentifier: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Manufacturer', 'ManufacturerIdentifier']} })
    manufacturerIdentifierType: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['ManufacturerIdentifier']} })


class Model(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    modelName: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Model']} })
    modelIdentifier: Optional[ModelIdentifier] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Model', 'ModelIdentifier']} })


class ModelIdentifier(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    modelIdentifier: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Model', 'ModelIdentifier']} })
    modelIdentifierType: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['ModelIdentifier']} })


class InstrumentType(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    instrumentTypeName: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['InstrumentType']} })
    instrumentTypeIdentifier: Optional[InstrumentTypeIdentifier] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['InstrumentType', 'InstrumentTypeIdentifier']} })


class InstrumentTypeIdentifier(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    instrumentTypeIdentifier: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['InstrumentType', 'InstrumentTypeIdentifier']} })
    instrumentTypeIdentifierType: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['InstrumentTypeIdentifier']} })


class Date(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    theDate: date = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Date']} })
    dateType: DateType = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Date']} })


class RelatedIdentifier(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    relatedIdentifier: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['RelatedIdentifier']} })
    relatedIdentifierType: RelatedIdentifierType = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['RelatedIdentifier']} })
    relationType: RelationType = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['RelatedIdentifier']} })
    relatedIdentifierName: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['RelatedIdentifier']} })


class AlternateIdentifier(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-base'})

    alternateIdentifier: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['AlternateIdentifier']} })
    alternateIdentifierType: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['AlternateIdentifier']} })
    alternateIdentifierName: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['AlternateIdentifier']} })


# Model rebuild
# see https://pydantic-docs.helpmanual.io/usage/models/#rebuilding-a-model
Instrument.model_rebuild()
Identifier.model_rebuild()
Owner.model_rebuild()
OwnerIdentifier.model_rebuild()
Manufacturer.model_rebuild()
ManufacturerIdentifier.model_rebuild()
Model.model_rebuild()
ModelIdentifier.model_rebuild()
InstrumentType.model_rebuild()
InstrumentTypeIdentifier.model_rebuild()
Date.model_rebuild()
RelatedIdentifier.model_rebuild()
AlternateIdentifier.model_rebuild()
