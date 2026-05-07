package org.incenp.yamf.playground.ome.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/DetectorType")
public enum DetectorType {

    CCD("CCD"),

    INTENSIFIEDCCD("IntensifiedCCD"),

    ANALOGVIDEO("AnalogVideo"),

    PMT("PMT"),

    PHOTODIODE("Photodiode"),

    SPECTROSCOPY("Spectroscopy"),

    LIFETIMEIMAGING("LifetimeImaging"),

    CORRELATIONSPECTROSCOPY("CorrelationSpectroscopy"),

    FTIR("FTIR"),

    EMCCD("EMCCD"),

    APD("APD"),

    CMOS("CMOS"),

    EBCCD("EBCCD"),

    OTHER("Other");

    private final static Map<String, DetectorType> MAP;

    static {
        Map<String, DetectorType> map = new HashMap<String, DetectorType>();
        for ( DetectorType value : DetectorType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    DetectorType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static DetectorType fromString(String v) {
        return MAP.get(v);
    }
}