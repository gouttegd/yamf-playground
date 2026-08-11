package org.incenp.yamf.playground;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.incenp.linkml.core.ClassInfo;
import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.linkml.core.Slot;
import org.incenp.linkml.ext.ObjectLoader;
import org.incenp.yamf.playground.pidinst.model.ExtensionNode;
import org.incenp.yamf.playground.pidinst.model.FooInstrument;
import org.incenp.yamf.playground.pidinst.model.FooInstrumentExtension;
import org.incenp.yamf.playground.pidinst.model.PIDInstInstrument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.DatabindException;

public class TestLinkMLOnlyPIDINSTParser {

    private ObjectLoader loader = new ObjectLoader();

    /*
     * Reads a file containing "base" data into an instance of the base model.
     */
    @Test
    void testParseBaseFile() throws IOException, LinkMLRuntimeException {
        PIDInstInstrument ins = loader.loadObject(new File("../samples/pidinst/pidinst-base.json"),
                PIDInstInstrument.class);

        Assertions.assertEquals("Alice", ins.getName());

        roundtrip(PIDInstInstrument.class, ins);
    }

    /*
     * Reads a file containing data from the Foo and Bar extensions into an instance
     * of the base model.
     */
    @Test
    void testParseExtendedFile() throws IOException, LinkMLRuntimeException {
        // Needed to allow the LinkML context to recognise the extension by its URI
        ClassInfo.get(FooInstrumentExtension.class);
        PIDInstInstrument ins = loader.loadObject(new File("../samples/pidinst/pidinst-extended-ext.json"),
                PIDInstInstrument.class);
        
        Assertions.assertEquals("Alice", ins.getName());

        Map<String, ExtensionNode> extensions = getExtensionMap(ins);
        ExtensionNode fooNode = extensions.get("https://example.org/pidinst-foo-extension/FooInstrumentExtension");
        Assertions.assertNotNull(fooNode);
        Assertions.assertInstanceOf(FooInstrumentExtension.class, fooNode);

        roundtrip(PIDInstInstrument.class, ins);
    }

    /*
     * Reads a file containing data from the Foo and Bar extensions into an instance
     * of the extended Foo model.
     */
    @Test
    void testParseExtendedFileIntoExtendedModel() throws IOException, LinkMLRuntimeException {
        ClassInfo.get(FooInstrumentExtension.class);
        FooInstrument ins = loader.loadObject(new File("../samples/pidinst/pidinst-extended-ext.json"),
                FooInstrument.class);

        Assertions.assertEquals("Alice", ins.getName());

        // The default LinkML converter is not aware of the extension mechanism, so the
        // extension data should still be in the "extensions" attribute.
        Assertions.assertNull(ins.getFoo());
        Map<String, ExtensionNode> extensions = getExtensionMap(ins);
        ExtensionNode fooNode = extensions.get("https://example.org/pidinst-foo-extension/FooInstrumentExtension");
        Assertions.assertNotNull(fooNode);
        Assertions.assertInstanceOf(FooInstrumentExtension.class, fooNode);

        roundtrip(FooInstrument.class, ins);
    }

    private Map<String, ExtensionNode> getExtensionMap(Object o) throws LinkMLRuntimeException {
        Map<String, ExtensionNode> extensions = new HashMap<>();

        ClassInfo ci = ClassInfo.get(o.getClass());
        Slot extensionSlot = ci.getSlot("extensions");
        @SuppressWarnings("unchecked")
        List<ExtensionNode> extensionList = (List<ExtensionNode>) extensionSlot.getValue(o);

        for ( ExtensionNode node : extensionList ) {
            extensions.put(node.getExtensionType().toString(), node);
        }

        return extensions;
    }

    private <T extends PIDInstInstrument> void roundtrip(Class<T> type, T instrument)
            throws LinkMLRuntimeException, IOException, DatabindException, IOException {
        File tmp = new File("tmp.json");
        loader.dumpObject(tmp, instrument);
        T back = loader.loadObject(tmp, type);
        tmp.delete();

        Assertions.assertEquals(instrument, back);
    }
}
