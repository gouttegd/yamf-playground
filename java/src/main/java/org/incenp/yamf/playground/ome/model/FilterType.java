package org.incenp.yamf.playground.ome.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/FilterType")
public enum FilterType {

    DICHROIC("Dichroic"),

    LONGPASS("LongPass"),

    SHORTPASS("ShortPass"),

    BANDPASS("BandPass"),

    MULTIPASS("MultiPass"),

    NEUTRALDENSITY("NeutralDensity"),

    TUNEABLE("Tuneable"),

    OTHER("Other");

    private final static Map<String, FilterType> MAP;

    static {
        Map<String, FilterType> map = new HashMap<String, FilterType>();
        for ( FilterType value : FilterType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    FilterType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static FilterType fromString(String v) {
        return MAP.get(v);
    }
}