package com.usps.api.rest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.bind.JAXBException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.usps.core.model.PaginateResponse;
import com.usps.core.model.PaginateResponseStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/META-INF/spring/applicationContext.xml"})
public class PaginateAPIControllerServerIntegTest {
	private PaginateResponse paginateResponse;
	
	@Before
	public void setup() {
		paginateResponse = new PaginateResponse(PaginateResponseStatus.OK,new ArrayList<String>(Arrays.asList("1","2","3")));
	}

	@Test
	public void testJSON() throws JsonGenerationException, JsonMappingException, IOException {
//		String uri = "http://localhost:9080/SOA_Pagination/api/paginate";
//		
//		RestTemplate restTemplate = new RestTemplate();
//		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
//		
//		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//		String json = ow.writeValueAsString(paginateResponse);
//		
//		mockServer.expect(requestTo(uri))
//			.andExpect(method(HttpMethod.GET))
//			.andExpect(header("Accept",MediaType.APPLICATION_JSON.toString()))
//			.andRespond(withStatus(HttpStatus.OK)
//					.body(json)
//					.contentType(MediaType.APPLICATION_JSON));
//
//		HttpHeaders requestHeaders = new HttpHeaders();
//		requestHeaders.setAccept(new ArrayList<MediaType>(Arrays.asList(MediaType.APPLICATION_JSON)));		
//		HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
//		
//		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
//
//		mockServer.verify();
//		
//		assertTrue(responseEntity.getStatusCode().value() == HttpStatus.OK.value());
//		
//		System.out.println("This is JSON => " + responseEntity.getBody());
	}

	@Test
	public void testXML() throws JsonGenerationException, JsonMappingException, IOException, JAXBException {
//		String uri = "http://localhost:9080/SOA_Pagination/api/paginate";
//		
//		RestTemplate restTemplate = new RestTemplate();
//		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
//		
//		StringWriter sw = new StringWriter();
//		JAXBContext jaxbContext = JAXBContext.newInstance(PaginateResponse.class);
//		Marshaller marshaller = jaxbContext.createMarshaller();
//		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//		marshaller.marshal(paginateResponse, sw);
//		
//		String xml = sw.toString();
//		
//		mockServer.expect(requestTo(uri))
//			.andExpect(method(HttpMethod.GET))
//			.andExpect(header("Accept",MediaType.APPLICATION_XML.toString()))
//			.andRespond(withStatus(HttpStatus.OK)
//					.body(xml)
//					.contentType(MediaType.APPLICATION_XML));
//
//		HttpHeaders requestHeaders = new HttpHeaders();
//		requestHeaders.setAccept(new ArrayList<MediaType>(Arrays.asList(MediaType.APPLICATION_XML)));		
//		HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
//		
//		ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
//
//		mockServer.verify();
//		
//		assertTrue(responseEntity.getStatusCode().value() == HttpStatus.OK.value());
//		
//		System.out.println("This is XML => " + responseEntity.getBody());
	}

	@Test
	public void testObject() throws JsonGenerationException, JsonMappingException, IOException {
//		String uri = "http://localhost:9080/SOA_Pagination/api/paginate";
//		
//		RestTemplate restTemplate = new RestTemplate();
//		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
//		
//		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//		String json = ow.writeValueAsString(paginateResponse);
//		
//		mockServer.expect(requestTo(uri))
//			.andExpect(method(HttpMethod.GET))
//			.andExpect(header("Accept",MediaType.APPLICATION_JSON.toString()))
//			.andRespond(withStatus(HttpStatus.OK)
//					.body(json)
//					.contentType(MediaType.APPLICATION_JSON));
//
//		HttpHeaders requestHeaders = new HttpHeaders();
//		requestHeaders.setAccept(new ArrayList<MediaType>(Arrays.asList(MediaType.APPLICATION_JSON)));		
//		HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);
//		
//		ResponseEntity<PaginateResponse> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, PaginateResponse.class);
//
//		mockServer.verify();
//		
//		assertTrue(responseEntity.getStatusCode().value() == HttpStatus.OK.value());
//		
//		PaginateResponse response = responseEntity.getBody();
//		System.out.println("This is OBJECT = " + response);
	}
}
