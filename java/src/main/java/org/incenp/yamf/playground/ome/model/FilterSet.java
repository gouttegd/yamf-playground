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

@LinkURI("https://example.org/ome/FilterSet")
public class FilterSet extends MicroscopeComponent {

    @SlotName("emission_filters")
    @LinkURI("https://example.org/ome/emission_filters")
    private List<Filter> emissionFilters;

    @LinkURI("https://example.org/ome/dichroic")
    private Dichroic dichroic;

    @SlotName("excitation_filters")
    @LinkURI("https://example.org/ome/excitation_filters")
    private List<Filter> excitationFilters;

    public void setEmissionFilters(List<Filter> emissionFilters) {
        this.emissionFilters = emissionFilters;
    }

    public List<Filter> getEmissionFilters() {
        return this.emissionFilters;
    }

    public List<Filter> getEmissionFilters(boolean set) {
        if ( this.emissionFilters == null && set ) {
            this.emissionFilters = new ArrayList<>();
        }
        return this.emissionFilters;
    }

    public void setDichroic(Dichroic dichroic) {
        this.dichroic = dichroic;
    }

    public Dichroic getDichroic() {
        return this.dichroic;
    }

    public void setExcitationFilters(List<Filter> excitationFilters) {
        this.excitationFilters = excitationFilters;
    }

    public List<Filter> getExcitationFilters() {
        return this.excitationFilters;
    }

    public List<Filter> getExcitationFilters(boolean set) {
        if ( this.excitationFilters == null && set ) {
            this.excitationFilters = new ArrayList<>();
        }
        return this.excitationFilters;
    }

    @Override
    public String toString() {
        return "FilterSet(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof FilterSet) ) return false;
        final FilterSet other = (FilterSet) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$emissionFilters = this.getEmissionFilters();
        final Object other$emissionFilters = other.getEmissionFilters();
        if ( this$emissionFilters == null ? other$emissionFilters != null : !this$emissionFilters.equals(other$emissionFilters)) return false;
        final Object this$dichroic = this.getDichroic();
        final Object other$dichroic = other.getDichroic();
        if ( this$dichroic == null ? other$dichroic != null : !this$dichroic.equals(other$dichroic)) return false;
        final Object this$excitationFilters = this.getExcitationFilters();
        final Object other$excitationFilters = other.getExcitationFilters();
        if ( this$excitationFilters == null ? other$excitationFilters != null : !this$excitationFilters.equals(other$excitationFilters)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FilterSet;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $emissionFilters = this.getEmissionFilters();
        result = result * PRIME + ($emissionFilters == null ? 43 : $emissionFilters.hashCode());
        final Object $dichroic = this.getDichroic();
        result = result * PRIME + ($dichroic == null ? 43 : $dichroic.hashCode());
        final Object $excitationFilters = this.getExcitationFilters();
        result = result * PRIME + ($excitationFilters == null ? 43 : $excitationFilters.hashCode());
        return result;
    }
}