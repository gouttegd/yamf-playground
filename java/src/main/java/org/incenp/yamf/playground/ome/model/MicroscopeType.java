package org.incenp.yamf.playground.ome.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/MicroscopeType")
public enum MicroscopeType {

    UPRIGHT("Upright"),

    INVERTED("Inverted"),

    DISSECTION("Dissection"),

    ELECTROPHYSIOLOGY("Electrophysiology"),

    OTHER("Other");

    private final static Map<String, MicroscopeType> MAP;

    static {
        Map<String, MicroscopeType> map = new HashMap<String, MicroscopeType>();
        for ( MicroscopeType value : MicroscopeType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    MicroscopeType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static MicroscopeType fromString(String v) {
        return MAP.get(v);
    }
}