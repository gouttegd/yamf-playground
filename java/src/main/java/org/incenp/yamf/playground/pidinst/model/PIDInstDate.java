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

@LinkURI("https://example.invalid/pidinst/PIDInstDate")
public class PIDInstDate {

    @Required
    @LinkURI("https://example.invalid/pidinst/value")
    private LocalDate value;

    @Required
    @LinkURI("https://example.invalid/pidinst/type")
    private PIDInstDateType type;

    @Inlined
    @LinkURI("https://schemas.incenp.org/extension/v1/extensions")
    private List<ExtensionNode> extensions;

    public void setValue(LocalDate value) {
        this.value = value;
    }

    public LocalDate getValue() {
        return this.value;
    }

    public void setType(PIDInstDateType type) {
        this.type = type;
    }

    public PIDInstDateType getType() {
        return this.type;
    }

    public void setExtensions(List<ExtensionNode> extensions) {
        this.extensions = extensions;
    }

    public List<ExtensionNode> getExtensions() {
        return this.extensions;
    }

    public List<ExtensionNode> getExtensions(boolean set) {
        if ( this.extensions == null && set ) {
            this.extensions = new ArrayList<>();
        }
        return this.extensions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("PIDInstDate(");
        if ( (o = this.getValue()) != null ) {
            sb.append("value=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getType()) != null ) {
            sb.append("type=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getExtensions()) != null ) {
            sb.append("extensions=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof PIDInstDate) ) return false;
        final PIDInstDate other = (PIDInstDate) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$value = this.getValue();
        final Object other$value = other.getValue();
        if ( this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if ( this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$extensions = this.getExtensions();
        final Object other$extensions = other.getExtensions();
        if ( this$extensions == null ? other$extensions != null : !this$extensions.equals(other$extensions)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PIDInstDate;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $value = this.getValue();
        result = result * PRIME + ($value == null ? 43 : $value.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $extensions = this.getExtensions();
        result = result * PRIME + ($extensions == null ? 43 : $extensions.hashCode());
        return result;
    }
}