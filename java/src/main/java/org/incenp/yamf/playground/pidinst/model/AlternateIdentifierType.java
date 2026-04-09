package org.incenp.yamf.playground.pidinst.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/pidinst/AlternateIdentifierType")
public enum AlternateIdentifierType {

    SERIALNUMBER("SerialNumber"),

    INVENTORYNUMBER("InventoryNumber"),

    OTHER("Other");

    private final static Map<String, AlternateIdentifierType> MAP;

    static {
        Map<String, AlternateIdentifierType> map = new HashMap<String, AlternateIdentifierType>();
        for ( AlternateIdentifierType value : AlternateIdentifierType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    AlternateIdentifierType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static AlternateIdentifierType fromString(String v) {
        return MAP.get(v);
    }
}