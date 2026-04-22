package org.incenp.yamf.playground.pidinst.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/pidinst/PIDInstRelationType")
public enum PIDInstRelationType {

    ISDESCRIBEDBY("IsDescribedBy"),

    ISNEWVERSIONOF("IsNewVersionOf"),

    ISPREVIOUSVERSIONOF("IsPreviousVersionOf"),

    HASCOMPONENT("HasComponent"),

    ISCOMPONENTOF("IsComponentOf"),

    REFERENCES("References"),

    HASMETADATA("HasMetadata"),

    WASUSEDIN("WasUsedIn"),

    ISIDENTICALTO("IsIdenticalTo"),

    ISATTACHEDTO("IsAttachedTo");

    private final static Map<String, PIDInstRelationType> MAP;

    static {
        Map<String, PIDInstRelationType> map = new HashMap<String, PIDInstRelationType>();
        for ( PIDInstRelationType value : PIDInstRelationType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    PIDInstRelationType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static PIDInstRelationType fromString(String v) {
        return MAP.get(v);
    }
}