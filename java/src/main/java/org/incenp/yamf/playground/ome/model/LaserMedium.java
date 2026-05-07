package org.incenp.yamf.playground.ome.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/ome/LaserMedium")
public enum LaserMedium {

    CU("Cu"),

    AG("Ag"),

    ARFL("ArFl"),

    ARCL("ArCl"),

    KRFL("KrFl"),

    KRCL("KrCl"),

    XEFL("XeFl"),

    XECL("XeCl"),

    XEBR("XeBr"),

    N("N"),

    AR("Ar"),

    KR("Kr"),

    XE("Xe"),

    HENE("HeNe"),

    HECD("HeCd"),

    CO("CO"),

    CO2("CO2"),

    H2O("H2O"),

    HFL("HFl"),

    NDGLASS("NdGlass"),

    NDYAG("NdYAG"),

    ERGLASS("ErGlass"),

    ERYAG("ErYAG"),

    HOYLF("HoYLF"),

    HOYAG("HoYAG"),

    RUBY("Ruby"),

    TISAPPHIRE("TiSapphire"),

    ALEXANDRITE("Alexandrite"),

    RHODAMINE6G("Rhodamine6G"),

    COUMARINC30("CoumarinC30"),

    GAAS("GaAs"),

    GAALAS("GaAlAs"),

    EMINUS("EMinus"),

    OTHER("Other");

    private final static Map<String, LaserMedium> MAP;

    static {
        Map<String, LaserMedium> map = new HashMap<String, LaserMedium>();
        for ( LaserMedium value : LaserMedium.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    LaserMedium(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static LaserMedium fromString(String v) {
        return MAP.get(v);
    }
}