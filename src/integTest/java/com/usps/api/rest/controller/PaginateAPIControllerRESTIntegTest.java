package com.usps.api.rest.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.usps.core.model.PaginateRequest;
import com.usps.core.model.PaginateResponseStatus;
import com.usps.core.service.JSONService;
import com.usps.core.service.JSONServiceImpl;
import com.usps.core.service.PaginateService;
import com.usps.domain.Constants;

public class PaginateAPIControllerRESTIntegTest {
	private MockMvc mockMvc;
	private List<String> pages;
	private JSONService jsonService = new JSONServiceImpl();
	
	@InjectMocks
	PaginateAPIController controller;
	
	@Mock
	private PaginateService paginateService;
	
	@Before
	public void setup() {
		pages = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"));
		
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setMessageConverters(
						new MappingJackson2HttpMessageConverter(),
						new Jaxb2RootElementHttpMessageConverter()).build();
	}
	
	@Test
	public void test() throws Exception {
		int pageNumber = 1;
		int displayPages = 5;
		int startIndex = getStartIndex(pageNumber, displayPages);
		PaginateRequest paginateRequest = new PaginateRequest("" + pageNumber, "" + displayPages, pages);
		String json = jsonService.getString(paginateRequest);

		Mockito.when(paginateService.paginate(Mockito.any(PaginateRequest.class))).thenReturn(pages.subList(startIndex, startIndex + displayPages));
		
		mockMvc.perform(
			MockMvcRequestBuilders.
				post(Constants.URL_API_PAGINATE).
				content(json).
				contentType(MediaType.APPLICATION_JSON).
				accept(MediaType.APPLICATION_JSON))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.status").value(PaginateResponseStatus.OK.toString()))
			.andExpect(jsonPath("$.results[0]").value("1"))
			.andExpect(jsonPath("$.results[1]").value("2"))
			.andExpect(jsonPath("$.results[2]").value("3"))
			.andExpect(jsonPath("$.results[3]").value("4"))
			.andExpect(jsonPath("$.results[4]").value("5"));
	}
	
	public int getStartIndex(int pageNumber, int displayPages) {
		return displayPages * (pageNumber - 1);
	}
}
