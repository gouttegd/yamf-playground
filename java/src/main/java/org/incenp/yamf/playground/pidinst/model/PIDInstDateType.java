package org.incenp.yamf.playground.pidinst.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/pidinst/PIDInstDateType")
public enum PIDInstDateType {

    COMMISSIONED("Commissioned"),

    DECOMISSIONED("DeComissioned");

    private final static Map<String, PIDInstDateType> MAP;

    static {
        Map<String, PIDInstDateType> map = new HashMap<String, PIDInstDateType>();
        for ( PIDInstDateType value : PIDInstDateType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    PIDInstDateType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static PIDInstDateType fromString(String v) {
        return MAP.get(v);
    }
}