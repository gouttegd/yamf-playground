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

@LinkURI("https://example.org/ome/Microscope")
public class Microscope {

    @LinkURI("https://example.org/ome/type")
    private MicroscopeType type;

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

    public void setType(MicroscopeType type) {
        this.type = type;
    }

    public MicroscopeType getType() {
        return this.type;
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
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("Microscope(");
        if ( (o = this.getType()) != null ) {
            sb.append("type=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getLotNumber()) != null ) {
            sb.append("lot_number=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getManufacturer()) != null ) {
            sb.append("manufacturer=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getModel()) != null ) {
            sb.append("model=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getSerialNumber()) != null ) {
            sb.append("serial_number=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Microscope) ) return false;
        final Microscope other = (Microscope) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if ( this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
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
        return other instanceof Microscope;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
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