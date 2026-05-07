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

@LinkURI("https://example.org/ome/LaserLightSource")
public class LaserLightSource extends LightSource {

    @LinkURI("https://example.org/ome/type")
    private LaserLightSourceType type;

    @LinkURI("https://example.org/ome/medium")
    private LaserMedium medium;

    @LinkURI("https://example.org/ome/wavelength")
    private Float wavelength;

    @SlotName("wavelength_unit")
    @LinkURI("https://example.org/ome/wavelength_unit")
    private LengthUnit wavelengthUnit;

    @SlotName("frequency_multiplication")
    @LinkURI("https://example.org/ome/frequency_multiplication")
    private Integer frequencyMultiplication;

    @LinkURI("https://example.org/ome/tuneable")
    private Boolean tuneable;

    @LinkURI("https://example.org/ome/pulse")
    private LaserPulseType pulse;

    @SlotName("pockel_cell")
    @LinkURI("https://example.org/ome/pockel_cell")
    private Boolean pockelCell;

    @SlotName("repetition_rate")
    @LinkURI("https://example.org/ome/repetition_rate")
    private Float repetitionRate;

    @SlotName("repetition_unit")
    @LinkURI("https://example.org/ome/repetition_unit")
    private FrequencyUnit repetitionUnit;

    @LinkURI("https://example.org/ome/pump")
    private LightSource pump;

    public void setType(LaserLightSourceType type) {
        this.type = type;
    }

    public LaserLightSourceType getType() {
        return this.type;
    }

    public void setMedium(LaserMedium medium) {
        this.medium = medium;
    }

    public LaserMedium getMedium() {
        return this.medium;
    }

    public void setWavelength(Float wavelength) {
        this.wavelength = wavelength;
    }

    public Float getWavelength() {
        return this.wavelength;
    }

    public void setWavelengthUnit(LengthUnit wavelengthUnit) {
        this.wavelengthUnit = wavelengthUnit;
    }

    public LengthUnit getWavelengthUnit() {
        return this.wavelengthUnit;
    }

    public void setFrequencyMultiplication(Integer frequencyMultiplication) {
        this.frequencyMultiplication = frequencyMultiplication;
    }

    public Integer getFrequencyMultiplication() {
        return this.frequencyMultiplication;
    }

    public void setTuneable(Boolean tuneable) {
        this.tuneable = tuneable;
    }

    public Boolean getTuneable() {
        return this.tuneable;
    }

    public void setPulse(LaserPulseType pulse) {
        this.pulse = pulse;
    }

    public LaserPulseType getPulse() {
        return this.pulse;
    }

    public void setPockelCell(Boolean pockelCell) {
        this.pockelCell = pockelCell;
    }

    public Boolean getPockelCell() {
        return this.pockelCell;
    }

    public void setRepetitionRate(Float repetitionRate) {
        this.repetitionRate = repetitionRate;
    }

    public Float getRepetitionRate() {
        return this.repetitionRate;
    }

    public void setRepetitionUnit(FrequencyUnit repetitionUnit) {
        this.repetitionUnit = repetitionUnit;
    }

    public FrequencyUnit getRepetitionUnit() {
        return this.repetitionUnit;
    }

    public void setPump(LightSource pump) {
        this.pump = pump;
    }

    public LightSource getPump() {
        return this.pump;
    }

    @Override
    public String toString() {
        return "LaserLightSource(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof LaserLightSource) ) return false;
        final LaserLightSource other = (LaserLightSource) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if ( this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$medium = this.getMedium();
        final Object other$medium = other.getMedium();
        if ( this$medium == null ? other$medium != null : !this$medium.equals(other$medium)) return false;
        final Object this$wavelength = this.getWavelength();
        final Object other$wavelength = other.getWavelength();
        if ( this$wavelength == null ? other$wavelength != null : !this$wavelength.equals(other$wavelength)) return false;
        final Object this$wavelengthUnit = this.getWavelengthUnit();
        final Object other$wavelengthUnit = other.getWavelengthUnit();
        if ( this$wavelengthUnit == null ? other$wavelengthUnit != null : !this$wavelengthUnit.equals(other$wavelengthUnit)) return false;
        final Object this$frequencyMultiplication = this.getFrequencyMultiplication();
        final Object other$frequencyMultiplication = other.getFrequencyMultiplication();
        if ( this$frequencyMultiplication == null ? other$frequencyMultiplication != null : !this$frequencyMultiplication.equals(other$frequencyMultiplication)) return false;
        final Object this$tuneable = this.getTuneable();
        final Object other$tuneable = other.getTuneable();
        if ( this$tuneable == null ? other$tuneable != null : !this$tuneable.equals(other$tuneable)) return false;
        final Object this$pulse = this.getPulse();
        final Object other$pulse = other.getPulse();
        if ( this$pulse == null ? other$pulse != null : !this$pulse.equals(other$pulse)) return false;
        final Object this$pockelCell = this.getPockelCell();
        final Object other$pockelCell = other.getPockelCell();
        if ( this$pockelCell == null ? other$pockelCell != null : !this$pockelCell.equals(other$pockelCell)) return false;
        final Object this$repetitionRate = this.getRepetitionRate();
        final Object other$repetitionRate = other.getRepetitionRate();
        if ( this$repetitionRate == null ? other$repetitionRate != null : !this$repetitionRate.equals(other$repetitionRate)) return false;
        final Object this$repetitionUnit = this.getRepetitionUnit();
        final Object other$repetitionUnit = other.getRepetitionUnit();
        if ( this$repetitionUnit == null ? other$repetitionUnit != null : !this$repetitionUnit.equals(other$repetitionUnit)) return false;
        final Object this$pump = this.getPump();
        final Object other$pump = other.getPump();
        if ( this$pump == null ? other$pump != null : !this$pump.equals(other$pump)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LaserLightSource;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $medium = this.getMedium();
        result = result * PRIME + ($medium == null ? 43 : $medium.hashCode());
        final Object $wavelength = this.getWavelength();
        result = result * PRIME + ($wavelength == null ? 43 : $wavelength.hashCode());
        final Object $wavelengthUnit = this.getWavelengthUnit();
        result = result * PRIME + ($wavelengthUnit == null ? 43 : $wavelengthUnit.hashCode());
        final Object $frequencyMultiplication = this.getFrequencyMultiplication();
        result = result * PRIME + ($frequencyMultiplication == null ? 43 : $frequencyMultiplication.hashCode());
        final Object $tuneable = this.getTuneable();
        result = result * PRIME + ($tuneable == null ? 43 : $tuneable.hashCode());
        final Object $pulse = this.getPulse();
        result = result * PRIME + ($pulse == null ? 43 : $pulse.hashCode());
        final Object $pockelCell = this.getPockelCell();
        result = result * PRIME + ($pockelCell == null ? 43 : $pockelCell.hashCode());
        final Object $repetitionRate = this.getRepetitionRate();
        result = result * PRIME + ($repetitionRate == null ? 43 : $repetitionRate.hashCode());
        final Object $repetitionUnit = this.getRepetitionUnit();
        result = result * PRIME + ($repetitionUnit == null ? 43 : $repetitionUnit.hashCode());
        final Object $pump = this.getPump();
        result = result * PRIME + ($pump == null ? 43 : $pump.hashCode());
        return result;
    }
}