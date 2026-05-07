package org.incenp.yamf.playground.ome.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/FilamentLightSourceType")
public enum FilamentLightSourceType {

    INCANDESCENT("Incandescent"),

    HALOGEN("Halogen"),

    OTHER("Other");

    private final static Map<String, FilamentLightSourceType> MAP;

    static {
        Map<String, FilamentLightSourceType> map = new HashMap<String, FilamentLightSourceType>();
        for ( FilamentLightSourceType value : FilamentLightSourceType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    FilamentLightSourceType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static FilamentLightSourceType fromString(String v) {
        return MAP.get(v);
    }
}