package org.incenp.yamf.playground.pidinst.model;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.incenp.linkml.core.annotations.Converter;
import org.incenp.linkml.core.annotations.ExtensionHolder;
import org.incenp.linkml.core.annotations.Identifier;
import org.incenp.linkml.core.annotations.Inlined;
import org.incenp.linkml.core.annotations.LinkURI;
import org.incenp.linkml.core.annotations.Required;
import org.incenp.linkml.core.annotations.SlotName;
import org.incenp.linkml.core.annotations.TypeDesignator;
import org.incenp.linkml.core.CurieConverter;

@LinkURI("https://example.org/pidinst/PIDInstInstrument")
public class PIDInstInstrument {

    @SlotName("schema_version")
    @Required
    @LinkURI("https://example.org/pidinst/schema_version")
    private PIDInstVersion schemaVersion;

    @SlotName("landing_page")
    @Required
    @LinkURI("https://example.org/pidinst/landing_page")
    private URI landingPage;

    @LinkURI("https://example.org/pidinst/owners")
    private List<PIDInstOwner> owners;

    @LinkURI("https://example.org/pidinst/manufacturers")
    private List<PIDInstManufacturer> manufacturers;

    @LinkURI("https://example.org/pidinst/model")
    private PIDInstModel model;

    @LinkURI("https://example.org/pidinst/description")
    private String description;

    @LinkURI("https://example.org/pidinst/types")
    private List<PIDInstInstrumentType> types;

    @SlotName("measured_variables")
    @LinkURI("https://example.org/pidinst/measured_variables")
    private List<String> measuredVariables;

    @LinkURI("https://example.org/pidinst/dates")
    private List<PIDInstDate> dates;

    @SlotName("related_identifiers")
    @LinkURI("https://example.org/pidinst/related_identifiers")
    private List<PIDInstRelatedIdentifier> relatedIdentifiers;

    @SlotName("alternate_identifiers")
    @LinkURI("https://example.org/pidinst/alternate_identifiers")
    private List<PIDInstAlternateIdentifier> alternateIdentifiers;

    @Required
    @LinkURI("https://example.org/pidinst/name")
    private String name;

    @Identifier
    @Required
    @LinkURI("https://example.org/pidinst/identifier")
    private String identifier;

    @SlotName("identifier_type")
    @LinkURI("https://example.org/pidinst/identifier_type")
    private String identifierType;

    @ExtensionHolder
    private Map<String, Object> extraSlots;

    public void setSchemaVersion(PIDInstVersion schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public PIDInstVersion getSchemaVersion() {
        return this.schemaVersion;
    }

    public void setLandingPage(URI landingPage) {
        this.landingPage = landingPage;
    }

    public URI getLandingPage() {
        return this.landingPage;
    }

    public void setOwners(List<PIDInstOwner> owners) {
        this.owners = owners;
    }

    public List<PIDInstOwner> getOwners() {
        return this.owners;
    }

    public List<PIDInstOwner> getOwners(boolean set) {
        if ( this.owners == null && set ) {
            this.owners = new ArrayList<>();
        }
        return this.owners;
    }

    public void setManufacturers(List<PIDInstManufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<PIDInstManufacturer> getManufacturers() {
        return this.manufacturers;
    }

    public List<PIDInstManufacturer> getManufacturers(boolean set) {
        if ( this.manufacturers == null && set ) {
            this.manufacturers = new ArrayList<>();
        }
        return this.manufacturers;
    }

    public void setModel(PIDInstModel model) {
        this.model = model;
    }

    public PIDInstModel getModel() {
        return this.model;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTypes(List<PIDInstInstrumentType> types) {
        this.types = types;
    }

    public List<PIDInstInstrumentType> getTypes() {
        return this.types;
    }

    public List<PIDInstInstrumentType> getTypes(boolean set) {
        if ( this.types == null && set ) {
            this.types = new ArrayList<>();
        }
        return this.types;
    }

    public void setMeasuredVariables(List<String> measuredVariables) {
        this.measuredVariables = measuredVariables;
    }

    public List<String> getMeasuredVariables() {
        return this.measuredVariables;
    }

    public List<String> getMeasuredVariables(boolean set) {
        if ( this.measuredVariables == null && set ) {
            this.measuredVariables = new ArrayList<>();
        }
        return this.measuredVariables;
    }

    public void setDates(List<PIDInstDate> dates) {
        this.dates = dates;
    }

    public List<PIDInstDate> getDates() {
        return this.dates;
    }

    public List<PIDInstDate> getDates(boolean set) {
        if ( this.dates == null && set ) {
            this.dates = new ArrayList<>();
        }
        return this.dates;
    }

    public void setRelatedIdentifiers(List<PIDInstRelatedIdentifier> relatedIdentifiers) {
        this.relatedIdentifiers = relatedIdentifiers;
    }

    public List<PIDInstRelatedIdentifier> getRelatedIdentifiers() {
        return this.relatedIdentifiers;
    }

    public List<PIDInstRelatedIdentifier> getRelatedIdentifiers(boolean set) {
        if ( this.relatedIdentifiers == null && set ) {
            this.relatedIdentifiers = new ArrayList<>();
        }
        return this.relatedIdentifiers;
    }

    public void setAlternateIdentifiers(List<PIDInstAlternateIdentifier> alternateIdentifiers) {
        this.alternateIdentifiers = alternateIdentifiers;
    }

    public List<PIDInstAlternateIdentifier> getAlternateIdentifiers() {
        return this.alternateIdentifiers;
    }

    public List<PIDInstAlternateIdentifier> getAlternateIdentifiers(boolean set) {
        if ( this.alternateIdentifiers == null && set ) {
            this.alternateIdentifiers = new ArrayList<>();
        }
        return this.alternateIdentifiers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public String getIdentifierType() {
        return this.identifierType;
    }

    public void setExtraSlots(Map<String,Object> extraSlots) {
        this.extraSlots = extraSlots;
    }

    public Map<String,Object> getExtraSlots() {
        return this.extraSlots;
    }

    public Map<String,Object> getExtraSlots(boolean set) {
        if ( this.extraSlots == null && set ) {
            this.extraSlots = new HashMap<>();
        }
        return this.extraSlots;
    }

    @Override
    public String toString() {
        return "PIDInstInstrument(identifier=" + this.getIdentifier() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof PIDInstInstrument) ) return false;
        final PIDInstInstrument other = (PIDInstInstrument) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$schemaVersion = this.getSchemaVersion();
        final Object other$schemaVersion = other.getSchemaVersion();
        if ( this$schemaVersion == null ? other$schemaVersion != null : !this$schemaVersion.equals(other$schemaVersion)) return false;
        final Object this$landingPage = this.getLandingPage();
        final Object other$landingPage = other.getLandingPage();
        if ( this$landingPage == null ? other$landingPage != null : !this$landingPage.equals(other$landingPage)) return false;
        final Object this$owners = this.getOwners();
        final Object other$owners = other.getOwners();
        if ( this$owners == null ? other$owners != null : !this$owners.equals(other$owners)) return false;
        final Object this$manufacturers = this.getManufacturers();
        final Object other$manufacturers = other.getManufacturers();
        if ( this$manufacturers == null ? other$manufacturers != null : !this$manufacturers.equals(other$manufacturers)) return false;
        final Object this$model = this.getModel();
        final Object other$model = other.getModel();
        if ( this$model == null ? other$model != null : !this$model.equals(other$model)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if ( this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        final Object this$types = this.getTypes();
        final Object other$types = other.getTypes();
        if ( this$types == null ? other$types != null : !this$types.equals(other$types)) return false;
        final Object this$measuredVariables = this.getMeasuredVariables();
        final Object other$measuredVariables = other.getMeasuredVariables();
        if ( this$measuredVariables == null ? other$measuredVariables != null : !this$measuredVariables.equals(other$measuredVariables)) return false;
        final Object this$dates = this.getDates();
        final Object other$dates = other.getDates();
        if ( this$dates == null ? other$dates != null : !this$dates.equals(other$dates)) return false;
        final Object this$relatedIdentifiers = this.getRelatedIdentifiers();
        final Object other$relatedIdentifiers = other.getRelatedIdentifiers();
        if ( this$relatedIdentifiers == null ? other$relatedIdentifiers != null : !this$relatedIdentifiers.equals(other$relatedIdentifiers)) return false;
        final Object this$alternateIdentifiers = this.getAlternateIdentifiers();
        final Object other$alternateIdentifiers = other.getAlternateIdentifiers();
        if ( this$alternateIdentifiers == null ? other$alternateIdentifiers != null : !this$alternateIdentifiers.equals(other$alternateIdentifiers)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if ( this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$identifier = this.getIdentifier();
        final Object other$identifier = other.getIdentifier();
        if ( this$identifier == null ? other$identifier != null : !this$identifier.equals(other$identifier)) return false;
        final Object this$identifierType = this.getIdentifierType();
        final Object other$identifierType = other.getIdentifierType();
        if ( this$identifierType == null ? other$identifierType != null : !this$identifierType.equals(other$identifierType)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PIDInstInstrument;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $schemaVersion = this.getSchemaVersion();
        result = result * PRIME + ($schemaVersion == null ? 43 : $schemaVersion.hashCode());
        final Object $landingPage = this.getLandingPage();
        result = result * PRIME + ($landingPage == null ? 43 : $landingPage.hashCode());
        final Object $owners = this.getOwners();
        result = result * PRIME + ($owners == null ? 43 : $owners.hashCode());
        final Object $manufacturers = this.getManufacturers();
        result = result * PRIME + ($manufacturers == null ? 43 : $manufacturers.hashCode());
        final Object $model = this.getModel();
        result = result * PRIME + ($model == null ? 43 : $model.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $types = this.getTypes();
        result = result * PRIME + ($types == null ? 43 : $types.hashCode());
        final Object $measuredVariables = this.getMeasuredVariables();
        result = result * PRIME + ($measuredVariables == null ? 43 : $measuredVariables.hashCode());
        final Object $dates = this.getDates();
        result = result * PRIME + ($dates == null ? 43 : $dates.hashCode());
        final Object $relatedIdentifiers = this.getRelatedIdentifiers();
        result = result * PRIME + ($relatedIdentifiers == null ? 43 : $relatedIdentifiers.hashCode());
        final Object $alternateIdentifiers = this.getAlternateIdentifiers();
        result = result * PRIME + ($alternateIdentifiers == null ? 43 : $alternateIdentifiers.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $identifier = this.getIdentifier();
        result = result * PRIME + ($identifier == null ? 43 : $identifier.hashCode());
        final Object $identifierType = this.getIdentifierType();
        result = result * PRIME + ($identifierType == null ? 43 : $identifierType.hashCode());
        return result;
    }
}