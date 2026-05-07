package org.incenp.yamf.playground;

import java.io.File;
import java.io.IOException;

import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.yamf.playground.ome.model.ArcLightSource;
import org.incenp.yamf.playground.ome.model.Detector;
import org.incenp.yamf.playground.ome.model.FocusingDevice;
import org.incenp.yamf.playground.ome.model.Instrument;
import org.incenp.yamf.playground.ome.model.Microscope;
import org.incenp.yamf.playground.ome.model.MicroscopeComponent;
import org.incenp.yamf.playground.ome.model.MicroscopeStand;
import org.incenp.yamf.playground.ome.model.NBOArcLightSource;
import org.incenp.yamf.playground.ome.model.Objective;
import org.incenp.yamf.playground.util.OverridingConverter;
import org.incenp.yamf.playground.util.YAMFParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestOMEParser {

    /*
     * Reads a file containing OME data into an instance of the OME model.
     */
    @Test
    void testParseOMEFile() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/ome/ome-base.json");
        YAMFParser<Instrument> p = new YAMFParser<>(Instrument.class);
        Instrument ins = p.parse(f, Instrument.class);
        
        Assertions.assertEquals("Zeiss", ins.getMicroscope().getManufacturer());
        Assertions.assertInstanceOf(Detector.class, ins.getComponents().get(0));
        Assertions.assertInstanceOf(Objective.class, ins.getComponents().get(1));
        Assertions.assertInstanceOf(ArcLightSource.class, ins.getComponents().get(2));
    }

    /*
     * Reads a file containing NBO data into an instance of the OME model.
     */
    @Test
    void testParseDirectNBOFile() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/ome/ome-nbo-direct.json");
        YAMFParser<Instrument> p = new YAMFParser<>(Instrument.class);
        Instrument ins = p.parse(f, Instrument.class);

        Assertions.assertEquals("Zeiss", ins.getMicroscope().getManufacturer());
        Assertions.assertInstanceOf(Detector.class, ins.getComponents().get(0));
        Assertions.assertInstanceOf(Objective.class, ins.getComponents().get(1));
        Assertions.assertInstanceOf(ArcLightSource.class, ins.getComponents().get(2));
        Assertions.assertInstanceOf(MicroscopeComponent.class, ins.getComponents().get(3));

        Assertions.assertTrue(ins.getMicroscope().getExtraSlots().containsKey("eye_piece_field_number"));
    }

    /*
     * Reads a file containing OME data into an instance of the NBO model.
     */
    @Test
    void testParseOMEFileToNBO() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/ome/ome-base.json");
        YAMFParser<Instrument> p = new YAMFParser<>(Instrument.class);
        Instrument ins = p.parse(f, Instrument.class);

        Assertions.assertEquals("Zeiss", ins.getMicroscope().getManufacturer());
        Assertions.assertInstanceOf(Detector.class, ins.getComponents().get(0));
        Assertions.assertInstanceOf(Objective.class, ins.getComponents().get(1));
        Assertions.assertInstanceOf(ArcLightSource.class, ins.getComponents().get(2));

        Assertions.assertFalse(ins.getMicroscope().getExtraSlots(true).containsKey("eye_piece_field_number"));
    }

    /*
     * Reads a file containing NBO data into an instance of the NBO model.
     */
    @Test
    void testParseNBOFileToNBO() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/ome/ome-nbo-direct.json");
        YAMFParser<Instrument> p = new YAMFParser<>(Instrument.class);
        Instrument ins = p.parse(f, Instrument.class);

        Assertions.assertEquals("Zeiss", ins.getMicroscope().getManufacturer());
        Assertions.assertInstanceOf(Detector.class, ins.getComponents().get(0));
        Assertions.assertInstanceOf(Objective.class, ins.getComponents().get(1));
        Assertions.assertInstanceOf(NBOArcLightSource.class, ins.getComponents().get(2));
        Assertions.assertInstanceOf(FocusingDevice.class, ins.getComponents().get(3));

        // FIXME: The converter has no way to know that we expect a NBO MicroscopeStand
        // rather than a OME Microscope, so the extension field is not parsed.
        // Possible solutions:
        // (a) Force the use of type designators in the base model, even when we expect
        // only one class (to account for the possibility of derived classes);
        // (b) Create a NBO Instrument class where we can force the use of a special
        // converter.
        // (c) Add the possibility of overriding a converter (see below).
        // (d) Other ideas?
        Assertions.assertTrue(ins.getMicroscope().getExtraSlots(true).containsKey("eye_piece_field_number"));
    }

    /*
     * This tests idea (c) above.
     */
    @Test
    void testParseNBOFileToNBOWithOverridingConverter() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/ome/ome-nbo-direct.json");
        YAMFParser<Instrument> p = new YAMFParser<>(Instrument.class);
        p.getContext().addConverter(new OverridingConverter(MicroscopeStand.class, Microscope.class));
        Instrument ins = p.parse(f, Instrument.class);

        Assertions.assertEquals("Zeiss", ins.getMicroscope().getManufacturer());
        Assertions.assertInstanceOf(Detector.class, ins.getComponents().get(0));
        Assertions.assertInstanceOf(Objective.class, ins.getComponents().get(1));
        Assertions.assertInstanceOf(NBOArcLightSource.class, ins.getComponents().get(2));
        Assertions.assertInstanceOf(FocusingDevice.class, ins.getComponents().get(3));

        Assertions.assertFalse(ins.getMicroscope().getExtraSlots(true).containsKey("eye_piece_field_number"));

        Assertions.assertInstanceOf(MicroscopeStand.class, ins.getMicroscope());
        MicroscopeStand stand = (MicroscopeStand) ins.getMicroscope();
        Assertions.assertEquals("eye piece field number", stand.getEyePieceFieldNumber());
    }
}
