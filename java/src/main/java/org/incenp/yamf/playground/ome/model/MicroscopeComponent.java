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

@LinkURI("https://example.org/ome/MicroscopeComponent")
public class MicroscopeComponent {

    @Identifier
    @Required
    @Converter(CurieConverter.class)
    @LinkURI("https://example.org/ome/id")
    private String id;

    @TypeDesignator
    @SlotName("component_type")
    @LinkURI("https://example.org/ome/component_type")
    private String componentType;

    @SlotName("lot_number")
    @LinkURI("https://example.org/ome/lot_number")
    private String lotNumber;

    @LinkURI("https://example.org/ome/manufacturer")
    private String manufacturer;

    @LinkURI("https://example.org/ome/model")
    private String model;

    @SlotName("serial_number")
    @LinkURI("https://example.org/ome/serial_number")
    private String serialNumber;

    @ExtensionHolder
    private Map<String, Object> extraSlots;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    public String getComponentType() {
        return this.componentType;
    }

    public void setLotNumber(String lotNumber) {
        this.lotNumber = lotNumber;
    }

    public String getLotNumber() {
        return this.lotNumber;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return this.model;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setExtraSlots(Map<String,Object> extraSlots) {
        this.extraSlots = extraSlots;
    }

    public Map<String,Object> getExtraSlots() {
        return this.extraSlots;
    }

    public Map<String,Object> getExtraSlots(boolean set) {
        if ( this.extraSlots == null && set ) {
            this.extraSlots = new HashMap<>();
        }
        return this.extraSlots;
    }

    @Override
    public String toString() {
        return "MicroscopeComponent(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof MicroscopeComponent) ) return false;
        final MicroscopeComponent other = (MicroscopeComponent) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if ( this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$componentType = this.getComponentType();
        final Object other$componentType = other.getComponentType();
        if ( this$componentType == null ? other$componentType != null : !this$componentType.equals(other$componentType)) return false;
        final Object this$lotNumber = this.getLotNumber();
        final Object other$lotNumber = other.getLotNumber();
        if ( this$lotNumber == null ? other$lotNumber != null : !this$lotNumber.equals(other$lotNumber)) return false;
        final Object this$manufacturer = this.getManufacturer();
        final Object other$manufacturer = other.getManufacturer();
        if ( this$manufacturer == null ? other$manufacturer != null : !this$manufacturer.equals(other$manufacturer)) return false;
        final Object this$model = this.getModel();
        final Object other$model = other.getModel();
        if ( this$model == null ? other$model != null : !this$model.equals(other$model)) return false;
        final Object this$serialNumber = this.getSerialNumber();
        final Object other$serialNumber = other.getSerialNumber();
        if ( this$serialNumber == null ? other$serialNumber != null : !this$serialNumber.equals(other$serialNumber)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MicroscopeComponent;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $componentType = this.getComponentType();
        result = result * PRIME + ($componentType == null ? 43 : $componentType.hashCode());
        final Object $lotNumber = this.getLotNumber();
        result = result * PRIME + ($lotNumber == null ? 43 : $lotNumber.hashCode());
        final Object $manufacturer = this.getManufacturer();
        result = result * PRIME + ($manufacturer == null ? 43 : $manufacturer.hashCode());
        final Object $model = this.getModel();
        result = result * PRIME + ($model == null ? 43 : $model.hashCode());
        final Object $serialNumber = this.getSerialNumber();
        result = result * PRIME + ($serialNumber == null ? 43 : $serialNumber.hashCode());
        return result;
    }
}