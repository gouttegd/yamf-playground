package org.incenp.yamf.playground.ome.model;

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

@LinkURI("https://example.org/ome/Filter")
public class Filter extends MicroscopeComponent {

    @LinkURI("https://example.org/ome/type")
    private FilterType type;

    @SlotName("filter_wheel")
    @LinkURI("https://example.org/ome/filter_wheel")
    private String filterWheel;

    @SlotName("transmittance_range")
    @LinkURI("https://example.org/ome/transmittance_range")
    private TransmittanceRange transmittanceRange;

    public void setType(FilterType type) {
        this.type = type;
    }

    public FilterType getType() {
        return this.type;
    }

    public void setFilterWheel(String filterWheel) {
        this.filterWheel = filterWheel;
    }

    public String getFilterWheel() {
        return this.filterWheel;
    }

    public void setTransmittanceRange(TransmittanceRange transmittanceRange) {
        this.transmittanceRange = transmittanceRange;
    }

    public TransmittanceRange getTransmittanceRange() {
        return this.transmittanceRange;
    }

    @Override
    public String toString() {
        return "Filter(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Filter) ) return false;
        final Filter other = (Filter) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if ( this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$filterWheel = this.getFilterWheel();
        final Object other$filterWheel = other.getFilterWheel();
        if ( this$filterWheel == null ? other$filterWheel != null : !this$filterWheel.equals(other$filterWheel)) return false;
        final Object this$transmittanceRange = this.getTransmittanceRange();
        final Object other$transmittanceRange = other.getTransmittanceRange();
        if ( this$transmittanceRange == null ? other$transmittanceRange != null : !this$transmittanceRange.equals(other$transmittanceRange)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Filter;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $filterWheel = this.getFilterWheel();
        result = result * PRIME + ($filterWheel == null ? 43 : $filterWheel.hashCode());
        final Object $transmittanceRange = this.getTransmittanceRange();
        result = result * PRIME + ($transmittanceRange == null ? 43 : $transmittanceRange.hashCode());
        return result;
    }
}