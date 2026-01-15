package org.incenp.yamf.playground.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Data
public class Manufacturer {
    private String manufacturerName;
    private ManufacturerIdentifier manufacturerIdentifier;
}
