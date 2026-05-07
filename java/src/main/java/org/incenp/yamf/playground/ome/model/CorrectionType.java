package org.incenp.yamf.playground.ome.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/CorrectionType")
public enum CorrectionType {

    UV("UV"),

    PLANAPO("PlanApo"),

    PLANFLUOR("PlanFluor"),

    SUPERFLUOR("SuperFluor"),

    VIOLETCORRECTED("VioletCorrected"),

    ACHRO("Achro"),

    ACHROMAT("Achromat"),

    FLUOR("Fluor"),

    FL("Fl"),

    FLUAR("Fluar"),

    NEOFLUAR("Neofluar"),

    FLUOTAR("Fluotar"),

    APO("Apo"),

    PLANNEOFLUAR("PlanNeofluar"),

    OTHER("Other");

    private final static Map<String, CorrectionType> MAP;

    static {
        Map<String, CorrectionType> map = new HashMap<String, CorrectionType>();
        for ( CorrectionType value : CorrectionType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    CorrectionType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static CorrectionType fromString(String v) {
        return MAP.get(v);
    }
}