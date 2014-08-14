package com.usps.core.service;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.stereotype.Service;

@Service
public class JSONServiceImpl implements JSONService {
    private final Logger log = Logger.getLogger(this.getClass());
    
	@Override
	public <T> T getObject(String json, Class<T> objectClass) throws JsonProcessingException, IOException {
		log.debug("Create JSON Object '" + objectClass + "' from json string...\n" + json);
		ObjectReader or = new ObjectMapper().reader(objectClass);
		return or.readValue(json);
	}
	
	public String getString(Object json) throws JsonGenerationException, JsonMappingException, IOException {
		log.debug("Create JSON string from object => " + json);
		ObjectWriter ow = new ObjectMapper().writerWithDefaultPrettyPrinter();
		return ow.writeValueAsString(json);
	}
}
