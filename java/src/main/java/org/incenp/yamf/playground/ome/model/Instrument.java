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

@LinkURI("https://example.org/ome/Instrument")
public class Instrument {

    @Identifier
    @Required
    @Converter(CurieConverter.class)
    @LinkURI("https://example.org/ome/id")
    private String id;

    @Inlined
    @LinkURI("https://example.org/ome/microscope")
    private Microscope microscope;

    @Inlined(asList = true)
    @LinkURI("https://example.org/ome/components")
    private List<MicroscopeComponent> components;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setMicroscope(Microscope microscope) {
        this.microscope = microscope;
    }

    public Microscope getMicroscope() {
        return this.microscope;
    }

    public void setComponents(List<MicroscopeComponent> components) {
        this.components = components;
    }

    public List<MicroscopeComponent> getComponents() {
        return this.components;
    }

    public List<MicroscopeComponent> getComponents(boolean set) {
        if ( this.components == null && set ) {
            this.components = new ArrayList<>();
        }
        return this.components;
    }

    @Override
    public String toString() {
        return "Instrument(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Instrument) ) return false;
        final Instrument other = (Instrument) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if ( this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$microscope = this.getMicroscope();
        final Object other$microscope = other.getMicroscope();
        if ( this$microscope == null ? other$microscope != null : !this$microscope.equals(other$microscope)) return false;
        final Object this$components = this.getComponents();
        final Object other$components = other.getComponents();
        if ( this$components == null ? other$components != null : !this$components.equals(other$components)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Instrument;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $microscope = this.getMicroscope();
        result = result * PRIME + ($microscope == null ? 43 : $microscope.hashCode());
        final Object $components = this.getComponents();
        result = result * PRIME + ($components == null ? 43 : $components.hashCode());
        return result;
    }
}