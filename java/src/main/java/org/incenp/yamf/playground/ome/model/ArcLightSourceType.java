package org.incenp.yamf.playground.ome.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/ArcLightSourceType")
public enum ArcLightSourceType {

    HG("Hg"),

    XE("Xe"),

    HGXE("HgXe"),

    OTHER("Other");

    private final static Map<String, ArcLightSourceType> MAP;

    static {
        Map<String, ArcLightSourceType> map = new HashMap<String, ArcLightSourceType>();
        for ( ArcLightSourceType value : ArcLightSourceType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    ArcLightSourceType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static ArcLightSourceType fromString(String v) {
        return MAP.get(v);
    }
}