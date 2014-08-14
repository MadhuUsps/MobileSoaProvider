package com.usps.api.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.usps.core.model.PaginateRequest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/resources/META-INF/spring/applicationContext.xml"})
public class PaginateValidatorTest {
	@Autowired
	private PaginateValidator paginateValidator;
	private ConstraintValidatorContext cvc;
	
	@Before
	public void beforeAllMethods() {
		cvc = new ConstraintValidatorContextImpl(new ArrayList<String>(), PathImpl.createRootPath(),null);
		assertNotNull(paginateValidator);		
	}
	
	@Test
	public void testIsInteger() {
		assertFalse(paginateValidator.isInteger(null, "", cvc));
		assertFalse(paginateValidator.isInteger("", "", cvc));
		assertFalse(paginateValidator.isInteger("a", "", cvc));
		assertFalse(paginateValidator.isInteger("-1", "", cvc));
		assertFalse(paginateValidator.isInteger("0", "", cvc));
		assertFalse(paginateValidator.isInteger("1.5", "", cvc));
		assertTrue(paginateValidator.isInteger("1", "", cvc));
	}
	
	@Test
	public void testIsValid() {
		// all null
		assertFalse(paginateValidator.isValid(new PaginateRequest(null,null,null), cvc));
		// 2 nulls
		assertFalse(paginateValidator.isValid(new PaginateRequest("",null,null), cvc));
		assertFalse(paginateValidator.isValid(new PaginateRequest(null,"",null), cvc));
		assertFalse(paginateValidator.isValid(new PaginateRequest(null,null,new ArrayList<String>()), cvc));
		// 1 null
		assertFalse(paginateValidator.isValid(new PaginateRequest(null,"",new ArrayList<String>()), cvc));
		assertFalse(paginateValidator.isValid(new PaginateRequest("",null,new ArrayList<String>()), cvc));
		assertFalse(paginateValidator.isValid(new PaginateRequest("","",null), cvc));
		// 1 null
		assertFalse(paginateValidator.isValid(new PaginateRequest(null,"",new ArrayList<String>()), cvc));
		assertFalse(paginateValidator.isValid(new PaginateRequest("",null,new ArrayList<String>()), cvc));
		assertFalse(paginateValidator.isValid(new PaginateRequest("","",null), cvc));
				
		assertFalse(paginateValidator.isValid(new PaginateRequest("1","1",new ArrayList<String>()), cvc));
		
		assertTrue(paginateValidator.isValid(new PaginateRequest("1","1",new ArrayList<String>(Arrays.asList("1"))), cvc));
		assertTrue(paginateValidator.isValid(new PaginateRequest("1","1",new ArrayList<String>(Arrays.asList("1","2"))), cvc));
	}	
}