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
import org.incenp.yamf.playground.pidinst.model.BaseExtensionObject;
import org.incenp.yamf.playground.pidinst.model.Foo;
import org.incenp.yamf.playground.pidinst.model.FooInstrument;
import org.incenp.yamf.playground.pidinst.model.FooInstrumentExtension;
import org.incenp.yamf.playground.pidinst.model.PIDInstInstrument;
import org.incenp.yamf.playground.util.ClassExtensionConverter;
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

        Map<String, BaseExtensionObject> extensions = getExtensionMap(ins);
        BaseExtensionObject fooNode = extensions
                .get("https://schemas.incenp.org/ngmf/v1/pidinst-foo-extension/FooInstrumentExtension");
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
        Map<String, BaseExtensionObject> extensions = getExtensionMap(ins);
        BaseExtensionObject fooNode = extensions
                .get("https://schemas.incenp.org/ngmf/v1/pidinst-foo-extension/FooInstrumentExtension");
        Assertions.assertNotNull(fooNode);
        Assertions.assertInstanceOf(FooInstrumentExtension.class, fooNode);

        roundtrip(FooInstrument.class, ins);
    }

    /*
     * Reads a file containing base model data into an instance of the extended Foo
     * model, using a extension-aware converter.
     */
    @Test
    void testParseBaseFileWithExtensionParser() throws IOException, LinkMLRuntimeException {
        ClassExtensionConverter fooInstrumentConverter = new ClassExtensionConverter(FooInstrument.class);
        fooInstrumentConverter.registerExtension(ClassInfo.get(FooInstrumentExtension.class));
        loader.getContext().addConverter(fooInstrumentConverter);

        FooInstrument ins = loader.loadObject(new File("../samples/pidinst/pidinst-base.json"), FooInstrument.class);
        Assertions.assertEquals("Alice", ins.getName());

        roundtrip(FooInstrument.class, ins);
    }

    @Test
    void testExtendedFileWithExtensionParser() throws IOException, LinkMLRuntimeException {
        ClassExtensionConverter fooInstrumentConverter = new ClassExtensionConverter(FooInstrument.class);
        fooInstrumentConverter.registerExtension(ClassInfo.get(FooInstrumentExtension.class));
        loader.getContext().addConverter(fooInstrumentConverter);

        FooInstrument ins = loader.loadObject(new File("../samples/pidinst/pidinst-extended-ext.json"),
                FooInstrument.class);
        Assertions.assertEquals("Alice", ins.getName());
        Foo foo = ins.getFoo();
        Assertions.assertNotNull(foo);
        Assertions.assertEquals(1, ins.getExtensions().size());

        roundtrip(FooInstrument.class, ins);
    }

    private Map<String, BaseExtensionObject> getExtensionMap(Object o) throws LinkMLRuntimeException {
        Map<String, BaseExtensionObject> extensions = new HashMap<>();

        ClassInfo ci = ClassInfo.get(o.getClass());
        Slot extensionSlot = ci.getSlot("extensions");
        @SuppressWarnings("unchecked")
        List<BaseExtensionObject> extensionList = (List<BaseExtensionObject>) extensionSlot.getValue(o);

        for ( BaseExtensionObject node : extensionList ) {
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
