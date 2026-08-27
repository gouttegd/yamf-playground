package org.incenp.yamf.playground.pidinst.model;

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

@LinkURI("https://schemas.incenp.org/ngmf/v1/base/BaseExtensionObject")
public class BaseExtensionObject {

    @Identifier(isGlobal = false)
    @TypeDesignator
    @SlotName("extension_type")
    @Required
    @LinkURI("https://schemas.incenp.org/ngmf/v1/base/extension_type")
    private URI extensionType;

    @ExtensionHolder
    private Map<String, Object> extraSlots;

    public void setExtensionType(URI extensionType) {
        this.extensionType = extensionType;
    }

    public URI getExtensionType() {
        return this.extensionType;
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
        return "BaseExtensionObject(extension_type=" + this.getExtensionType() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof BaseExtensionObject) ) return false;
        final BaseExtensionObject other = (BaseExtensionObject) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$extensionType = this.getExtensionType();
        final Object other$extensionType = other.getExtensionType();
        if ( this$extensionType == null ? other$extensionType != null : !this$extensionType.equals(other$extensionType) ) return false;
        if ( this.extraSlots == null ? other.extraSlots != null : !this.extraSlots.equals(other.extraSlots) ) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseExtensionObject;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $extensionType = this.getExtensionType();
        result = result * PRIME + ($extensionType == null ? 43 : $extensionType.hashCode());
        result = result * PRIME + (this.extraSlots == null ? 43 : this.extraSlots.hashCode());
        return result;
    }
}