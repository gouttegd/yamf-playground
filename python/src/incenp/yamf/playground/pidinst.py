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


linkml_meta = LinkMLMeta({'conforms_to': 'https://doi.org/10.15497/RDA00070',
     'created_by': 'orcid:0000-0002-6095-8718',
     'default_prefix': 'pidinst',
     'default_range': 'string',
     'description': 'A tentative implementation of the PIDINST model as a LinkML '
                    'schema designed to be transparently extensible.',
     'id': 'https://example.org/pidinst',
     'imports': ['linkml:types'],
     'name': 'pidinst',
     'prefixes': {'linkml': {'prefix_prefix': 'linkml',
                             'prefix_reference': 'https://w3id.org/linkml/'},
                  'orcid': {'prefix_prefix': 'orcid',
                            'prefix_reference': 'https://orcid.org/'},
                  'pidinst': {'prefix_prefix': 'pidinst',
                              'prefix_reference': 'https://example.org/pidinst/'}},
     'source_file': 'linkml/pidinst/pidinst.yaml',
     'title': 'PIDInst LinkML Schema'} )

class PIDInstVersion(str, Enum):
    """
    Available versions of the PIDInst model.
    """
    number_1FULL_STOP0 = "1.0"


class PIDInstDateType(str, Enum):
    """
    Values describing how a date is relevant to an instrument.
    """
    Commissioned = "Commissioned"
    DeComissioned = "DeComissioned"


class PIDInstRelatedIdentifierType(str, Enum):
    """
    Possible types of related identifiers for an instrument.
    """
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


class PIDInstRelationType(str, Enum):
    """
    Possible types of relation between a PIDInst instance and its related identifiers.
    """
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


class PIDInstAlternateIdentifierType(str, Enum):
    """
    Possible types of alternate identifiers for a PIDINST instance.
    """
    SerialNumber = "SerialNumber"
    InventoryNumber = "InventoryNumber"
    Other = "Other"



class PIDInstIsIdentifiableEntity(ConfiguredBaseModel):
    """
    A mixin that represents any kind of identifiable entity, and that contains the slots that are shared by all classes in this schema intended to represent such entities.
    The main purpose of having those slots in a shared mixin is to be able to state once and for all the constraint that an `identifier_type` must be present whenever an `identifier` is present.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst',
         'mixin': True,
         'rules': [{'description': 'If the `identifier` is present, then the '
                                   '`identifier_type` must be present as well.'},
                   {'postconditions': {'slot_conditions': {'identifier_type': {'name': 'identifier_type',
                                                                               'required': True}}},
                    'preconditions': {'slot_conditions': {'identifier': {'name': 'identifier',
                                                                         'required': True}}}}]})

    name: str = Field(default=..., description="""The human-readable name of the entity.""", json_schema_extra = { "linkml_meta": {'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    identifier: Optional[str] = Field(default=None, description="""An identifier (not necessarily human-readable) for the entity. Of note, this is not an identifier in the LinkML sense of the term – this is a string that identifies the entity, but without the LinkML implications with respect to uniqueness or inlining.""", json_schema_extra = { "linkml_meta": {'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, description="""A value that indicates the type of identifier contained in the `identifier` slot.""", json_schema_extra = { "linkml_meta": {'domain_of': ['PIDInstIsIdentifiableEntity']} })


class PIDInstInstrument(PIDInstIsIdentifiableEntity):
    """
    A research instrument. This is the main class of the PIDInst model.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'extra_slots': {'allowed': True},
         'from_schema': 'https://example.org/pidinst',
         'mixins': ['PIDInstIsIdentifiableEntity'],
         'slot_usage': {'identifier': {'conforms_to': 'PIDINST model property ID 1',
                                       'description': 'A string that uniquely '
                                                      'identifies the instrument '
                                                      'instance.',
                                       'identifier': True,
                                       'name': 'identifier'},
                        'identifier_type': {'conforms_to': 'PIDINST model property ID '
                                                           '1.1',
                                            'name': 'identifier_type'},
                        'name': {'conforms_to': 'PIDINST model property ID 4',
                                 'description': 'The name by which the instrument '
                                                'instance is known.',
                                 'name': 'name'}}})

    schema_version: PIDInstVersion = Field(default=..., description="""The version of the PIDInst model this record conforms to.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDInst model property ID 2',
         'domain_of': ['PIDInstInstrument']} })
    landing_page: str = Field(default=..., description="""A landing page that the identifier resolves to.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDInst model property ID 3',
         'domain_of': ['PIDInstInstrument']} })
    owners: Optional[list[PIDInstOwner]] = Field(default=None, description="""The institution(s) responsible for the management of the instrument. This may include the legal owner, the operator, or an institute providing access to the instrument.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 5',
         'domain_of': ['PIDInstInstrument']} })
    manufacturers: Optional[list[PIDInstManufacturer]] = Field(default=None, description="""The instrument’s manufacturer(s) or developer. Thiis may also be the owner for custom built instruments.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 6',
         'domain_of': ['PIDInstInstrument']} })
    model: Optional[PIDInstModel] = Field(default=None, description="""The model or type of device, as attributed by the manufacturer.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 7',
         'domain_of': ['PIDInstInstrument']} })
    description: Optional[str] = Field(default=None, description="""A technical description of the device and its capabilities.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 8',
         'domain_of': ['PIDInstInstrument']} })
    types: Optional[list[PIDInstInstrumentType]] = Field(default=None, description="""The type(s) the instrument can be classified into.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 9',
         'domain_of': ['PIDInstInstrument']} })
    measured_variables: Optional[list[str]] = Field(default=None, description="""The variable(s) that this instrument measures or observes.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 10',
         'domain_of': ['PIDInstInstrument']} })
    dates: Optional[list[PIDInstDate]] = Field(default=None, description="""Dates that are relevant to the instrument.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 11',
         'domain_of': ['PIDInstInstrument']} })
    related_identifiers: Optional[list[PIDInstRelatedIdentifier]] = Field(default=None, description="""Identifiers of resources that are related to the instrument.
Not to be confused with `alternate_identifiers`, which is intended for other identifiers for the very same instrument.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 12',
         'domain_of': ['PIDInstInstrument']} })
    alternate_identifiers: Optional[list[PIDInstAlternateIdentifier]] = Field(default=None, description="""Identifiers (other than the PIDInst identifier itself) pertaining to the same instrument instance. This should be used if the instrument has a serial number. Other possible uses include an owner’s inventory number or an entry in some instrument database.
Not to be confused with `related_identifiers`, which is intended for identifiers pointing to other resources that are merely related to this instrument instance while being distinct from it.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 13',
         'domain_of': ['PIDInstInstrument']} })
    name: str = Field(default=..., description="""The name by which the instrument instance is known.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 4',
         'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    identifier: str = Field(default=..., description="""A string that uniquely identifies the instrument instance.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 1',
         'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, description="""A value that indicates the type of identifier contained in the `identifier` slot.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 1.1',
         'domain_of': ['PIDInstIsIdentifiableEntity']} })


class PIDInstOwner(PIDInstIsIdentifiableEntity):
    """
    An institution responsible for the management of an instrument. This may include the legal owner, the operator, or an institute providing access to the instrument.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst',
         'mixins': ['PIDInstIsIdentifiableEntity'],
         'slot_usage': {'identifier': {'conforms_to': 'PIDINST model property 5.3',
                                       'name': 'identifier'},
                        'identifier_type': {'conforms_to': 'PIDINST model property '
                                                           '5.3.1',
                                            'name': 'identifier_type'},
                        'name': {'conforms_to': 'PIDINST model property 5.1',
                                 'name': 'name'}}})

    contact: Optional[str] = Field(default=None, description="""Contact address of the owner.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 5.2', 'domain_of': ['PIDInstOwner']} })
    name: str = Field(default=..., description="""The human-readable name of the entity.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property 5.1',
         'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    identifier: Optional[str] = Field(default=None, description="""An identifier (not necessarily human-readable) for the entity. Of note, this is not an identifier in the LinkML sense of the term – this is a string that identifies the entity, but without the LinkML implications with respect to uniqueness or inlining.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property 5.3',
         'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, description="""A value that indicates the type of identifier contained in the `identifier` slot.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property 5.3.1',
         'domain_of': ['PIDInstIsIdentifiableEntity']} })


class PIDInstManufacturer(PIDInstIsIdentifiableEntity):
    """
    The manufacturer of an instrument.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst',
         'mixins': ['PIDInstIsIdentifiableEntity'],
         'slot_usage': {'identifier': {'conforms_to': 'PIDINST model property ID 6.2',
                                       'name': 'identifier'},
                        'identifier_type': {'conforms_to': 'PIDINST model property ID '
                                                           '6.2.1',
                                            'name': 'identifier_type'},
                        'name': {'conforms_to': 'PIDINST model property ID 6.1',
                                 'name': 'name'}}})

    name: str = Field(default=..., description="""The human-readable name of the entity.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 6.1',
         'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    identifier: Optional[str] = Field(default=None, description="""An identifier (not necessarily human-readable) for the entity. Of note, this is not an identifier in the LinkML sense of the term – this is a string that identifies the entity, but without the LinkML implications with respect to uniqueness or inlining.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 6.2',
         'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, description="""A value that indicates the type of identifier contained in the `identifier` slot.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 6.2.1',
         'domain_of': ['PIDInstIsIdentifiableEntity']} })


class PIDInstModel(PIDInstIsIdentifiableEntity):
    """
    The model of an instrument.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst',
         'mixins': ['PIDInstIsIdentifiableEntity'],
         'slot_usage': {'identifier': {'conforms_to': 'PIDINST model property ID 7.2',
                                       'name': 'identifier'},
                        'identifier_type': {'conforms_to': 'PIDINST model property ID '
                                                           '7.2.1',
                                            'name': 'identifier_type'},
                        'name': {'conforms_to': 'PIDINST model property ID 7.1',
                                 'name': 'name'}}})

    name: str = Field(default=..., description="""The human-readable name of the entity.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 7.1',
         'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    identifier: Optional[str] = Field(default=None, description="""An identifier (not necessarily human-readable) for the entity. Of note, this is not an identifier in the LinkML sense of the term – this is a string that identifies the entity, but without the LinkML implications with respect to uniqueness or inlining.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 7.2',
         'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, description="""A value that indicates the type of identifier contained in the `identifier` slot.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 7.2.1',
         'domain_of': ['PIDInstIsIdentifiableEntity']} })


class PIDInstInstrumentType(PIDInstIsIdentifiableEntity):
    """
    The type of an instrument.
    This differs from the instrument’ model in that (1) it may not be assigned by the manufacturer, and (2) a single instrument can be of more than one type.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst',
         'mixins': ['PIDInstIsIdentifiableEntity'],
         'slot_usage': {'identifier': {'conforms_to': 'PIDINST model property ID 9.2',
                                       'name': 'identifier'},
                        'identifier_type': {'conforms_to': 'PIDINST model property ID '
                                                           '9.2.1',
                                            'name': 'identifier_type'},
                        'name': {'conforms_to': 'PIDINST model property ID 9.1',
                                 'name': 'name'}}})

    name: str = Field(default=..., description="""The human-readable name of the entity.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 9.1',
         'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    identifier: Optional[str] = Field(default=None, description="""An identifier (not necessarily human-readable) for the entity. Of note, this is not an identifier in the LinkML sense of the term – this is a string that identifies the entity, but without the LinkML implications with respect to uniqueness or inlining.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 9.2',
         'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    identifier_type: Optional[str] = Field(default=None, description="""A value that indicates the type of identifier contained in the `identifier` slot.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 9.2.1',
         'domain_of': ['PIDInstIsIdentifiableEntity']} })


class PIDInstDate(ConfiguredBaseModel):
    """
    A date relevant to an instrument.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst'})

    date: adate = Field(default=..., description="""The date itself.""", json_schema_extra = { "linkml_meta": {'domain_of': ['PIDInstDate']} })
    type: PIDInstDateType = Field(default=..., description="""The type of date, indicating how the date is relevant to the instrument.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 11.1',
         'domain_of': ['PIDInstDate',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })


class PIDInstRelatedIdentifier(ConfiguredBaseModel):
    """
    An identifier for a resource related to an instrument.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst'})

    identifier: str = Field(default=..., description="""The identifier itself.""", json_schema_extra = { "linkml_meta": {'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    name: Optional[str] = Field(default=None, description="""A name for the related resource. This may be used to give an hint on the content of that resource.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 12.3',
         'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    type: PIDInstRelatedIdentifierType = Field(default=..., description="""The type of the identifier.
Note that, contrary to most other “identifier type” slots in this schema, this one is _not_ free text and is instead constrained to a fixed list of values.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 12.1',
         'domain_of': ['PIDInstDate',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    relation: PIDInstRelationType = Field(default=..., description="""A description of the relationship between an instrument and the related resource pointed to by the identifier.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 12.2',
         'domain_of': ['PIDInstRelatedIdentifier']} })


class PIDInstAlternateIdentifier(ConfiguredBaseModel):
    """
    A non-PIDInst identifier that can also identify an instrument instance.
    """
    linkml_meta: ClassVar[LinkMLMeta] = LinkMLMeta({'from_schema': 'https://example.org/pidinst'})

    identifier: str = Field(default=..., description="""The identifier itself.""", json_schema_extra = { "linkml_meta": {'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    type: PIDInstAlternateIdentifierType = Field(default=..., description="""The type of the alternate identifier.
Note that, contrary to most other “identifier type” slots in this schema, this one is _not_ free text and is instead constrained to a fixed list of values.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 13.1',
         'domain_of': ['PIDInstDate',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })
    name: Optional[str] = Field(default=None, description="""A supplementary name for the identifier type. This is mostly useful if `type` is set to `Other`.""", json_schema_extra = { "linkml_meta": {'conforms_to': 'PIDINST model property ID 13.2',
         'domain_of': ['PIDInstIsIdentifiableEntity',
                       'PIDInstRelatedIdentifier',
                       'PIDInstAlternateIdentifier']} })


# Model rebuild
# see https://pydantic-docs.helpmanual.io/usage/models/#rebuilding-a-model
PIDInstIsIdentifiableEntity.model_rebuild()
PIDInstInstrument.model_rebuild()
PIDInstOwner.model_rebuild()
PIDInstManufacturer.model_rebuild()
PIDInstModel.model_rebuild()
PIDInstInstrumentType.model_rebuild()
PIDInstDate.model_rebuild()
PIDInstRelatedIdentifier.model_rebuild()
PIDInstAlternateIdentifier.model_rebuild()
