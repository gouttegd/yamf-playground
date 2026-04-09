package org.incenp.yamf.playground.pidinst.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/pidinst/PidinstVersion")
public enum PidinstVersion {

    NUMBER_1FULL_STOP0("1.0");

    private final static Map<String, PidinstVersion> MAP;

    static {
        Map<String, PidinstVersion> map = new HashMap<String, PidinstVersion>();
        for ( PidinstVersion value : PidinstVersion.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    PidinstVersion(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static PidinstVersion fromString(String v) {
        return MAP.get(v);
    }
}