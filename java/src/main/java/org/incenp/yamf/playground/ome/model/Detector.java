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

@LinkURI("https://example.org/ome/Detector")
public class Detector extends MicroscopeComponent {

    @LinkURI("https://example.org/ome/gain")
    private Float gain;

    @LinkURI("https://example.org/ome/voltage")
    private Float voltage;

    @SlotName("voltage_unit")
    @LinkURI("https://example.org/ome/voltage_unit")
    private ElectricPotentialUnit voltageUnit;

    @LinkURI("https://example.org/ome/offset")
    private Float offset;

    @LinkURI("https://example.org/ome/zoom")
    private Float zoom;

    @SlotName("amplification_gain")
    @LinkURI("https://example.org/ome/amplification_gain")
    private Float amplificationGain;

    @LinkURI("https://example.org/ome/type")
    private DetectorType type;

    public void setGain(Float gain) {
        this.gain = gain;
    }

    public Float getGain() {
        return this.gain;
    }

    public void setVoltage(Float voltage) {
        this.voltage = voltage;
    }

    public Float getVoltage() {
        return this.voltage;
    }

    public void setVoltageUnit(ElectricPotentialUnit voltageUnit) {
        this.voltageUnit = voltageUnit;
    }

    public ElectricPotentialUnit getVoltageUnit() {
        return this.voltageUnit;
    }

    public void setOffset(Float offset) {
        this.offset = offset;
    }

    public Float getOffset() {
        return this.offset;
    }

    public void setZoom(Float zoom) {
        this.zoom = zoom;
    }

    public Float getZoom() {
        return this.zoom;
    }

    public void setAmplificationGain(Float amplificationGain) {
        this.amplificationGain = amplificationGain;
    }

    public Float getAmplificationGain() {
        return this.amplificationGain;
    }

    public void setType(DetectorType type) {
        this.type = type;
    }

    public DetectorType getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "Detector(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Detector) ) return false;
        final Detector other = (Detector) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$gain = this.getGain();
        final Object other$gain = other.getGain();
        if ( this$gain == null ? other$gain != null : !this$gain.equals(other$gain)) return false;
        final Object this$voltage = this.getVoltage();
        final Object other$voltage = other.getVoltage();
        if ( this$voltage == null ? other$voltage != null : !this$voltage.equals(other$voltage)) return false;
        final Object this$voltageUnit = this.getVoltageUnit();
        final Object other$voltageUnit = other.getVoltageUnit();
        if ( this$voltageUnit == null ? other$voltageUnit != null : !this$voltageUnit.equals(other$voltageUnit)) return false;
        final Object this$offset = this.getOffset();
        final Object other$offset = other.getOffset();
        if ( this$offset == null ? other$offset != null : !this$offset.equals(other$offset)) return false;
        final Object this$zoom = this.getZoom();
        final Object other$zoom = other.getZoom();
        if ( this$zoom == null ? other$zoom != null : !this$zoom.equals(other$zoom)) return false;
        final Object this$amplificationGain = this.getAmplificationGain();
        final Object other$amplificationGain = other.getAmplificationGain();
        if ( this$amplificationGain == null ? other$amplificationGain != null : !this$amplificationGain.equals(other$amplificationGain)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if ( this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Detector;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $gain = this.getGain();
        result = result * PRIME + ($gain == null ? 43 : $gain.hashCode());
        final Object $voltage = this.getVoltage();
        result = result * PRIME + ($voltage == null ? 43 : $voltage.hashCode());
        final Object $voltageUnit = this.getVoltageUnit();
        result = result * PRIME + ($voltageUnit == null ? 43 : $voltageUnit.hashCode());
        final Object $offset = this.getOffset();
        result = result * PRIME + ($offset == null ? 43 : $offset.hashCode());
        final Object $zoom = this.getZoom();
        result = result * PRIME + ($zoom == null ? 43 : $zoom.hashCode());
        final Object $amplificationGain = this.getAmplificationGain();
        result = result * PRIME + ($amplificationGain == null ? 43 : $amplificationGain.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        return result;
    }
}