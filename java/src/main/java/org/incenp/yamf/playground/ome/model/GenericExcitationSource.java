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

@LinkURI("https://example.org/ome/GenericExcitationSource")
public class GenericExcitationSource extends LightSource {

    @Inlined
    @LinkURI("https://example.org/ome/map")
    private List<MapEntry> map;

    public void setMap(List<MapEntry> map) {
        this.map = map;
    }

    public List<MapEntry> getMap() {
        return this.map;
    }

    public List<MapEntry> getMap(boolean set) {
        if ( this.map == null && set ) {
            this.map = new ArrayList<>();
        }
        return this.map;
    }

    @Override
    public String toString() {
        return "GenericExcitationSource(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof GenericExcitationSource) ) return false;
        final GenericExcitationSource other = (GenericExcitationSource) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$map = this.getMap();
        final Object other$map = other.getMap();
        if ( this$map == null ? other$map != null : !this$map.equals(other$map)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof GenericExcitationSource;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $map = this.getMap();
        result = result * PRIME + ($map == null ? 43 : $map.hashCode());
        return result;
    }
}