package org.incenp.yamf.playground.ome.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/FrequencyUnit")
public enum FrequencyUnit {

    YHz("YHz"),

    ZHz("ZHz"),

    EHZ("EHz"),

    PHz("PHz"),

    THZ("THz"),

    GHZ("GHz"),

    MHz("MHz"),

    KHZ("kHz"),

    HHZ("hHz"),

    DAHZ("daHz"),

    HZ("Hz"),

    DHZ("dHz"),

    CHZ("cHz"),

    MHZ("mHz"),

    ΜHZ("μHz"),

    NHZ("nHz"),

    PHZ("pHz"),

    FHZ("fHz"),

    AHZ("aHz"),

    ZHZ("zHz"),

    YHZ("yHz");

    private final static Map<String, FrequencyUnit> MAP;

    static {
        Map<String, FrequencyUnit> map = new HashMap<String, FrequencyUnit>();
        for ( FrequencyUnit value : FrequencyUnit.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    FrequencyUnit(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static FrequencyUnit fromString(String v) {
        return MAP.get(v);
    }
}