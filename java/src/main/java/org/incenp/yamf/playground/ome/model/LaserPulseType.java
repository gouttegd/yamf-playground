package org.incenp.yamf.playground.ome.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/LaserPulseType")
public enum LaserPulseType {

    CW("CW"),

    SINGLE("Single"),

    QSWITCHED("QSwitched"),

    REPETITIVE("Repetitive"),

    MODELOCKED("ModeLocked"),

    OTHER("Other");

    private final static Map<String, LaserPulseType> MAP;

    static {
        Map<String, LaserPulseType> map = new HashMap<String, LaserPulseType>();
        for ( LaserPulseType value : LaserPulseType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    LaserPulseType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static LaserPulseType fromString(String v) {
        return MAP.get(v);
    }
}