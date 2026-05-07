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

@LinkURI("https://example.org/ome/LightSource")
public class LightSource extends MicroscopeComponent {

    @LinkURI("https://example.org/ome/power")
    private Float power;

    @SlotName("power_unit")
    @LinkURI("https://example.org/ome/power_unit")
    private PowerUnit powerUnit;

    public void setPower(Float power) {
        this.power = power;
    }

    public Float getPower() {
        return this.power;
    }

    public void setPowerUnit(PowerUnit powerUnit) {
        this.powerUnit = powerUnit;
    }

    public PowerUnit getPowerUnit() {
        return this.powerUnit;
    }

    @Override
    public String toString() {
        return "LightSource(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof LightSource) ) return false;
        final LightSource other = (LightSource) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$power = this.getPower();
        final Object other$power = other.getPower();
        if ( this$power == null ? other$power != null : !this$power.equals(other$power)) return false;
        final Object this$powerUnit = this.getPowerUnit();
        final Object other$powerUnit = other.getPowerUnit();
        if ( this$powerUnit == null ? other$powerUnit != null : !this$powerUnit.equals(other$powerUnit)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof LightSource;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $power = this.getPower();
        result = result * PRIME + ($power == null ? 43 : $power.hashCode());
        final Object $powerUnit = this.getPowerUnit();
        result = result * PRIME + ($powerUnit == null ? 43 : $powerUnit.hashCode());
        return result;
    }
}