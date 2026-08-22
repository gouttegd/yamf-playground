package org.incenp.yamf.playground.util;

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
    private Map<Slot, String> extensionIdBySlot = new HashMap<>();

    public ClassExtensionConverter(Class<?> klass) {
        super(klass);
    }

    public void registerExtension(ClassInfo extension) {
        extensions.put(extension.getURI(), extension);

        for ( Slot extensionSlot : extension.getSlots() ) {
            if ( extensionSlot.isExtensionStore() ) {
                continue;
            }
            Slot extendedSlot = klass.getSlot(extensionSlot.getLinkMLName());
            if ( extendedSlot != null )
                extensionIdBySlot.put(extendedSlot, extension.getURI());
        }
    }

    @Override
    public void convertTo(Map<String, Object> rawMap, Object dest, ConverterContext ctx) throws LinkMLRuntimeException {
        if ( !extensions.isEmpty() && rawMap.containsKey(EXTENSIONS_KEY) ) {
            Map<String, Object> rawExtensions = toMap(rawMap.get(EXTENSIONS_KEY));
            for ( String extensionId : extensions.keySet() ) {
                if ( rawExtensions.containsKey(extensionId) ) {
                    Map<String, Object> rawExtension = toMap(rawExtensions.remove(extensionId));

                    for ( Map.Entry<String, Object> entry : rawExtension.entrySet() ) {
                        Slot slot = klass.getSlot(entry.getKey());
                        if ( slot != null ) {
                            ctx.getConverter(slot).convertForSlot(entry.getValue(), dest, slot, ctx);
                        }
                    }
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
        // FIXME: The LinkML-Java runtime should be updated to provide the
        // `initSerialise` and `serialiseSlot` methods.
        if ( !getType().isInstance(object) ) {
            throw new LinkMLValueError(String.format("Invalid value type, '%s' expected", getType().getName()));
        }

        Map<String, Object> raw = new HashMap<>();
        initSerialise(object, withIdentifier, raw, ctx);
        for ( Slot slot : klass.getSlots() ) {
            if ( (slot.isIdentifier() && !withIdentifier) ) {
                continue;
            }

            Object slotValue = slot.getValue(object);
            if ( slotValue == null && slot.isTypeDesignator() ) {
                if ( slot.isMultivalued() ) {
                    slotValue = ctx.getTypeDesignatorResolver().getDesignators(klass);
                } else {
                    slotValue = ctx.getTypeDesignatorResolver().getDesignator(klass);
                }
            } else if ( slotValue == null ) {
                continue;
            }

            if ( slot.isExtensionStore() ) {
                for ( Map.Entry<String, Object> extension : toMap(slotValue).entrySet() ) {
                    raw.put(extension.getKey(), extension.getValue());
                }
            } else {
                serialiseSlot(slot, slotValue, raw, ctx);
            }
        }

        return raw;
    }

    protected void initSerialise(Object object, boolean withIdentifier, Map<String, Object> raw, ConverterContext ctx)
            throws LinkMLRuntimeException {
        // Process all unregistered extensions immediately.
        Slot extensionsSlot = klass.getSlot(EXTENSIONS_KEY);
        if ( extensionsSlot != null ) {
            Object extensions = extensionsSlot.getValue(object);
            if ( extensions != null ) {
                Object rawExtensions = ctx.getConverter(extensionsSlot).serialiseForSlot(extensions, extensionsSlot,
                        ctx);
                if ( rawExtensions != null ) {
                    raw.put(EXTENSIONS_KEY, rawExtensions);
                }
            }
        }
    }

    protected void serialiseSlot(Slot slot, Object value, Map<String, Object> dest, ConverterContext ctx)
            throws LinkMLRuntimeException {
        if ( slot.getLinkMLName().equals(EXTENSIONS_KEY) ) {
            // Already dealt with in initSerialise.
            return;

        }
        Object rawValue = ctx.getConverter(slot).serialiseForSlot(value, slot, ctx);
        if ( rawValue == null ) {
            return;
        }

        if ( extensionIdBySlot.containsKey(slot) ) {
            // This slot belongs to an extension. We must store it at the proper place under
            // the extensions key.
            String id = extensionIdBySlot.get(slot);
            @SuppressWarnings("unchecked")
            Map<String, Map<String, Object>> extensionsMap = (Map<String, Map<String, Object>>) dest
                    .get(EXTENSIONS_KEY);
            if ( extensionsMap == null ) {
                extensionsMap = new HashMap<>();
                dest.put(EXTENSIONS_KEY, extensionsMap);
            }
            Map<String, Object> extensionMap = extensionsMap.get(id);
            if ( extensionMap == null ) {
                extensionMap = new HashMap<>();
                extensionsMap.put(id, extensionMap);
            }
            extensionMap.put(slot.getLinkMLName(), rawValue);
        } else {
            // This is a "normal" slot, to be stored at its natural place.
            dest.put(slot.getLinkMLName(), rawValue);
        }
    }
}
