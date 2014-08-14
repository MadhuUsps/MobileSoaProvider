package com.usps.core.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.usps.core.service.PaginateService;
import com.usps.core.service.PaginateServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/META-INF/spring/applicationContext.xml"})
public class PaginateServiceTest {
	@Autowired
	private PaginateService paginateService;
	private List<String> pages;
	
	@Before
	public void setup() {
		pages = new ArrayList<String>(Arrays.asList("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPageNumberArgumentNegative() {
		paginateService.paginate(-1,1,pages);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testPageNumberArgumentZero() {
		paginateService.paginate(0,1,pages);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDisplayPagesArgumentNegative() {
		paginateService.paginate(1,-1,pages);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDisplayPagesArgumentZero() {
		paginateService.paginate(1,0,pages);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testPagesNull() {
		paginateService.paginate(1,0,null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testPagesEmpty() {
		paginateService.paginate(1,0,new ArrayList<String>());
	}

	@Test
	public void testSuccessAllParams() {
		testSuccess(1, 5);
		testSuccess(2, 5);
		testSuccess(3, 5);
		testSuccess(2, 3);
		testSuccess(5, 1);
		testSuccess(1, 15);
	}
	
	private void testSuccess(int pageNumber, int displayPages) {
		int startIndex = PaginateServiceImpl.getStartIndex(pageNumber, displayPages);
		
		List<String> results = paginateService.paginate(pageNumber,displayPages,pages);
		
		for (String page : results) {
			assertEquals(page, ++startIndex + "");
		}			
	}
}
