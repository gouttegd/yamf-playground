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

@LinkURI("https://exanple.org/ome-nbo/MicroscopeStand")
public class MicroscopeStand extends Microscope {

    @SlotName("eye_piece_field_number")
    @LinkURI("https://exanple.org/ome-nbo/eye_piece_field_number")
    private String eyePieceFieldNumber;

    public void setEyePieceFieldNumber(String eyePieceFieldNumber) {
        this.eyePieceFieldNumber = eyePieceFieldNumber;
    }

    public String getEyePieceFieldNumber() {
        return this.eyePieceFieldNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("MicroscopeStand(");
        if ( (o = this.getEyePieceFieldNumber()) != null ) {
            sb.append("eye_piece_field_number=");
            sb.append(o);
            sb.append(",");
        }
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
        if ( !(o instanceof MicroscopeStand) ) return false;
        final MicroscopeStand other = (MicroscopeStand) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$eyePieceFieldNumber = this.getEyePieceFieldNumber();
        final Object other$eyePieceFieldNumber = other.getEyePieceFieldNumber();
        if ( this$eyePieceFieldNumber == null ? other$eyePieceFieldNumber != null : !this$eyePieceFieldNumber.equals(other$eyePieceFieldNumber)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof MicroscopeStand;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $eyePieceFieldNumber = this.getEyePieceFieldNumber();
        result = result * PRIME + ($eyePieceFieldNumber == null ? 43 : $eyePieceFieldNumber.hashCode());
        return result;
    }
}