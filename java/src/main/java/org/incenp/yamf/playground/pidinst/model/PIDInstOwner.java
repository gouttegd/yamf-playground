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

@LinkURI("https://example.org/pidinst/PIDInstOwner")
public class PIDInstOwner {

    @LinkURI("https://example.org/pidinst/contact")
    private String contact;

    @Required
    @LinkURI("https://example.org/pidinst/name")
    private String name;

    @LinkURI("https://example.org/pidinst/identifier")
    private String identifier;

    @SlotName("identifier_type")
    @LinkURI("https://example.org/pidinst/identifier_type")
    private String identifierType;

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact() {
        return this.contact;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("PIDInstOwner(");
        if ( (o = this.getContact()) != null ) {
            sb.append("contact=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getName()) != null ) {
            sb.append("name=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getIdentifier()) != null ) {
            sb.append("identifier=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getIdentifierType()) != null ) {
            sb.append("identifier_type=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof PIDInstOwner) ) return false;
        final PIDInstOwner other = (PIDInstOwner) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$contact = this.getContact();
        final Object other$contact = other.getContact();
        if ( this$contact == null ? other$contact != null : !this$contact.equals(other$contact)) return false;
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
        return other instanceof PIDInstOwner;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $contact = this.getContact();
        result = result * PRIME + ($contact == null ? 43 : $contact.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $identifier = this.getIdentifier();
        result = result * PRIME + ($identifier == null ? 43 : $identifier.hashCode());
        final Object $identifierType = this.getIdentifierType();
        result = result * PRIME + ($identifierType == null ? 43 : $identifierType.hashCode());
        return result;
    }
}