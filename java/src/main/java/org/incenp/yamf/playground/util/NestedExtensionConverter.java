package org.incenp.yamf.playground.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.incenp.linkml.core.ConverterContext;
import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.linkml.core.ObjectConverter;
import org.incenp.linkml.core.Slot;

/**
 * A custom LinkML converter that can automatically recognise extensions
 * represented using the nested model.
 * <p>
 * At some point this may become part of LinkML-Java’s default ObjectConverter.
 */
public class NestedExtensionConverter extends ObjectConverter {

    private Map<String, Set<Slot>> namespacedExtensions = new HashMap<>();
    private boolean writeNested = false;

    /**
     * Creates a new instance.
     * 
     * @param klass The type to convert to and from.
     */
    public NestedExtensionConverter(Class<?> klass) {
        this(klass, false);
    }

    /**
     * Creates a new instance that can serialise extensions in their nested form.
     * <p>
     * The fact that writing extensions in nested form is parameterisable here is
     * mostly for testing purposes. In a "real" implementation, that behaviour would
     * likely be built-in.
     * 
     * @param klass       The type to convert to and from.
     * @param writeNested If <code>true</code>, extensions that are associated with
     *                    a namespace are written in "nested" form (under a key
     *                    corresponding to the namespace).
     */
    public NestedExtensionConverter(Class<?> klass, boolean writeNested) {
        super(klass);

        // Find all extension slots that use a nested (namespaced) scheme.
        for ( Slot slot : this.klass.getSlots() ) {
            ExtensionNamespace annot = slot.getInnerType().getAnnotation(ExtensionNamespace.class);
            if ( annot != null ) {
                namespacedExtensions.computeIfAbsent(annot.value(), k -> new HashSet<>()).add(slot);
            }
        }

        this.writeNested = writeNested;
    }

    @Override
    public void convertTo(Map<String, Object> rawMap, Object dest, ConverterContext ctx) throws LinkMLRuntimeException {
        // Move nested slots to the top-level object.
        for ( String namespace : namespacedExtensions.keySet() ) {
            if ( rawMap.containsKey(namespace) ) {
                Map<String, Object> extension = toMap(rawMap.remove(namespace));
                for ( Map.Entry<String, Object> entry : extension.entrySet() ) {
                    rawMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        super.convertTo(rawMap, dest, ctx);
    }

    @Override
    public Map<String, Object> serialise(Object object, boolean withIdentifier, ConverterContext ctx)
            throws LinkMLRuntimeException {
        Map<String, Object> raw = super.serialise(object, withIdentifier, ctx);

        if ( writeNested ) {
            // Move namespaced extensions under an intermediate key that represents the
            // namespace itself.
            for ( Map.Entry<String, Set<Slot>> entry : namespacedExtensions.entrySet() ) {
                Map<String, Object> values = new HashMap<>();
                for ( Slot slot : entry.getValue() ) {
                    Object value = raw.remove(slot.getLinkMLName());
                    if ( value != null ) {
                        values.put(slot.getLinkMLName(), value);
                    }
                }

                if ( !values.isEmpty() ) {
                    raw.put(entry.getKey(), values);
                }
            }
        }

        return raw;
    }
}
