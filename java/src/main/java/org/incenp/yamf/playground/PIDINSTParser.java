package org.incenp.yamf.playground;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.incenp.yamf.playground.model.Instrument;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PIDINSTParser {

    public Instrument parse(File f) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(f));

        ObjectMapper mapper = new ObjectMapper(new JsonFactory());
        mapper.findAndRegisterModules();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        @SuppressWarnings("unchecked")
        Map<String, Object> rawMeta = mapper.readValue(reader, Map.class);

        Instrument ins = mapper.convertValue(rawMeta, Instrument.class);
        ins.setExtensions(getExtensions(rawMeta, Instrument.class));

        return ins;
    }
    
    public <T extends Instrument> T parse(File f, Class<T> t) throws IOException {
    	BufferedReader reader = new BufferedReader(new FileReader(f));
    	
    	ObjectMapper mapper = new ObjectMapper(new JsonFactory());
    	mapper.findAndRegisterModules();
    	mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	@SuppressWarnings("unchecked")
    	Map<String, Object> rawMeta = mapper.readValue(reader, Map.class);
    	
    	T ins = mapper.convertValue(rawMeta, t);
    	ins.setExtensions(getExtensions(rawMeta, t));
    	
    	return ins;
    }
    
    private HashMap<String, Object> getExtensions(Map<String, Object>map, Class<?> reference) {
    	Set<String> knownProperties = new HashSet<>();
    	for (Field f : reference.getDeclaredFields()) {
    		knownProperties.add(f.getName());
    	}
    	for (Field f : reference.getSuperclass().getDeclaredFields()) {
    		knownProperties.add(f.getName());
    	}
    	
    	HashMap<String, Object> extensions = new HashMap<>();
    	for (String key : map.keySet()) {
    		if (!knownProperties.contains(key)) {
    			extensions.put(key, map.get(key));
    		}
    	}
    	
    	return extensions;
    }
}
