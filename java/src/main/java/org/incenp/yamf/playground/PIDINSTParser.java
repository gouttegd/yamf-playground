package org.incenp.yamf.playground;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.incenp.yamf.playground.model.Instrument;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PIDINSTParser {

    private ObjectMapper mapper;

    public PIDINSTParser() {
        mapper = new ObjectMapper(new JsonFactory());
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public Instrument parse(File f) throws IOException {
        Map<String, Object> rawMeta = readJson(f);
        Instrument ins = mapper.convertValue(rawMeta, Instrument.class);
        ins.setExtensions(getExtensions(rawMeta, Instrument.class));

        return ins;
    }

    public <T extends Instrument> T parse(File f, Class<T> t) throws IOException {
        Map<String, Object> rawMeta = readJson(f);
        T ins = mapper.convertValue(rawMeta, t);
        ins.setExtensions(getExtensions(rawMeta, t));

        return ins;
    }

    private HashMap<String, Object> getExtensions(Map<String, Object> map, Class<?> reference) {
        Set<String> knownProperties = new HashSet<>();
        for ( Field f : reference.getDeclaredFields() ) {
            knownProperties.add(f.getName());
        }
        for ( Field f : reference.getSuperclass().getDeclaredFields() ) {
            knownProperties.add(f.getName());
        }

        HashMap<String, Object> extensions = new HashMap<>();
        for ( String key : map.keySet() ) {
            if ( !knownProperties.contains(key) ) {
                extensions.put(key, map.get(key));
            }
        }

        return extensions;
    }

    public Instrument parseNested(File f) throws IOException {
        Map<String, Object> rawMeta = readJson(f);
        Instrument ins = mapper.convertValue(rawMeta, Instrument.class);

        HashMap<String, Object> extensions = new HashMap<>();
        for ( String key : rawMeta.keySet() ) {
            if ( key.contains(".") ) {
                extensions.put(key, rawMeta.get(key));
            }
        }
        ins.setExtensions(extensions);

        return ins;
    }

    public <T extends Instrument> T parseNested(File f, Class<T> t, List<String> namespaces) throws IOException {
        Map<String, Object> rawMeta = readJson(f);

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

        T ins = mapper.convertValue(rawMeta, t);

        HashMap<String, Object> extensions = new HashMap<>();
        for ( String key : rawMeta.keySet() ) {
            if ( key.contains(".") ) {
                extensions.put(key, extensions);
            }
        }
        ins.setExtensions(extensions);

        return ins;
    }

    private Map<String, Object> readJson(File f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));
        @SuppressWarnings("unchecked")
        Map<String, Object> rawMap = mapper.readValue(reader, Map.class);
        return rawMap;
    }

    public <T> T getExtension(Instrument ins, String namespace, String name, Class<T> extensionClass) {
        Map<String, Object> extensionRoot = ins.getExtensions();
        if ( namespace != null ) { // Nested model
            Object rawExtensionRoot = extensionRoot.get(namespace);
            if ( rawExtensionRoot != null ) {
                @SuppressWarnings("unchecked")
                Map<String, Object> tmp = (Map<String, Object>) rawExtensionRoot;
                extensionRoot = tmp;
            } else {
                return null;
            }
        }

        Object rawExtension = extensionRoot.get(name);
        if ( rawExtension != null ) {
            try {
                T extension = mapper.convertValue(rawExtension, extensionClass);
                return extension;
            } catch ( IllegalArgumentException e ) {
            }
        }

        return null;
    }
}
