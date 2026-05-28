package org.incenp.yamf.playground.ome.model;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://exanple.org/ome-nbo/NBOInstrument")
public class NBOInstrument extends Instrument {

    @Override
    public MicroscopeStand getMicroscope() {
        return (MicroscopeStand) super.getMicroscope();
    }

    @Override
    public void setMicroscope(Microscope microscope) {
        if ( !(microscope instanceof MicroscopeStand) ) {
            throw new IllegalArgumentException("invalid type for microscope");
        }
        super.setMicroscope(microscope);
    }

    public void setMicroscope(MicroscopeStand microscope) {
        super.setMicroscope(microscope);
    }

    @Override
    public String toString() {
        return "NBOInstrument(id=" + this.getId() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof NBOInstrument) ) return false;
        final NBOInstrument other = (NBOInstrument) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof NBOInstrument;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        return result;
    }
}