package org.incenp.yamf.playground.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.incenp.linkml.core.ClassInfo;
import org.incenp.linkml.core.ConverterContext;
import org.incenp.linkml.core.LinkMLRuntimeException;
import org.incenp.linkml.core.LinkMLValueError;
import org.incenp.linkml.core.ObjectConverter;
import org.incenp.linkml.core.Slot;

public class ClassExtensionConverter extends ObjectConverter {

    private final static String EXTENSIONS_KEY = "extensions";

    private Map<String, ClassInfo> extensions = new HashMap<>();

    public ClassExtensionConverter(Class<?> klass) {
        super(klass);
    }

    public void registerExtension(ClassInfo extension) {
        extensions.put(extension.getURI(), extension);
    }

    @Override
    public void convertTo(Map<String, Object> rawMap, Object dest, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( !extensions.isEmpty() && rawMap.containsKey(EXTENSIONS_KEY) ) {
            Map<String, Object> rawExtensions = toMap(rawMap.get(EXTENSIONS_KEY));
            for ( String extensionId : extensions.keySet() ) {
                if ( rawExtensions.containsKey(extensionId) ) {
                    Map<String, Object> rawExtension = toMap(rawExtensions.remove(extensionId));
                    rawMap.putAll(rawExtension);
                }
            }
            if ( rawExtensions.isEmpty() ) {
                rawMap.remove(EXTENSIONS_KEY);
            }
        }

        super.convertTo(rawMap, dest, ctx);
    }

    @Override
    public Map<String, Object> serialise(Object object, boolean withIdentifier, ConverterContext ctx)
            throws LinkMLRuntimeException {
        Map<String, Object> raw = super.serialise(object, withIdentifier, ctx);

        Map<String, Object> rawExtensions = new HashMap<>();
        for ( String extensionId : extensions.keySet() ) {
            ClassInfo extension = extensions.get(extensionId);
            Map<String, Object> rawExtension = new HashMap<>();
            for ( Slot slot : extension.getSlots() ) {
                if ( !slot.isTypeDesignator() ) {
                    Object value = raw.remove(slot.getLinkMLName());
                    if ( value != null ) {
                        rawExtension.put(slot.getLinkMLName(), value);
                    }
                }
            }
            if ( !rawExtension.isEmpty() ) {
                rawExtensions.put(extensionId, rawExtension);
            }
        }
        if ( !rawExtensions.isEmpty() ) {
            Object existingRawExtensions = raw.get(EXTENSIONS_KEY);
            if ( existingRawExtensions != null ) {
                toMap(existingRawExtensions).putAll(rawExtensions);
            } else {
                raw.put(EXTENSIONS_KEY, rawExtensions);
            }
        }

        return raw;
    }

    /*
     * Workaround for a bug in LinkML-Java 0.2.2; already fixed in the main branch.
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> toMap(Object value) throws LinkMLRuntimeException {
        if ( !(value instanceof Map) ) {
            throw new LinkMLValueError("Invalid value type, map expected");
        }
        Map<Object, Object> map = (Map<Object, Object>) value;
        for ( Object key : map.keySet() ) {
            if ( !(key instanceof URI) && !(key instanceof String) ) {
                throw new LinkMLValueError("Invalid value type, string expected");
            }
        }
        return (Map<String, Object>) value;
    }
}
