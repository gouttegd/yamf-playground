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

@LinkURI("https://example.invalid/pidinst/PIDInstVersionable")
public class PIDInstVersionable {

    @SlotName("added_in")
    @LinkURI("https://example.invalid/pidinst/added_in")
    private PIDInstVersion addedIn;

    public void setAddedIn(PIDInstVersion addedIn) {
        this.addedIn = addedIn;
    }

    public PIDInstVersion getAddedIn() {
        return this.addedIn;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Object o;
        sb.append("PIDInstVersionable(");
        if ( (o = this.getAddedIn()) != null ) {
            sb.append("added_in=");
            sb.append(o);
            sb.append(",");
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof PIDInstVersionable) ) return false;
        final PIDInstVersionable other = (PIDInstVersionable) o;
        if ( !other.canEqual((Object) this)) return false;
        final Object this$addedIn = this.getAddedIn();
        final Object other$addedIn = other.getAddedIn();
        if ( this$addedIn == null ? other$addedIn != null : !this$addedIn.equals(other$addedIn) ) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PIDInstVersionable;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $addedIn = this.getAddedIn();
        result = result * PRIME + ($addedIn == null ? 43 : $addedIn.hashCode());
        return result;
    }
}