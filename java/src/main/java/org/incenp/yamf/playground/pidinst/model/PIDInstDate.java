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

@LinkURI("https://example.org/pidinst/PIDInstDate")
public class PIDInstDate {

    @Required
    @LinkURI("https://example.org/pidinst/date")
    private LocalDate date;

    @Required
    @LinkURI("https://example.org/pidinst/type")
    private PIDInstDateType type;

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setType(PIDInstDateType type) {
        this.type = type;
    }

    public PIDInstDateType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("PIDInstDate(");
        if ( (o = this.getDate()) != null ) {
            sb.append("date=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getType()) != null ) {
            sb.append("type=");
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
        final Object this$date = this.getDate();
        final Object other$date = other.getDate();
        if ( this$date == null ? other$date != null : !this$date.equals(other$date)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if ( this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PIDInstDate;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $date = this.getDate();
        result = result * PRIME + ($date == null ? 43 : $date.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        return result;
    }
}