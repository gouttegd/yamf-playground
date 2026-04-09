package org.incenp.yamf.playground.pidinst.model;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import org.incenp.linkml.core.annotations.LinkURI;

@LinkURI("https://example.org/pidinst/RelatedIdentifierType")
public enum RelatedIdentifierType {

    ARK("ARK"),

    ARXIV("arXiv"),

    BIBCODE("bibcode"),

    DOI("DOI"),

    EAN13("EAN13"),

    EISSN("EISSN"),

    HANDLE("Handle"),

    IGSN("IGSN"),

    ISBN("ISBN"),

    ISSN("ISSN"),

    ISTC("ISTC"),

    LISSN("LISSN"),

    PMID("PMID"),

    PURL("PURL"),

    RAID("RAiD"),

    RRID("RRID"),

    UPC("UPC"),

    URL("URL"),

    URN("URN"),

    W3ID("w3id");

    private final static Map<String, RelatedIdentifierType> MAP;

    static {
        Map<String, RelatedIdentifierType> map = new HashMap<String, RelatedIdentifierType>();
        for ( RelatedIdentifierType value : RelatedIdentifierType.values() ) {
            map.put(value.toString(), value);
        }

        MAP = Collections.unmodifiableMap(map);
    }

    private final String repr;

    RelatedIdentifierType(String repr) {
        this.repr = repr;
    }

    @Override
    public String toString() {
        return repr;
    }

    public static RelatedIdentifierType fromString(String v) {
        return MAP.get(v);
    }
}