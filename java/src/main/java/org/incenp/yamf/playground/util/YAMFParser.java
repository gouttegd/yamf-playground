package org.incenp.yamf.playground.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.incenp.linkml.core.ClassInfo;
import org.incenp.linkml.core.ConverterContext;
import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.linkml.ext.ObjectLoader;

/**
 * A generic parser for YAMF-compliant objects.
 * 
 * @param <T> The type of object to parse. For extended models, this should be
 *            the base class.
 */
public class YAMFParser<T> {

    ObjectLoader loader = new ObjectLoader();
    ClassInfo ci;
    Set<Class<T>> extensionConverters = new HashSet<>();

    public YAMFParser(Class<T> klass) {
        ci = ClassInfo.get(klass);
    }

    /**
     * Gets the underlying converter context.
     */
    public ConverterContext getContext() {
        return loader.getContext();
    }

    /**
     * Parses a YAMF file into a readily usable object.
     * 
     * @param <V> The exact type of object to parse. It can either be the base type
     *            (same parameter as the YAMFParser instance), or any subtype
     *            declared in a schema extension.
     * @param f   The file to parse.
     * @param t   The exact type to parse.
     * @return The parsed object.
     * @throws IOException            If any generic I/O error occurs.
     * @throws LinkMLRuntimeException If the file contains invalid data.
     */
    public <V extends T> V parse(File f, Class<V> t) throws IOException, LinkMLRuntimeException {
        if ( !extensionConverters.contains(t) ) {
            loader.getContext().addConverter(new NestedExtensionConverter(t));
        }
        return loader.loadObject(f, t);
    }

    /**
     * Extracts an extension object from a LinkML generic extension slot.
     * 
     * @param <V>            The type of the extension object to extract.
     * @param object         The instance object from which to extract the
     *                       extension.
     * @param namespace      The namespace under which the extension slot lives; may
     *                       be <code>null</code> if a non-nested extension model is
     *                       used.
     * @param name           The name of the root key for extension object.
     * @param extensionClass The type of the extension.
     * @return The extracted extension, or <code>null</code> if the instance object
     *         does not contain data pertaining to that extension of if the data is
     *         invalid.
     */
    @SuppressWarnings("unchecked")
    public <V> V getExtension(T object, String namespace, String name, Class<V> extensionClass) {
        if ( !ci.hasExtensionSlot() ) {
            return null;
        }

        Map<String, Object> extensions = null;
        Object extension = null;

        try {
            // FIXME: In actual NGMF code, we should probably have a fundamental base object
            // whose extension slot is known in advance
            extensions = (Map<String, Object>) ci.getExtensionSlot().getValue(object);
        } catch ( LinkMLRuntimeException e ) {
            return null;
        }

        if (namespace != null) { // Nested model
            extension = extensions.get(namespace);
            if ( extension == null ) {
                return null;
            }

            Map<String, Object> tmp = (Map<String, Object>) extension;
            extension = tmp.get(name);
        } else {
            extension = extensions.get(name);
        }

        if ( extension == null ) {
            return null;
        }

        try {
            ConverterContext ctx = loader.getContext();
            V ext = (V) ctx.getConverter(extensionClass).convert(extension, ctx);
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
     * {@link #getExtension(Object, String, String, Class)}, but it uses a Java
     * annotation to automatically detect what is the expected namespace of the
     * extension, if the nested model of extension is used.
     * 
     * @param <V>            The type of the extension object to extract.
     * @param object         The instance object from which to extract the
     *                       extension.
     * @param name           The name of the root key for the extension slot.
     * @param extensionClass The type of the extension.
     * @return The extracted extension, or <code>null</code> if the instance object
     *         does not contain data pertaining to that extension or if the data is
     *         invalid.
     */
    public <V> V getExtension(T object, String name, Class<V> extensionClass) {
        ExtensionNamespace annot = extensionClass.getAnnotation(ExtensionNamespace.class);
        String namespace = annot != null ? annot.value() : null;

        return getExtension(object, namespace, name, extensionClass);
    }
}
