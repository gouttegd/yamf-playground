package org.incenp.yamf.playground;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.incenp.yamf.playground.model.foo.FooInstrument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFooParser {
	
	@Test
	void testParseBaseFile() throws IOException {
		File f = new File("../samples/test-base.json");
		PIDINSTParser p = new PIDINSTParser();
		FooInstrument ins = p.parse(f, FooInstrument.class);
		
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
		FooInstrument ins = p.parse(f, FooInstrument.class);
		
		Assertions.assertEquals("Alice", ins.getName());
        Assertions.assertEquals("a measured variable", ins.getMeasuredVariables().get(0));
        Assertions.assertNotNull(ins.getFoo());
        Assertions.assertEquals("The name of the Foo", ins.getFoo().getFooName());
        
        Assertions.assertFalse(ins.getExtensions().containsKey("foo"));
        Assertions.assertTrue(ins.getExtensions().containsKey("bar"));
	}
}
