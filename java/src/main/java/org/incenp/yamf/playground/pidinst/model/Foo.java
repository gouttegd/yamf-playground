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

@LinkURI("https://schemas.incenp.org/ngmf/v1/pidinst-foo-extension/Foo")
public class Foo {

    @Required
    @LinkURI("https://schemas.incenp.org/ngmf/v1/pidinst-foo-extension/name")
    private String name;

    @LinkURI("https://schemas.incenp.org/ngmf/v1/pidinst-foo-extension/type")
    private String type;

    @Inlined
    @LinkURI("https://schemas.incenp.org/ngmf/v1/base/extensions")
    private List<BaseExtensionObject> extensions;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setExtensions(List<BaseExtensionObject> extensions) {
        this.extensions = extensions;
    }

    public List<BaseExtensionObject> getExtensions() {
        return this.extensions;
    }

    public List<BaseExtensionObject> getExtensions(boolean set) {
        if ( this.extensions == null && set ) {
            this.extensions = new ArrayList<>();
        }
        return this.extensions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("Foo(");
        if ( (o = this.getName()) != null ) {
            sb.append("name=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getType()) != null ) {
            sb.append("type=");
            sb.append(o);
            sb.append(",");
        }
        if ( (o = this.getExtensions()) != null ) {
            sb.append("extensions=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof Foo) ) return false;
        final Foo other = (Foo) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if ( this$name == null ? other$name != null : !this$name.equals(other$name) ) return false;
        final Object this$type = this.getType();
        final Object other$type = other.getType();
        if ( this$type == null ? other$type != null : !this$type.equals(other$type) ) return false;
        final Object this$extensions = this.getExtensions();
        final Object other$extensions = other.getExtensions();
        if ( this$extensions == null ? other$extensions != null : !this$extensions.equals(other$extensions) ) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Foo;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $type = this.getType();
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $extensions = this.getExtensions();
        result = result * PRIME + ($extensions == null ? 43 : $extensions.hashCode());
        return result;
    }
}