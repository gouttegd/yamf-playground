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

@LinkURI("https://example.org/ome/Objective")
public class Objective extends MicroscopeComponent {

    @LinkURI("https://example.org/ome/correction")
    private CorrectionType correction;

    @LinkURI("https://example.org/ome/immersion")
    private ImmersionType immersion;

    @SlotName("lens_na")
    @LinkURI("https://example.org/ome/lens_na")
    private Float lensNa;

    @SlotName("nominal_magnification")
    @LinkURI("https://example.org/ome/nominal_magnification")
    private Float nominalMagnification;

    @SlotName("calibrated_magnification")
    @LinkURI("https://example.org/ome/calibrated_magnification")
    private Float calibratedMagnification;

    @SlotName("working_distance")
    @LinkURI("https://example.org/ome/working_distance")
    private Float workingDistance;

    @SlotName("working_distance_unit")
    @LinkURI("https://example.org/ome/working_distance_unit")
    private LengthUnit workingDistanceUnit;

    @LinkURI("https://example.org/ome/iris")
    private Boolean iris;

    public void setCorrection(CorrectionType correction) {
        this.correction = correction;
    }

    public CorrectionType getCorrection() {
        return this.correction;
    }

    public void setImmersion(ImmersionType immersion) {
        this.immersion = immersion;
    }

    public ImmersionType getImmersion() {
        return this.immersion;
    }

    public void setLensNa(Float lensNa) {
        this.lensNa = lensNa;
    }

    public Float getLensNa() {
        return this.lensNa;
    }

    public void setNominalMagnification(Float nominalMagnification) {
        this.nominalMagnification = nominalMagnification;
    }

    public Float getNominalMagnification() {
        return this.nominalMagnification;
    }

    public void setCalibratedMagnification(Float calibratedMagnification) {
        this.calibratedMagnification = calibratedMagnification;
    }

    public Float getCalibratedMagnification() {
        return this.calibratedMagnification;
    }

    public void setWorkingDistance(Float workingDistance) {
        this.workingDistance = workingDistance;
    }

    public Float getWorkingDistance() {
        return this.workingDistance;
    }

    public void setWorkingDistanceUnit(LengthUnit workingDistanceUnit) {
        this.workingDistanceUnit = workingDistanceUnit;
    }

    public LengthUnit getWorkingDistanceUnit() {
        return this.workingDistanceUnit;
    }

    public void setIris(Boolean iris) {
        this.iris = iris;
    }

    public Boolean getIris() {
        return this.iris;
    }

    @Override
    public String toString() {
        return "Objective(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Objective) ) return false;
        final Objective other = (Objective) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$correction = this.getCorrection();
        final Object other$correction = other.getCorrection();
        if ( this$correction == null ? other$correction != null : !this$correction.equals(other$correction)) return false;
        final Object this$immersion = this.getImmersion();
        final Object other$immersion = other.getImmersion();
        if ( this$immersion == null ? other$immersion != null : !this$immersion.equals(other$immersion)) return false;
        final Object this$lensNa = this.getLensNa();
        final Object other$lensNa = other.getLensNa();
        if ( this$lensNa == null ? other$lensNa != null : !this$lensNa.equals(other$lensNa)) return false;
        final Object this$nominalMagnification = this.getNominalMagnification();
        final Object other$nominalMagnification = other.getNominalMagnification();
        if ( this$nominalMagnification == null ? other$nominalMagnification != null : !this$nominalMagnification.equals(other$nominalMagnification)) return false;
        final Object this$calibratedMagnification = this.getCalibratedMagnification();
        final Object other$calibratedMagnification = other.getCalibratedMagnification();
        if ( this$calibratedMagnification == null ? other$calibratedMagnification != null : !this$calibratedMagnification.equals(other$calibratedMagnification)) return false;
        final Object this$workingDistance = this.getWorkingDistance();
        final Object other$workingDistance = other.getWorkingDistance();
        if ( this$workingDistance == null ? other$workingDistance != null : !this$workingDistance.equals(other$workingDistance)) return false;
        final Object this$workingDistanceUnit = this.getWorkingDistanceUnit();
        final Object other$workingDistanceUnit = other.getWorkingDistanceUnit();
        if ( this$workingDistanceUnit == null ? other$workingDistanceUnit != null : !this$workingDistanceUnit.equals(other$workingDistanceUnit)) return false;
        final Object this$iris = this.getIris();
        final Object other$iris = other.getIris();
        if ( this$iris == null ? other$iris != null : !this$iris.equals(other$iris)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Objective;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $correction = this.getCorrection();
        result = result * PRIME + ($correction == null ? 43 : $correction.hashCode());
        final Object $immersion = this.getImmersion();
        result = result * PRIME + ($immersion == null ? 43 : $immersion.hashCode());
        final Object $lensNa = this.getLensNa();
        result = result * PRIME + ($lensNa == null ? 43 : $lensNa.hashCode());
        final Object $nominalMagnification = this.getNominalMagnification();
        result = result * PRIME + ($nominalMagnification == null ? 43 : $nominalMagnification.hashCode());
        final Object $calibratedMagnification = this.getCalibratedMagnification();
        result = result * PRIME + ($calibratedMagnification == null ? 43 : $calibratedMagnification.hashCode());
        final Object $workingDistance = this.getWorkingDistance();
        result = result * PRIME + ($workingDistance == null ? 43 : $workingDistance.hashCode());
        final Object $workingDistanceUnit = this.getWorkingDistanceUnit();
        result = result * PRIME + ($workingDistanceUnit == null ? 43 : $workingDistanceUnit.hashCode());
        final Object $iris = this.getIris();
        result = result * PRIME + ($iris == null ? 43 : $iris.hashCode());
        return result;
    }
}