package org.incenp.yamf.playground.pidinst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private List<String> extensionNamespaces = new ArrayList<>();

    public NestedExtensionConverter(Class<?> klass) {
        super(klass);

        // Find all extension slots that use a nested (namespaced) scheme.
        for ( Slot slot : this.klass.getSlots() ) {
            ExtensionNamespace annot = slot.getInnerType().getAnnotation(ExtensionNamespace.class);
            if ( annot != null ) {
                extensionNamespaces.add(annot.value());
            }
        }
    }

    @Override
    public void convertTo(Map<String, Object> rawMap, Object dest, ConverterContext ctx) throws LinkMLRuntimeException {
        // Move nested slots to the top-level object.
        for ( String namespace : extensionNamespaces ) {
            if ( rawMap.containsKey(namespace) ) {
                Map<String, Object> extension = toMap(rawMap.remove(namespace));
                for ( Map.Entry<String, Object> entry : extension.entrySet() ) {
                    rawMap.put(entry.getKey(), entry.getValue());
                }
            }
        }
        super.convertTo(rawMap, dest, ctx);
    }
}
