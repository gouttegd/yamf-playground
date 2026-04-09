from __future__ import annotations

import re
import sys
from datetime import (
    date as adate,
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


metamodel_version = "1.7.0"
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


linkml_meta = LinkMLMeta({'default_prefix': 'https://example.org/pidinst-foo/',
     'default_range': 'string',
     'id': 'https://example.org/pidinst-foo',
     'imports': ['pidinst'],
     'name': 'pidinst-foo-extension',
     'prefixes': {'fooext': {'prefix_prefix': 'fooext',
                             'prefix_reference': 'https://example.org/pidinst-foo/'}},
     'source_file': 'linkml/pidinst/pidinst-foo.yaml'} )

class PidinstVersion(str, Enum):
    number_1FULL_STOP0 = "1.0"


class DateType(str, Enum):
    Commissioned = "Commissioned"
    DeComissioned = "DeComissioned"


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


class AlternateIdentifierType(str, Enum):
    SerialNumber = "SerialNumber"
    InventoryNumber = "InventoryNumber"
    Other = "Other"



class IsIdentifiableEntity(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst',
         'mixin': True,
         'rules': [{'description': 'If the identifier is present, then the '
                                   'identifier_type must be present as well.'},
                   {'postconditions': {'slot_conditions': {'identifier_type': {'name': 'identifier_type',
                                                                               'required': True}}},
                    'preconditions': {'slot_conditions': {'identifier': {'name': 'identifier',
                                                                         'required': True}}}}]})

    name: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier',
                       'Foo']} })
    identifier: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity']} })


class Instrument(IsIdentifiableEntity):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'extra_slots': {'allowed': True},
         'from_schema': 'https://example.org/pidinst',
         'mixins': ['IsIdentifiableEntity'],
         'slot_usage': {'identifier': {'identifier': True, 'name': 'identifier'}}})

    schema_version: PidinstVersion = Field(default=..., description="""Version number of the PIDINST model used in this record.""", json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    landing_page: str = Field(default=..., description="""A landing page that the identifier resolves to.""", json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    owners: Optional[list[Owner]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    manufacturers: Optional[list[Manufacturer]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    model: Optional[Model] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    description: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    types: Optional[list[InstrumentType]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    measured_variables: Optional[list[str]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    dates: Optional[list[Date]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    related_identifiers: Optional[list[RelatedIdentifier]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    alternate_identifiers: Optional[list[AlternateIdentifier]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    name: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier',
                       'Foo']} })
    identifier: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity']} })


class Owner(IsIdentifiableEntity):
    """
    An institution responsible for the management of the instrument. This may include the legal owner, the operator, or an institute providing access to the instrument.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst',
         'mixins': ['IsIdentifiableEntity']})

    contact: Optional[str] = Field(default=None, description="""Contact address of the owner.""", json_schema_extra = { "linkml_meta": {'domain_of': ['Owner']} })
    name: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier',
                       'Foo']} })
    identifier: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity']} })


class Manufacturer(IsIdentifiableEntity):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst',
         'mixins': ['IsIdentifiableEntity']})

    name: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier',
                       'Foo']} })
    identifier: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity']} })


class Model(IsIdentifiableEntity):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst',
         'mixins': ['IsIdentifiableEntity']})

    name: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier',
                       'Foo']} })
    identifier: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity']} })


class InstrumentType(IsIdentifiableEntity):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst',
         'mixins': ['IsIdentifiableEntity']})

    name: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier',
                       'Foo']} })
    identifier: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity']} })


class Date(ConfiguredBaseModel):
    """
    A date relevant to the instrument.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst'})

    date: adate = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Date']} })
    type: DateType = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Date', 'RelatedIdentifier', 'AlternateIdentifier', 'Foo']} })


class RelatedIdentifier(ConfiguredBaseModel):
    """
    An identifier for a resource related to the instrument.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst'})

    identifier: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier']} })
    name: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier',
                       'Foo']} })
    type: RelatedIdentifierType = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Date', 'RelatedIdentifier', 'AlternateIdentifier', 'Foo']} })
    relation: RelationType = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['RelatedIdentifier']} })


class AlternateIdentifier(ConfiguredBaseModel):
    """
    Identifier other than the PIDINST pertaining to the same instrument instance. This should be used if the instrument has a serial number. Other possible uses include an owner’s inventory number or an entry in some instrument database.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst'})

    identifier: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier']} })
    type: AlternateIdentifierType = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['Date', 'RelatedIdentifier', 'AlternateIdentifier', 'Foo']} })
    name: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier',
                       'Foo']} })


class FooMixin(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-foo', 'mixin': True})

    foo: Optional[Foo] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['FooMixin']} })


class Foo(ConfiguredBaseModel):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-foo'})

    name: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier',
                       'Foo']} })
    type: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Date', 'RelatedIdentifier', 'AlternateIdentifier', 'Foo']} })


class FooInstrument(FooMixin, Instrument):
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst-foo', 'mixins': ['FooMixin']})

    foo: Optional[Foo] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['FooMixin']} })
    schema_version: PidinstVersion = Field(default=..., description="""Version number of the PIDINST model used in this record.""", json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    landing_page: str = Field(default=..., description="""A landing page that the identifier resolves to.""", json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    owners: Optional[list[Owner]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    manufacturers: Optional[list[Manufacturer]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    model: Optional[Model] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    description: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    types: Optional[list[InstrumentType]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    measured_variables: Optional[list[str]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    dates: Optional[list[Date]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    related_identifiers: Optional[list[RelatedIdentifier]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    alternate_identifiers: Optional[list[AlternateIdentifier]] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['Instrument']} })
    name: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier',
                       'Foo']} })
    identifier: str = Field(default=..., json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity',
                       'RelatedIdentifier',
                       'AlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, json_schema_extra = { "linkml_meta": {'domain_of': ['IsIdentifiableEntity']} })


# Model rebuild
# see https://pydantic-docs.helpmanual.io/usage/models/#rebuilding-a-model
IsIdentifiableEntity.model_rebuild()
Instrument.model_rebuild()
Owner.model_rebuild()
Manufacturer.model_rebuild()
Model.model_rebuild()
InstrumentType.model_rebuild()
Date.model_rebuild()
RelatedIdentifier.model_rebuild()
AlternateIdentifier.model_rebuild()
FooMixin.model_rebuild()
Foo.model_rebuild()
FooInstrument.model_rebuild()
