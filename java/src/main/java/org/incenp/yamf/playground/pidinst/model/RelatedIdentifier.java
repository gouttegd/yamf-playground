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

@LinkURI("https://example.org/pidinst/RelatedIdentifier")
public class RelatedIdentifier {

    @Required
    @LinkURI("https://example.org/pidinst/identifier")
    private String identifier;

    @LinkURI("https://example.org/pidinst/name")
    private String name;

    @Required
    @LinkURI("https://example.org/pidinst/type")
    private RelatedIdentifierType type;

    @Required
    @LinkURI("https://example.org/pidinst/relation")
    private RelationType relation;

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType(RelatedIdentifierType type) {
        this.type = type;
    }

    public RelatedIdentifierType getType() {
        return this.type;
    }

    public void setRelation(RelationType relation) {
        this.relation = relation;
    }

    public RelationType getRelation() {
        return this.relation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("RelatedIdentifier(");
        if ( (o = this.getIdentifier()) != null ) {
            sb.append("identifier=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getName()) != null ) {
            sb.append("name=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getType()) != null ) {
            sb.append("type=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getRelation()) != null ) {
            sb.append("relation=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof RelatedIdentifier) ) return false;
        final RelatedIdentifier other = (RelatedIdentifier) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$identifier = this.getIdentifier();
        final Object other$identifier = other.getIdentifier();
        if ( this$identifier == null ? other$identifier != null : !this$identifier.equals(other$identifier)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if ( this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if ( this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$relation = this.getRelation();
        final Object other$relation = other.getRelation();
        if ( this$relation == null ? other$relation != null : !this$relation.equals(other$relation)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RelatedIdentifier;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $identifier = this.getIdentifier();
        result = result * PRIME + ($identifier == null ? 43 : $identifier.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $relation = this.getRelation();
        result = result * PRIME + ($relation == null ? 43 : $relation.hashCode());
        return result;
    }
}