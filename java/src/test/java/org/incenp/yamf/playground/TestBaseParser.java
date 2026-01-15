package org.incenp.yamf.playground;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.incenp.yamf.playground.model.Instrument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestBaseParser {

    @Test
    void testParseBaseFile() throws IOException {
        File f = new File("../samples/test-base.json");
        PIDINSTParser p = new PIDINSTParser();
        Instrument ins = p.parse(f, Instrument.class);

        Assertions.assertEquals("Alice", ins.getName());
        Assertions.assertEquals("a measured variable", ins.getMeasuredVariables().get(0));

        for ( Map.Entry<String, Object> extension : ins.getExtensions().entrySet() ) {
            System.err.printf("Extension: %s: %s\n", extension.getKey(), extension.getValue().toString());
        }
        Assertions.assertTrue(ins.getExtensions().isEmpty());
    }

    @Test
    void testParseExtendedFile() throws IOException {
        File f = new File("../samples/test-extended-direct.json");
        PIDINSTParser p = new PIDINSTParser();
        Instrument ins = p.parse(f, Instrument.class);

        Assertions.assertEquals("Alice", ins.getName());
        Assertions.assertEquals("a measured variable", ins.getMeasuredVariables().get(0));

        Assertions.assertTrue(ins.getExtensions().containsKey("foo"));
        Assertions.assertTrue(ins.getExtensions().containsKey("bar"));
    }

    @Test
    void testParseBaseNestedFile() throws IOException {
        File f = new File("../samples/test-base.json");
        PIDINSTParser p = new PIDINSTParser();
        Instrument ins = p.parseNested(f);

        Assertions.assertEquals("Alice", ins.getName());
        Assertions.assertEquals("a measured variable", ins.getMeasuredVariables().get(0));

        for ( Map.Entry<String, Object> extension : ins.getExtensions().entrySet() ) {
            System.err.printf("Extension: %s: %s\n", extension.getKey(), extension.getValue().toString());
        }
        Assertions.assertTrue(ins.getExtensions().isEmpty());
    }

    @Test
    void testParseExtendedNestFile() throws IOException {
        File f = new File("../samples/test-extended-nested.json");
        PIDINSTParser p = new PIDINSTParser();
        Instrument ins = p.parseNested(f, Instrument.class, null);

        Assertions.assertEquals("Alice", ins.getName());
        Assertions.assertEquals("a measured variable", ins.getMeasuredVariables().get(0));

        Assertions.assertTrue(ins.getExtensions().containsKey("com.example.foo"));
        Assertions.assertTrue(ins.getExtensions().containsKey("org.example.bar"));
    }
}
