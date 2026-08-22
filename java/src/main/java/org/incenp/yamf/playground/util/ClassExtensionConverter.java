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
        // FIXME: The LinkML-Java runtime should be more flexible here. We should be
        // able to customise the serialisation of slots without having to rewrite the
        // entire method. We would need something like "serialiseSlot".
        if ( !getType().isInstance(object) ) {
            throw new LinkMLValueError(String.format("Invalid value type, '%s' expected", getType().getName()));
        }

        Map<Slot, String> extensionsBySlot = new HashMap<>();
        for ( Map.Entry<String, ClassInfo> extension : extensions.entrySet() ) {
            for ( Slot extensionSlot : extension.getValue().getSlots() ) {
                if ( extensionSlot.isExtensionStore() ) {
                    continue;
                }
                Slot extendedSlot = klass.getSlot(extensionSlot.getLinkMLName());
                if ( extendedSlot != null ) {
                    extensionsBySlot.put(extendedSlot, extension.getKey());
                }
            }
        }

        Map<String, Object> raw = new HashMap<>();
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
            } else if ( extensionsBySlot.containsKey(slot) ) {
                String extensionId = extensionsBySlot.get(slot);
                @SuppressWarnings("unchecked")
                Map<String, Map<String, Object>> rawExtensions = (Map<String, Map<String, Object>>) raw
                        .get(EXTENSIONS_KEY);
                if ( rawExtensions == null ) {
                    rawExtensions = new HashMap<>();
                    raw.put(EXTENSIONS_KEY, rawExtensions);
                }
                Map<String, Object> rawExtension = rawExtensions.get(extensionId);
                if ( rawExtension == null ) {
                    rawExtension = new HashMap<>();
                    rawExtensions.put(extensionId, rawExtension);
                }
                Object rawValue = ctx.getConverter(slot).serialiseForSlot(slotValue, slot, ctx);
                if ( rawValue != null ) {
                    rawExtension.put(slot.getLinkMLName(), rawValue);
                }
            } else if ( slot.getLinkMLName().equals(EXTENSIONS_KEY) ) {
                Object o = ctx.getConverter(slot).serialiseForSlot(slotValue, slot, ctx);

                @SuppressWarnings("unchecked")
                Map<String, Map<String, Object>> rawExtensions = (Map<String, Map<String, Object>>) raw
                        .get(EXTENSIONS_KEY);
                if ( rawExtensions == null ) {
                    raw.put(EXTENSIONS_KEY, o);
                } else {
                    @SuppressWarnings("unchecked")
                    Map<String, Map<String, Object>> unknownExtensions = (Map<String, Map<String, Object>>) o;
                    rawExtensions.putAll(unknownExtensions);
                }
            } else {
                Object o = ctx.getConverter(slot).serialiseForSlot(slotValue, slot, ctx);
                if ( o != null ) {
                    raw.put(slot.getLinkMLName(), o);
                }
            }
        }

        return raw;
    }
}
