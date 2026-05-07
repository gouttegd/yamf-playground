package org.incenp.yamf.playground.ome.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/ImmersionType")
public enum ImmersionType {

    OIL("Oil"),

    WATER("Water"),

    WATERDIPPING("WaterDipping"),

    AIR("Air"),

    MULTI("Multi"),

    GLYCEROL("Glycerol"),

    OTHER("Other");

    private final static Map<String, ImmersionType> MAP;

    static {
        Map<String, ImmersionType> map = new HashMap<String, ImmersionType>();
        for ( ImmersionType value : ImmersionType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    ImmersionType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static ImmersionType fromString(String v) {
        return MAP.get(v);
    }
}