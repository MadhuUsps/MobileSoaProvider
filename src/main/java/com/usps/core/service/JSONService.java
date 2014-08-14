package com.usps.core.service;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;

public interface JSONService {
	<T> T getObject(String json, Class<T> objectClass) throws JsonProcessingException, IOException;
	public String getString(Object json) throws JsonGenerationException, JsonMappingException, IOException;
}
