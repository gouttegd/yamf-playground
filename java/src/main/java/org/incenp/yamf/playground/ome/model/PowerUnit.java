package org.incenp.yamf.playground.ome.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/PowerUnit")
public enum PowerUnit {

    YW("YW"),

    ZW("ZW"),

    EW("EW"),

    PW("PW"),

    TW("TW"),

    GW("GW"),

    MW("MW"),

    kW("kW"),

    hW("hW"),

    daW("daW"),

    W("W"),

    dW("dW"),

    cW("cW"),

    mW("mW"),

    microW("μW"),

    nW("nW"),

    pW("pW"),

    fW("fW"),

    aW("aW"),

    zW("zW"),

    yW("yW");

    private final static Map<String, PowerUnit> MAP;

    static {
        Map<String, PowerUnit> map = new HashMap<String, PowerUnit>();
        for ( PowerUnit value : PowerUnit.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    PowerUnit(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static PowerUnit fromString(String v) {
        return MAP.get(v);
    }
}