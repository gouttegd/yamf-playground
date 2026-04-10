package org.incenp.yamf.playground.pidinst;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.incenp.linkml.core.ConverterContext;
import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.linkml.ext.ObjectLoader;
import org.incenp.yamf.playground.pidinst.model.FooInstrument;
import org.incenp.yamf.playground.pidinst.model.Instrument;

/**
 * An example of a LinkML-based parser for PIDINST data.
 */
public class PIDINSTParser {

    ObjectLoader loader = new ObjectLoader();

    public PIDINSTParser() {
        loader.getContext().addConverter(new NestedExtensionConverter(FooInstrument.class));
    }

    /**
     * Parses a PIDINST file into an {@link Instrument} object.
     * 
     * @param <T> The exact type of {@link Instrument} to parse. It can be either
     *            the base <code>Instrument</code> type itself, or any subtype
     *            declared in a schema extension.
     * @param f   The file to parse.
     * @param t   The exact type of {@link Instrument} to parse.
     * @return The parsed instrument instance.
     * @throws IOException            If any generic I/O error occurs.
     * @throws LinkMLRuntimeException If the file contains invalid data.
     */
    public <T extends Instrument> T parse(File f, Class<T> t) throws IOException, LinkMLRuntimeException {
        return loader.loadObject(f, t);
    }

    /**
     * Extracts an extension object from a LinkML generic extension slot.
     * 
     * @param <T>            The type of the extension object to extract.
     * @param ins            The instance object from which to extract the
     *                       extension.
     * @param namespace      The namespace under which the extension slot lives; may
     *                       be <code>null</code> if a non-nested extension model is
     *                       used.
     * @param name           The name of the root key for the extension object.
     * @param extensionClass The name of the root key for the extension object..
     * @return The extracted extension, or <code>null</code> if the instance object
     *         does not contain data pertaining to that extension or if the data is
     *         invalid.
     */
    public <T> T getExtension(Instrument ins, String namespace, String name, Class<T> extensionClass) {
        Object extension = null;
        if ( namespace != null ) { // Nested model
            extension = ins.getExtraSlots(true).get(namespace);
            if ( extension == null ) {
                return null;
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> tmp = (Map<String, Object>) extension;
            extension = tmp.get(name);
        } else {
            extension = ins.getExtraSlots(true).get(name);
        }

        if ( extension == null ) {
            return null;
        }

        try {
            ConverterContext ctx = loader.getContext();
            @SuppressWarnings("unchecked")
            T ext = (T) ctx.getConverter(extensionClass).convert(extension, ctx);
            return ext;
        } catch ( LinkMLRuntimeException e ) {

        }
        return null;
    }

    /**
     * Extracts an extension object from a LinkML generic extension slot, with
     * automatic detection of the extension namespace.
     * <p>
     * This method is similar to
     * {@link #getExtension(Instrument, String, String, Class)}, but it uses Java
     * annotation to automatically detect what is the expected namespace of the
     * extension, if the nested model of extension is used.
     * 
     * @param <T>            The type of the extension object to extract.
     * @param ins            The instance object from which to extract the
     *                       extension.
     * @param name           The name of the root key for the extension object.
     * @param extensionClass The name of the root key for the extension object.
     * @return The extracted extension, or <code>null</code> if the instance object
     *         does not contain data pertaining to that extension or if the data is
     *         invalid.
     */
    public <T> T getExtension(Instrument ins, String name, Class<T> extensionClass) {
        ExtensionNamespace annot = extensionClass.getAnnotation(ExtensionNamespace.class);
        String namespace = annot != null ? annot.value() : null;

        return getExtension(ins, namespace, name, extensionClass);
    }
}
