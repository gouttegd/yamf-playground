package org.incenp.yamf.playground.pidinst.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/pidinst/RelationType")
public enum RelationType {

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

    private final static Map<String, RelationType> MAP;

    static {
        Map<String, RelationType> map = new HashMap<String, RelationType>();
        for ( RelationType value : RelationType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    RelationType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static RelationType fromString(String v) {
        return MAP.get(v);
    }
}