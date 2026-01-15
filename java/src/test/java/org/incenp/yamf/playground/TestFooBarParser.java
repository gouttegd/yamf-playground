package org.incenp.yamf.playground;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.incenp.yamf.playground.model.foobar.FooBarInstrument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFooBarParser {
	
	@Test
	void testParseBaseFile() throws IOException {
		File f = new File("../samples/test-base.json");
		PIDINSTParser p = new PIDINSTParser();
		FooBarInstrument ins = p.parse(f, FooBarInstrument.class);
		
		Assertions.assertEquals("Alice", ins.getName());
        Assertions.assertEquals("a measured variable", ins.getMeasuredVariables().get(0));
        
        for (Map.Entry<String, Object> extension : ins.getExtensions().entrySet()) {
        	System.err.printf("Extension: %s: %s\n", extension.getKey(), extension.getValue().toString());
        }
        Assertions.assertTrue(ins.getExtensions().isEmpty());
	}
	
	@Test
	void testParseExtendedFile() throws IOException {
		File f = new File("../samples/test-extended-direct.json");
		PIDINSTParser p = new PIDINSTParser();
		FooBarInstrument ins = p.parse(f, FooBarInstrument.class);
		
		Assertions.assertEquals("Alice", ins.getName());
        Assertions.assertEquals("a measured variable", ins.getMeasuredVariables().get(0));
        Assertions.assertNotNull(ins.getFoo());
        Assertions.assertEquals("The name of the Foo", ins.getFoo().getFooName());
        Assertions.assertNotNull(ins.getBar());
        Assertions.assertEquals("The name of the Bar", ins.getBar().getBarName());
        
        Assertions.assertFalse(ins.getExtensions().containsKey("foo"));
        Assertions.assertFalse(ins.getExtensions().containsKey("bar"));
	}

    @Test
    void testParseBaseNestedFile() throws IOException {
        File f = new File("../samples/test-base.json");
        PIDINSTParser p = new PIDINSTParser();
        List<String> namespaces = new ArrayList<>();
        namespaces.add("com.example.foo");
        namespaces.add("org.example.bar");
        FooBarInstrument ins = p.parseNested(f, FooBarInstrument.class, namespaces);

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
        List<String> namespaces = new ArrayList<>();
        namespaces.add("com.example.foo");
        namespaces.add("org.example.bar");
        FooBarInstrument ins = p.parseNested(f, FooBarInstrument.class, namespaces);

        Assertions.assertEquals("Alice", ins.getName());
        Assertions.assertEquals("a measured variable", ins.getMeasuredVariables().get(0));
        Assertions.assertNotNull(ins.getFoo());
        Assertions.assertEquals("The name of the Foo", ins.getFoo().getFooName());
        Assertions.assertNotNull(ins.getBar());
        Assertions.assertEquals("The name of the Bar", ins.getBar().getBarName());

        Assertions.assertFalse(ins.getExtensions().containsKey("com.example.foo"));
        Assertions.assertFalse(ins.getExtensions().containsKey("org.example.bar"));
    }
}
