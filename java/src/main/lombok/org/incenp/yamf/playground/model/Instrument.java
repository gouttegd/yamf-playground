package org.incenp.yamf.playground.model;

import java.util.HashMap;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Data
public class Instrument {
    private Identifier identifier;
    private String schemaVersion;
    private String landingPage;
    private String name;
    private List<Owner> owners;
    private List<Manufacturer> manufacturers;
    private Model model;
    private String description;
    private List<InstrumentType> instrumentTypes;
    private List<String> measuredVariables;
    private List<Date> dates;
    private List<RelatedIdentifier> relatedIdentifiers;
    private List<AlternateIdentifier> alternateIdentifiers;
    private HashMap<String,Object> extensions;
}
