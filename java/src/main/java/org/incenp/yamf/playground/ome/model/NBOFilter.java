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

@LinkURI("https://exanple.org/ome-nbo/NBOFilter")
public class NBOFilter extends Filter {

    @SlotName("coating_method")
    @LinkURI("https://exanple.org/ome-nbo/coating_method")
    private String coatingMethod;

    @SlotName("light_path_location")
    @LinkURI("https://exanple.org/ome-nbo/light_path_location")
    private String lightPathLocation;

    @LinkURI("https://exanple.org/ome-nbo/technology")
    private String technology;

    @SlotName("attenuation_method")
    @LinkURI("https://exanple.org/ome-nbo/attenuation_method")
    private String attenuationMethod;

    @SlotName("attenuation_coefficient")
    @LinkURI("https://exanple.org/ome-nbo/attenuation_coefficient")
    private String attenuationCoefficient;

    @LinkURI("https://exanple.org/ome-nbo/diameter")
    private String diameter;

    @LinkURI("https://exanple.org/ome-nbo/thickness")
    private String thickness;

    @SlotName("angle_of_incidence")
    @LinkURI("https://exanple.org/ome-nbo/angle_of_incidence")
    private String angleOfIncidence;

    @LinkURI("https://exanple.org/ome-nbo/polarization")
    private String polarization;

    @SlotName("transmittance_profile_file")
    @LinkURI("https://exanple.org/ome-nbo/transmittance_profile_file")
    private String transmittanceProfileFile;

    public void setCoatingMethod(String coatingMethod) {
        this.coatingMethod = coatingMethod;
    }

    public String getCoatingMethod() {
        return this.coatingMethod;
    }

    public void setLightPathLocation(String lightPathLocation) {
        this.lightPathLocation = lightPathLocation;
    }

    public String getLightPathLocation() {
        return this.lightPathLocation;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTechnology() {
        return this.technology;
    }

    public void setAttenuationMethod(String attenuationMethod) {
        this.attenuationMethod = attenuationMethod;
    }

    public String getAttenuationMethod() {
        return this.attenuationMethod;
    }

    public void setAttenuationCoefficient(String attenuationCoefficient) {
        this.attenuationCoefficient = attenuationCoefficient;
    }

    public String getAttenuationCoefficient() {
        return this.attenuationCoefficient;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getDiameter() {
        return this.diameter;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getThickness() {
        return this.thickness;
    }

    public void setAngleOfIncidence(String angleOfIncidence) {
        this.angleOfIncidence = angleOfIncidence;
    }

    public String getAngleOfIncidence() {
        return this.angleOfIncidence;
    }

    public void setPolarization(String polarization) {
        this.polarization = polarization;
    }

    public String getPolarization() {
        return this.polarization;
    }

    public void setTransmittanceProfileFile(String transmittanceProfileFile) {
        this.transmittanceProfileFile = transmittanceProfileFile;
    }

    public String getTransmittanceProfileFile() {
        return this.transmittanceProfileFile;
    }

    @Override
    public String toString() {
        return "NBOFilter(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof NBOFilter) ) return false;
        final NBOFilter other = (NBOFilter) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$coatingMethod = this.getCoatingMethod();
        final Object other$coatingMethod = other.getCoatingMethod();
        if ( this$coatingMethod == null ? other$coatingMethod != null : !this$coatingMethod.equals(other$coatingMethod)) return false;
        final Object this$lightPathLocation = this.getLightPathLocation();
        final Object other$lightPathLocation = other.getLightPathLocation();
        if ( this$lightPathLocation == null ? other$lightPathLocation != null : !this$lightPathLocation.equals(other$lightPathLocation)) return false;
        final Object this$technology = this.getTechnology();
        final Object other$technology = other.getTechnology();
        if ( this$technology == null ? other$technology != null : !this$technology.equals(other$technology)) return false;
        final Object this$attenuationMethod = this.getAttenuationMethod();
        final Object other$attenuationMethod = other.getAttenuationMethod();
        if ( this$attenuationMethod == null ? other$attenuationMethod != null : !this$attenuationMethod.equals(other$attenuationMethod)) return false;
        final Object this$attenuationCoefficient = this.getAttenuationCoefficient();
        final Object other$attenuationCoefficient = other.getAttenuationCoefficient();
        if ( this$attenuationCoefficient == null ? other$attenuationCoefficient != null : !this$attenuationCoefficient.equals(other$attenuationCoefficient)) return false;
        final Object this$diameter = this.getDiameter();
        final Object other$diameter = other.getDiameter();
        if ( this$diameter == null ? other$diameter != null : !this$diameter.equals(other$diameter)) return false;
        final Object this$thickness = this.getThickness();
        final Object other$thickness = other.getThickness();
        if ( this$thickness == null ? other$thickness != null : !this$thickness.equals(other$thickness)) return false;
        final Object this$angleOfIncidence = this.getAngleOfIncidence();
        final Object other$angleOfIncidence = other.getAngleOfIncidence();
        if ( this$angleOfIncidence == null ? other$angleOfIncidence != null : !this$angleOfIncidence.equals(other$angleOfIncidence)) return false;
        final Object this$polarization = this.getPolarization();
        final Object other$polarization = other.getPolarization();
        if ( this$polarization == null ? other$polarization != null : !this$polarization.equals(other$polarization)) return false;
        final Object this$transmittanceProfileFile = this.getTransmittanceProfileFile();
        final Object other$transmittanceProfileFile = other.getTransmittanceProfileFile();
        if ( this$transmittanceProfileFile == null ? other$transmittanceProfileFile != null : !this$transmittanceProfileFile.equals(other$transmittanceProfileFile)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof NBOFilter;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $coatingMethod = this.getCoatingMethod();
        result = result * PRIME + ($coatingMethod == null ? 43 : $coatingMethod.hashCode());
        final Object $lightPathLocation = this.getLightPathLocation();
        result = result * PRIME + ($lightPathLocation == null ? 43 : $lightPathLocation.hashCode());
        final Object $technology = this.getTechnology();
        result = result * PRIME + ($technology == null ? 43 : $technology.hashCode());
        final Object $attenuationMethod = this.getAttenuationMethod();
        result = result * PRIME + ($attenuationMethod == null ? 43 : $attenuationMethod.hashCode());
        final Object $attenuationCoefficient = this.getAttenuationCoefficient();
        result = result * PRIME + ($attenuationCoefficient == null ? 43 : $attenuationCoefficient.hashCode());
        final Object $diameter = this.getDiameter();
        result = result * PRIME + ($diameter == null ? 43 : $diameter.hashCode());
        final Object $thickness = this.getThickness();
        result = result * PRIME + ($thickness == null ? 43 : $thickness.hashCode());
        final Object $angleOfIncidence = this.getAngleOfIncidence();
        result = result * PRIME + ($angleOfIncidence == null ? 43 : $angleOfIncidence.hashCode());
        final Object $polarization = this.getPolarization();
        result = result * PRIME + ($polarization == null ? 43 : $polarization.hashCode());
        final Object $transmittanceProfileFile = this.getTransmittanceProfileFile();
        result = result * PRIME + ($transmittanceProfileFile == null ? 43 : $transmittanceProfileFile.hashCode());
        return result;
    }
}