package org.incenp.yamf.playground.pidinst.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/pidinst/PIDInstAlternateIdentifierType")
public enum PIDInstAlternateIdentifierType {

    SERIALNUMBER("SerialNumber"),

    INVENTORYNUMBER("InventoryNumber"),

    OTHER("Other");

    private final static Map<String, PIDInstAlternateIdentifierType> MAP;

    static {
        Map<String, PIDInstAlternateIdentifierType> map = new HashMap<String, PIDInstAlternateIdentifierType>();
        for ( PIDInstAlternateIdentifierType value : PIDInstAlternateIdentifierType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    PIDInstAlternateIdentifierType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static PIDInstAlternateIdentifierType fromString(String v) {
        return MAP.get(v);
    }
}