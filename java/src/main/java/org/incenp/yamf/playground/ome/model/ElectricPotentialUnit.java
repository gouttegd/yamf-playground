package org.incenp.yamf.playground.ome.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/ElectricPotentialUnit")
public enum ElectricPotentialUnit {

    YV("YV"),

    ZV("ZV"),

    EV("EV"),

    PV("PV"),

    TV("TV"),

    GV("GV"),

    MV("MV"),

    kV("kV"),

    hV("hV"),

    daV("daV"),

    V("V"),

    dV("dV"),

    cV("cV"),

    mV("mV"),

    microV("µV"),

    nV("nV"),

    pV("pV"),

    fV("fV"),

    aV("aV"),

    zV("zV"),

    yV("yV");

    private final static Map<String, ElectricPotentialUnit> MAP;

    static {
        Map<String, ElectricPotentialUnit> map = new HashMap<String, ElectricPotentialUnit>();
        for ( ElectricPotentialUnit value : ElectricPotentialUnit.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    ElectricPotentialUnit(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static ElectricPotentialUnit fromString(String v) {
        return MAP.get(v);
    }
}