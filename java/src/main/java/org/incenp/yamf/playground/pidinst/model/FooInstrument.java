package org.incenp.yamf.playground.pidinst.model;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/pidinst-foo/FooInstrument")
public class FooInstrument extends Instrument {

    @LinkURI("https://example.org/pidinst-foo/foo")
    private Foo foo;

    public void setFoo(Foo foo) {
        this.foo = foo;
    }

    public Foo getFoo() {
        return this.foo;
    }

    @Override
    public String toString() {
        return "FooInstrument(identifier=" + this.getIdentifier() + ")";
    }

    @Override
    public boolean equals(final Object o) {
        if ( o == this ) return true;
        if ( !(o instanceof FooInstrument) ) return false;
        final FooInstrument other = (FooInstrument) o;
        if ( !other.canEqual((Object) this)) return false;
        if ( !super.equals(o) ) return false;

        final Object this$foo = this.getFoo();
        final Object other$foo = other.getFoo();
        if ( this$foo == null ? other$foo != null : !this$foo.equals(other$foo)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof FooInstrument;
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $foo = this.getFoo();
        result = result * PRIME + ($foo == null ? 43 : $foo.hashCode());
        return result;
    }
}