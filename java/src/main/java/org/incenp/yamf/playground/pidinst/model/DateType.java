package org.incenp.yamf.playground.pidinst.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/pidinst/DateType")
public enum DateType {

    COMMISSIONED("Commissioned"),

    DECOMISSIONED("DeComissioned");

    private final static Map<String, DateType> MAP;

    static {
        Map<String, DateType> map = new HashMap<String, DateType>();
        for ( DateType value : DateType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    DateType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static DateType fromString(String v) {
        return MAP.get(v);
    }
}