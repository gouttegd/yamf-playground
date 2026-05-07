package org.incenp.yamf.playground.ome.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/LengthUnit")
public enum LengthUnit {

    Ym("Ym"),

    Zm("Zm"),

    Em("Em"),

    Pm("Pm"),

    Tm("Tm"),

    Gm("Gm"),

    Mm("Mm"),

    km("km"),

    hm("hm"),

    ham("ham"),

    m("m"),

    dm("dm"),

    cm("cm"),

    mm("mm"),

    microm("µm"),

    nm("nm"),

    pm("pm"),

    fm("fm"),

    am("am"),

    zm("zm"),

    ym("ym"),

    Å("Å"),

    THOU("thou"),

    LI("li"),

    LN("ln"),

    FT("ft"),

    YD("yd"),

    ML("ml"),

    UA("ua"),

    LY("ly"),

    PC("pc"),

    PT("pt"),

    PIXEL("pixel"),

    REFERENCE_FRAME("reference frame");

    private final static Map<String, LengthUnit> MAP;

    static {
        Map<String, LengthUnit> map = new HashMap<String, LengthUnit>();
        for ( LengthUnit value : LengthUnit.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    LengthUnit(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static LengthUnit fromString(String v) {
        return MAP.get(v);
    }
}