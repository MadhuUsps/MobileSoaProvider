package com.usps.api.rest.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.Valid;

import org.codehaus.jackson.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.usps.api.annotation.Paginate;
import com.usps.api.exception.PaginateMethodArgumentNotValidException;
import com.usps.core.model.PaginateProxyRequest;
import com.usps.core.model.PaginateRequest;
import com.usps.core.model.PaginateResponse;
import com.usps.core.model.PaginateResponseStatus;
import com.usps.core.service.JSONService;
import com.usps.core.service.PaginateService;
import com.usps.domain.Constants;

/**
 * To get json/xml do one of the following in the HTTP Request Headers:
 * 
 * Accept: application/json
 * Accept: application/xml
 * 
 */
@RestController
@RequestMapping(value = Constants.URL_API_PAGINATE)
public class PaginateAPIController extends APIController {
	@Value("${error.validation.paginate_request_parameters_not_valid}")
	private String defaultPaginateRequestParametersErrorMsg;
	@Autowired
	private PaginateService paginateService;
	@Autowired
	private JSONService jsonService;
	@Autowired
	private ConstraintValidator<Paginate, PaginateRequest> paginateValidator;	

//	@RequestMapping(value="/proxy", method = RequestMethod.POST, headers = {"Content-type="+MediaType.APPLICATION_JSON_VALUE})
//	@ResponseStatus(HttpStatus.OK)
//	public PaginateResponse paginateProxy(@Valid @RequestBody PaginateProxyRequest paginateProxyRequest, HttpServletRequest servletRequest) {
//		log.debug("Proxy Paginate POST, paginateProxyRequest = " + paginateProxyRequest);
//		
//		String forwardURL = paginateProxyRequest.getForwardURL();
//		PaginateRequest request = new PaginateRequest(paginateProxyRequest);
//		
//		log.debug("Proxy Paginate POST, forward URL = " + forwardURL + ", request object = " + request);
//		
//		HttpHeaders headers = new HttpHeaders();
//		MediaType.parseMediaType(servletRequest.getHeader("content-type"));
//		// TODO make this generic
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.setAccept(Arrays.<MediaType>asList(MediaType.APPLICATION_JSON));
//		HttpEntity<PaginateRequest> httpRequest = new HttpEntity<PaginateRequest>(request, headers);
//		RestTemplate rt = new RestTemplate();
////		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
////		messageConverters.add(new MappingJackson2HttpMessageConverter());   
////		messageConverters.add(new FormHttpMessageConverter());
////		rt.setMessageConverters(messageConverters);
//		 
//		PaginateResponse response = rt.postForObject(forwardURL, httpRequest, PaginateResponse.class);
//
//		log.debug("Proxy Paginate POST, response = " + response);
//		
//		return response;
//	}
	
	@RequestMapping(method = RequestMethod.GET, produces={"application/json"})
	@ResponseStatus(HttpStatus.OK)	
	public PaginateResponse paginateGet(@RequestParam(value = "json", required = true) String json) throws PaginateMethodArgumentNotValidException {		
		log.debug("Paginate GET, json string...\n" + json + "");
		
		PaginateMethodArgumentNotValidException ex = new PaginateMethodArgumentNotValidException("HTTP GET 'json' parameter is not valid, "
				+ "probably JSON string is not formatted correctly.  "
				+ "Cannot create PaginateRequest object from json string.");
		
		PaginateRequest paginateRequest;	
		try {
			paginateRequest = jsonService.getObject(json, PaginateRequest.class);
			log.debug("Paginate GET, created paginate request object...\n" + paginateRequest + "");
		} catch (JsonProcessingException e) {
			throw ex;
		} catch (IOException e) {
			throw ex;
		}

		if (!paginateValidator.isValid(paginateRequest, null)) {
			throw ex;
		}

		return getPaginateResponse(paginateRequest);
	}
	
	/**
	 * headers = {"Content-type=application/json"} The content received in the
	 * post request are in JSON format.
	 * 
	 * @param paginateRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, headers = {"Content-type="+MediaType.APPLICATION_JSON_VALUE}, produces={"application/json"})
	@ResponseStatus(HttpStatus.OK)
	public PaginateResponse paginatePost(@Valid @RequestBody PaginateRequest paginateRequest) {
		log.debug("Paginate POST, paginateRequest = " + paginateRequest);
		return getPaginateResponse(paginateRequest);
	}
	
	private PaginateResponse getPaginateResponse(PaginateRequest paginateRequest) {
		List<String> results = paginateService.paginate(paginateRequest);		
		return new PaginateResponse(PaginateResponseStatus.OK, results);
	}
	
	/**
	 * 
	 * @param ex
	 * @return
	 */
    @ExceptionHandler(PaginateMethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, Object> handlePaginateMethodArgumentNotValidException(PaginateMethodArgumentNotValidException ex) {
        return handleOtherException(ex);
    }

	@Override
	protected String getDefaultMethodArgumentNotValidExceptionMsg() {
		return defaultPaginateRequestParametersErrorMsg;
	}
}