package org.incenp.yamf.playground.ome.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/LaserLightSourceType")
public enum LaserLightSourceType {

    EXCIMER("Excimer"),

    GAS("Gas"),

    METALVAPOR("MetalVapor"),

    SOLIDSTATE("SolidState"),

    DYE("Dye"),

    SEMICONDUCTOR("Semiconductor"),

    FREEELECTRON("FreeElectron"),

    OTHER("Other");

    private final static Map<String, LaserLightSourceType> MAP;

    static {
        Map<String, LaserLightSourceType> map = new HashMap<String, LaserLightSourceType>();
        for ( LaserLightSourceType value : LaserLightSourceType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    LaserLightSourceType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static LaserLightSourceType fromString(String v) {
        return MAP.get(v);
    }
}