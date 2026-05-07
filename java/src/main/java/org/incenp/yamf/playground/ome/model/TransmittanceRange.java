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

@LinkURI("https://example.org/ome/TransmittanceRange")
public class TransmittanceRange {

    @LinkURI("https://example.org/ome/cutin")
    private Float cutin;

    @SlotName("cutin_unit")
    @LinkURI("https://example.org/ome/cutin_unit")
    private LengthUnit cutinUnit;

    @LinkURI("https://example.org/ome/cutout")
    private Float cutout;

    @SlotName("cutout_unit")
    @LinkURI("https://example.org/ome/cutout_unit")
    private LengthUnit cutoutUnit;

    @SlotName("cutin_tolerance")
    @LinkURI("https://example.org/ome/cutin_tolerance")
    private Float cutinTolerance;

    @SlotName("cutin_tolerance_unit")
    @LinkURI("https://example.org/ome/cutin_tolerance_unit")
    private LengthUnit cutinToleranceUnit;

    @SlotName("cutout_tolerance")
    @LinkURI("https://example.org/ome/cutout_tolerance")
    private Float cutoutTolerance;

    @SlotName("cutout_tolerance_unit")
    @LinkURI("https://example.org/ome/cutout_tolerance_unit")
    private LengthUnit cutoutToleranceUnit;

    @LinkURI("https://example.org/ome/transmittance")
    private Float transmittance;

    public void setCutin(Float cutin) {
        this.cutin = cutin;
    }

    public Float getCutin() {
        return this.cutin;
    }

    public void setCutinUnit(LengthUnit cutinUnit) {
        this.cutinUnit = cutinUnit;
    }

    public LengthUnit getCutinUnit() {
        return this.cutinUnit;
    }

    public void setCutout(Float cutout) {
        this.cutout = cutout;
    }

    public Float getCutout() {
        return this.cutout;
    }

    public void setCutoutUnit(LengthUnit cutoutUnit) {
        this.cutoutUnit = cutoutUnit;
    }

    public LengthUnit getCutoutUnit() {
        return this.cutoutUnit;
    }

    public void setCutinTolerance(Float cutinTolerance) {
        this.cutinTolerance = cutinTolerance;
    }

    public Float getCutinTolerance() {
        return this.cutinTolerance;
    }

    public void setCutinToleranceUnit(LengthUnit cutinToleranceUnit) {
        this.cutinToleranceUnit = cutinToleranceUnit;
    }

    public LengthUnit getCutinToleranceUnit() {
        return this.cutinToleranceUnit;
    }

    public void setCutoutTolerance(Float cutoutTolerance) {
        this.cutoutTolerance = cutoutTolerance;
    }

    public Float getCutoutTolerance() {
        return this.cutoutTolerance;
    }

    public void setCutoutToleranceUnit(LengthUnit cutoutToleranceUnit) {
        this.cutoutToleranceUnit = cutoutToleranceUnit;
    }

    public LengthUnit getCutoutToleranceUnit() {
        return this.cutoutToleranceUnit;
    }

    public void setTransmittance(Float transmittance) {
        this.transmittance = transmittance;
    }

    public Float getTransmittance() {
        return this.transmittance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("TransmittanceRange(");
        if ( (o = this.getCutin()) != null ) {
            sb.append("cutin=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCutinUnit()) != null ) {
            sb.append("cutin_unit=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCutout()) != null ) {
            sb.append("cutout=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCutoutUnit()) != null ) {
            sb.append("cutout_unit=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCutinTolerance()) != null ) {
            sb.append("cutin_tolerance=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCutinToleranceUnit()) != null ) {
            sb.append("cutin_tolerance_unit=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCutoutTolerance()) != null ) {
            sb.append("cutout_tolerance=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getCutoutToleranceUnit()) != null ) {
            sb.append("cutout_tolerance_unit=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getTransmittance()) != null ) {
            sb.append("transmittance=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof TransmittanceRange) ) return false;
        final TransmittanceRange other = (TransmittanceRange) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$cutin = this.getCutin();
        final Object other$cutin = other.getCutin();
        if ( this$cutin == null ? other$cutin != null : !this$cutin.equals(other$cutin)) return false;
        final Object this$cutinUnit = this.getCutinUnit();
        final Object other$cutinUnit = other.getCutinUnit();
        if ( this$cutinUnit == null ? other$cutinUnit != null : !this$cutinUnit.equals(other$cutinUnit)) return false;
        final Object this$cutout = this.getCutout();
        final Object other$cutout = other.getCutout();
        if ( this$cutout == null ? other$cutout != null : !this$cutout.equals(other$cutout)) return false;
        final Object this$cutoutUnit = this.getCutoutUnit();
        final Object other$cutoutUnit = other.getCutoutUnit();
        if ( this$cutoutUnit == null ? other$cutoutUnit != null : !this$cutoutUnit.equals(other$cutoutUnit)) return false;
        final Object this$cutinTolerance = this.getCutinTolerance();
        final Object other$cutinTolerance = other.getCutinTolerance();
        if ( this$cutinTolerance == null ? other$cutinTolerance != null : !this$cutinTolerance.equals(other$cutinTolerance)) return false;
        final Object this$cutinToleranceUnit = this.getCutinToleranceUnit();
        final Object other$cutinToleranceUnit = other.getCutinToleranceUnit();
        if ( this$cutinToleranceUnit == null ? other$cutinToleranceUnit != null : !this$cutinToleranceUnit.equals(other$cutinToleranceUnit)) return false;
        final Object this$cutoutTolerance = this.getCutoutTolerance();
        final Object other$cutoutTolerance = other.getCutoutTolerance();
        if ( this$cutoutTolerance == null ? other$cutoutTolerance != null : !this$cutoutTolerance.equals(other$cutoutTolerance)) return false;
        final Object this$cutoutToleranceUnit = this.getCutoutToleranceUnit();
        final Object other$cutoutToleranceUnit = other.getCutoutToleranceUnit();
        if ( this$cutoutToleranceUnit == null ? other$cutoutToleranceUnit != null : !this$cutoutToleranceUnit.equals(other$cutoutToleranceUnit)) return false;
        final Object this$transmittance = this.getTransmittance();
        final Object other$transmittance = other.getTransmittance();
        if ( this$transmittance == null ? other$transmittance != null : !this$transmittance.equals(other$transmittance)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TransmittanceRange;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $cutin = this.getCutin();
        result = result * PRIME + ($cutin == null ? 43 : $cutin.hashCode());
        final Object $cutinUnit = this.getCutinUnit();
        result = result * PRIME + ($cutinUnit == null ? 43 : $cutinUnit.hashCode());
        final Object $cutout = this.getCutout();
        result = result * PRIME + ($cutout == null ? 43 : $cutout.hashCode());
        final Object $cutoutUnit = this.getCutoutUnit();
        result = result * PRIME + ($cutoutUnit == null ? 43 : $cutoutUnit.hashCode());
        final Object $cutinTolerance = this.getCutinTolerance();
        result = result * PRIME + ($cutinTolerance == null ? 43 : $cutinTolerance.hashCode());
        final Object $cutinToleranceUnit = this.getCutinToleranceUnit();
        result = result * PRIME + ($cutinToleranceUnit == null ? 43 : $cutinToleranceUnit.hashCode());
        final Object $cutoutTolerance = this.getCutoutTolerance();
        result = result * PRIME + ($cutoutTolerance == null ? 43 : $cutoutTolerance.hashCode());
        final Object $cutoutToleranceUnit = this.getCutoutToleranceUnit();
        result = result * PRIME + ($cutoutToleranceUnit == null ? 43 : $cutoutToleranceUnit.hashCode());
        final Object $transmittance = this.getTransmittance();
        result = result * PRIME + ($transmittance == null ? 43 : $transmittance.hashCode());
        return result;
    }
}