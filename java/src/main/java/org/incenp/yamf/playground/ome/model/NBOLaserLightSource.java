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

@LinkURI("https://exanple.org/ome-nbo/NBOLaserLightSource")
public class NBOLaserLightSource extends LaserLightSource {

    @SlotName("purchase_date")
    @LinkURI("https://exanple.org/ome-nbo/purchase_date")
    private String purchaseDate;

    @SlotName("illumination_power_reporting_statistic")
    @LinkURI("https://exanple.org/ome-nbo/illumination_power_reporting_statistic")
    private String illuminationPowerReportingStatistic;

    @SlotName("illumination_power_reporting_position")
    @LinkURI("https://exanple.org/ome-nbo/illumination_power_reporting_position")
    private String illuminationPowerReportingPosition;

    @SlotName("modulation_mechanism")
    @LinkURI("https://exanple.org/ome-nbo/modulation_mechanism")
    private String modulationMechanism;

    @SlotName("laser_class")
    @LinkURI("https://exanple.org/ome-nbo/laser_class")
    private String laserClass;

    @SlotName("is_pump")
    @LinkURI("https://exanple.org/ome-nbo/is_pump")
    private String isPump;

    @LinkURI("https://exanple.org/ome-nbo/position")
    private String position;

    @SlotName("pulse_duration")
    @LinkURI("https://exanple.org/ome-nbo/pulse_duration")
    private String pulseDuration;

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseDate() {
        return this.purchaseDate;
    }

    public void setIlluminationPowerReportingStatistic(String illuminationPowerReportingStatistic) {
        this.illuminationPowerReportingStatistic = illuminationPowerReportingStatistic;
    }

    public String getIlluminationPowerReportingStatistic() {
        return this.illuminationPowerReportingStatistic;
    }

    public void setIlluminationPowerReportingPosition(String illuminationPowerReportingPosition) {
        this.illuminationPowerReportingPosition = illuminationPowerReportingPosition;
    }

    public String getIlluminationPowerReportingPosition() {
        return this.illuminationPowerReportingPosition;
    }

    public void setModulationMechanism(String modulationMechanism) {
        this.modulationMechanism = modulationMechanism;
    }

    public String getModulationMechanism() {
        return this.modulationMechanism;
    }

    public void setLaserClass(String laserClass) {
        this.laserClass = laserClass;
    }

    public String getLaserClass() {
        return this.laserClass;
    }

    public void setIsPump(String isPump) {
        this.isPump = isPump;
    }

    public String getIsPump() {
        return this.isPump;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPulseDuration(String pulseDuration) {
        this.pulseDuration = pulseDuration;
    }

    public String getPulseDuration() {
        return this.pulseDuration;
    }

    @Override
    public String toString() {
        return "NBOLaserLightSource(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof NBOLaserLightSource) ) return false;
        final NBOLaserLightSource other = (NBOLaserLightSource) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$purchaseDate = this.getPurchaseDate();
        final Object other$purchaseDate = other.getPurchaseDate();
        if ( this$purchaseDate == null ? other$purchaseDate != null : !this$purchaseDate.equals(other$purchaseDate)) return false;
        final Object this$illuminationPowerReportingStatistic = this.getIlluminationPowerReportingStatistic();
        final Object other$illuminationPowerReportingStatistic = other.getIlluminationPowerReportingStatistic();
        if ( this$illuminationPowerReportingStatistic == null ? other$illuminationPowerReportingStatistic != null : !this$illuminationPowerReportingStatistic.equals(other$illuminationPowerReportingStatistic)) return false;
        final Object this$illuminationPowerReportingPosition = this.getIlluminationPowerReportingPosition();
        final Object other$illuminationPowerReportingPosition = other.getIlluminationPowerReportingPosition();
        if ( this$illuminationPowerReportingPosition == null ? other$illuminationPowerReportingPosition != null : !this$illuminationPowerReportingPosition.equals(other$illuminationPowerReportingPosition)) return false;
        final Object this$modulationMechanism = this.getModulationMechanism();
        final Object other$modulationMechanism = other.getModulationMechanism();
        if ( this$modulationMechanism == null ? other$modulationMechanism != null : !this$modulationMechanism.equals(other$modulationMechanism)) return false;
        final Object this$laserClass = this.getLaserClass();
        final Object other$laserClass = other.getLaserClass();
        if ( this$laserClass == null ? other$laserClass != null : !this$laserClass.equals(other$laserClass)) return false;
        final Object this$isPump = this.getIsPump();
        final Object other$isPump = other.getIsPump();
        if ( this$isPump == null ? other$isPump != null : !this$isPump.equals(other$isPump)) return false;
        final Object this$position = this.getPosition();
        final Object other$position = other.getPosition();
        if ( this$position == null ? other$position != null : !this$position.equals(other$position)) return false;
        final Object this$pulseDuration = this.getPulseDuration();
        final Object other$pulseDuration = other.getPulseDuration();
        if ( this$pulseDuration == null ? other$pulseDuration != null : !this$pulseDuration.equals(other$pulseDuration)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof NBOLaserLightSource;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $purchaseDate = this.getPurchaseDate();
        result = result * PRIME + ($purchaseDate == null ? 43 : $purchaseDate.hashCode());
        final Object $illuminationPowerReportingStatistic = this.getIlluminationPowerReportingStatistic();
        result = result * PRIME + ($illuminationPowerReportingStatistic == null ? 43 : $illuminationPowerReportingStatistic.hashCode());
        final Object $illuminationPowerReportingPosition = this.getIlluminationPowerReportingPosition();
        result = result * PRIME + ($illuminationPowerReportingPosition == null ? 43 : $illuminationPowerReportingPosition.hashCode());
        final Object $modulationMechanism = this.getModulationMechanism();
        result = result * PRIME + ($modulationMechanism == null ? 43 : $modulationMechanism.hashCode());
        final Object $laserClass = this.getLaserClass();
        result = result * PRIME + ($laserClass == null ? 43 : $laserClass.hashCode());
        final Object $isPump = this.getIsPump();
        result = result * PRIME + ($isPump == null ? 43 : $isPump.hashCode());
        final Object $position = this.getPosition();
        result = result * PRIME + ($position == null ? 43 : $position.hashCode());
        final Object $pulseDuration = this.getPulseDuration();
        result = result * PRIME + ($pulseDuration == null ? 43 : $pulseDuration.hashCode());
        return result;
    }
}