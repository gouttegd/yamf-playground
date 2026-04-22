package org.incenp.yamf.playground.pidinst.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/pidinst/PIDInstVersion")
public enum PIDInstVersion {

    NUMBER_1FULL_STOP0("1.0");

    private final static Map<String, PIDInstVersion> MAP;

    static {
        Map<String, PIDInstVersion> map = new HashMap<String, PIDInstVersion>();
        for ( PIDInstVersion value : PIDInstVersion.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    PIDInstVersion(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static PIDInstVersion fromString(String v) {
        return MAP.get(v);
    }
}