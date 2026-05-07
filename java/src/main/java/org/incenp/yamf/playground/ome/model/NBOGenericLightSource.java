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

@LinkURI("https://exanple.org/ome-nbo/NBOGenericLightSource")
public class NBOGenericLightSource extends GenericExcitationSource {

    @SlotName("purchase_date")
    @LinkURI("https://exanple.org/ome-nbo/purchase_date")
    private String purchaseDate;

    @SlotName("illumination_power_reporting_statistic")
    @LinkURI("https://exanple.org/ome-nbo/illumination_power_reporting_statistic")
    private String illuminationPowerReportingStatistic;

    @SlotName("illumination_power_reporting_position")
    @LinkURI("https://exanple.org/ome-nbo/illumination_power_reporting_position")
    private String illuminationPowerReportingPosition;

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

    @Override
    public String toString() {
        return "NBOGenericLightSource(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof NBOGenericLightSource) ) return false;
        final NBOGenericLightSource other = (NBOGenericLightSource) o;
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
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof NBOGenericLightSource;
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
        return result;
    }
}