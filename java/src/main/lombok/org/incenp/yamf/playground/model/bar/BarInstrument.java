package org.incenp.yamf.playground.model.bar;

import org.incenp.yamf.playground.model.Instrument;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(toBuilder = true)
@Data
public class BarInstrument extends Instrument {
    private Bar bar;
}
