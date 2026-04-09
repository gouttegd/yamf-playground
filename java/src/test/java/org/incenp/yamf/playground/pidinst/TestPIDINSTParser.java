package org.incenp.yamf.playground.pidinst;

import java.io.File;
import java.io.IOException;

import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.yamf.playground.pidinst.model.Foo;
import org.incenp.yamf.playground.pidinst.model.FooInstrument;
import org.incenp.yamf.playground.pidinst.model.Instrument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPIDINSTParser {

    /*
     * Reads a file containing "base" data into an instance of the base model.
     */
    @Test
    void testParseSimpleFile() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/pidinst/pidinst-base.json");
        PIDINSTParser p = new PIDINSTParser();
        Instrument ins = p.parse(f, Instrument.class);

        Assertions.assertEquals("Alice", ins.getName());
    }

    /*
     * Reads a file containing extended data into an instance of the base model.
     */
    @Test
    void testParseDirectExtendedFile() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/pidinst/pidinst-extended-direct.json");
        PIDINSTParser p = new PIDINSTParser();
        Instrument ins = p.parse(f, Instrument.class);

        Assertions.assertEquals("Alice", ins.getName());

        Assertions.assertTrue(ins.getExtraSlots().containsKey("foo"));
        Assertions.assertTrue(ins.getExtraSlots().containsKey("bar"));
    }

    /*
     * Reads a file containing extended data (using the nested model) into an
     * instance of the base model.
     */
    @Test
    void testParseDirectNestedFile() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/pidinst/pidinst-extended-nested.json");
        PIDINSTParser p = new PIDINSTParser();
        Instrument ins = p.parse(f, Instrument.class);

        Assertions.assertEquals("Alice", ins.getName());

        Assertions.assertTrue(ins.getExtraSlots().containsKey("com.example.foo"));
        Assertions.assertTrue(ins.getExtraSlots().containsKey("org.example.bar"));
    }

    /*
     * Reads a file containing base data into an instance of the extended model.
     */
    @Test
    void testParseFooSimpleFile() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/pidinst/pidinst-base.json");
        PIDINSTParser p = new PIDINSTParser();
        FooInstrument ins = p.parse(f, FooInstrument.class);

        Assertions.assertEquals("Alice", ins.getName());
        Assertions.assertNull(ins.getFoo());
    }

    /*
     * Reads a file containing extended data into an instance of the extended model.
     */
    @Test
    void testParseFooDirectExtendedFile() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/pidinst/pidinst-extended-direct.json");
        PIDINSTParser p = new PIDINSTParser();
        FooInstrument ins = p.parse(f, FooInstrument.class);

        Assertions.assertEquals("Alice", ins.getName());
        Assertions.assertNotNull(ins.getFoo());
        Assertions.assertEquals("The name of the Foo", ins.getFoo().getName());

        Assertions.assertFalse(ins.getExtraSlots().containsKey("foo"));
        Assertions.assertTrue(ins.getExtraSlots().containsKey("bar"));
    }

    /*
     * Reads a file containing extended data (using the nested model) into an
     * instance of the extended model.
     */
    @Test
    void testParseFooNestedExtendedFile() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/pidinst/pidinst-extended-nested.json");
        PIDINSTParser p = new PIDINSTParser();
        FooInstrument ins = p.parse(f, FooInstrument.class);

        Assertions.assertEquals("Alice", ins.getName());
        Assertions.assertNotNull(ins.getFoo());
        Assertions.assertEquals("The name of the Foo", ins.getFoo().getName());

        Assertions.assertFalse(ins.getExtraSlots().containsKey("com.example.foo"));
        Assertions.assertTrue(ins.getExtraSlots().containsKey("org.example.bar"));
    }

    /*
     * Reads a file containing extended data into an instance of the base model,
     * then extracts the Foo extension.
     */
    @Test
    void testGetExtensionFromDirect() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/pidinst/pidinst-extended-direct.json");
        PIDINSTParser p = new PIDINSTParser();
        Instrument ins = p.parse(f, Instrument.class);

        Assertions.assertEquals("Alice", ins.getName());
        Foo ext = p.getExtension(ins, null, "foo", Foo.class);
        Assertions.assertNotNull(ext);
        Assertions.assertEquals("The name of the Foo", ext.getName());
    }

    /*
     * Reads a file containing extended data (using the nested model) into an
     * instance of the base model, then extracts the Foo extension.
     */
    @Test
    void testGetExtensionFromNested() throws IOException, LinkMLRuntimeException {
        File f = new File("../samples/pidinst/pidinst-extended-nested.json");
        PIDINSTParser p = new PIDINSTParser();
        Instrument ins = p.parse(f, Instrument.class);

        Assertions.assertEquals("Alice", ins.getName());
        Foo ext = p.getExtension(ins, "foo", Foo.class);
        Assertions.assertNotNull(ext);
        Assertions.assertEquals("The name of the Foo", ext.getName());
    }
}
