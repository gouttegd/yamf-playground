package org.incenp.yamf.playground.pidinst;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.incenp.linkml.core.ConverterContext;
import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.yamf.playground.pidinst.model.Instrument;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * An example of a LinkML-based parser for PIDINST data.
 */
public class PIDINSTParser {

    ObjectMapper mapper = new ObjectMapper();
    ConverterContext ctx = new ConverterContext();

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
        // Automatically detect supported nested extensions
        List<String> extensionNamespaces = new ArrayList<>();
        for ( Field field : t.getDeclaredFields() ) {
            ExtensionNamespace annot = field.getType().getAnnotation(ExtensionNamespace.class);
            if ( annot != null ) {
                extensionNamespaces.add(annot.value());
            }
        }
        return parseNested(f, t, extensionNamespaces);
    }

    /**
     * Parses a PIDINST file into an {@link Instrument} object, with support for the
     * “nested model” of extension.
     * 
     * @param <T>        The exact type of {@link Instrument} to parse. It can be
     *                   either the base <code>Instrument</code> type itself, or any
     *                   subtype declared in a schema extension.
     * @param f          The file to parse.
     * @param t          The exact type of {@link Instrument} to parse.
     * @param namespaces The namespaces in which the extension slots live, if any.
     *                   In a future version, those may be declared directly on the
     *                   <code>T</code> type so that they don’t have to be
     *                   explicitly specified.
     * @return The parsed instrument instance.
     * @throws IOException            If any generic I/O error occurs.
     * @throws LinkMLRuntimeException If the file contains invalid data.
     */
    public <T extends Instrument> T parseNested(File f, Class<T> t, List<String> namespaces)
            throws IOException, LinkMLRuntimeException {
        // Read the file into a generic map
        BufferedReader reader = new BufferedReader(new FileReader(f));
        @SuppressWarnings("unchecked")
        Map<String, Object> rawMeta = mapper.readValue(reader, Map.class);

        // Find extended data and move them to the top-level
        if ( namespaces != null ) {
            for ( String namespace : namespaces ) {
                if ( rawMeta.containsKey(namespace) ) {
                    Object rawExtension = rawMeta.remove(namespace);

                    @SuppressWarnings("unchecked")
                    Map<String, Object> extensionAsMap = mapper.convertValue(rawExtension, Map.class);
                    for ( Map.Entry<String, Object> entry : extensionAsMap.entrySet() ) {
                        rawMeta.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }

        // Convert the generic map into the actual object
        @SuppressWarnings("unchecked")
        T ins = (T) ctx.getConverter(t).convert(rawMeta, ctx);

        return ins;
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
