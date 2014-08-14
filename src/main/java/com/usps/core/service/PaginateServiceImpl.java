package com.usps.core.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.usps.api.validator.PaginateValidator;
import com.usps.core.model.PaginateRequest;

@Service
public class PaginateServiceImpl implements PaginateService {
    private final Logger log = Logger.getLogger(this.getClass());
	@Value("${error.validation.paginate_request_parameter_integer}")
	private String paginateRequestParametersIntegerMsg;
	@Value("${error.validation.paginate_request_parameter_null}")
	private String paginateRequestParametersNullMsg;
	@Value("${error.validation.paginate_request_parameter_wrong}")
	private String paginateRequestParametersWrongMsg;	
	
	@Override
	public List<String> paginate(PaginateRequest request) throws NumberFormatException {
		log.debug("Paginate service received request => " + request);
		
		int pageNumber = Integer.parseInt(request.getPageNumber());
		int displayPages = Integer.parseInt(request.getDisplayPages());
		
		return paginate(pageNumber,displayPages,request.getPages());
	}
	
	@Override
	public List<String> paginate(int pageNumber, int displayPages, List<String> pages) {
		log.debug("Paginate service requested...");
		
		// start of the pages array (inclusive)
		int startIndex = getStartIndex(pageNumber, displayPages);
		// stop of the pages array (exclusive)
		int stopIndex = startIndex + displayPages;

		Assert.isTrue(pageNumber > 0, getErrorMsg("pageNumber", pageNumber));
		Assert.isTrue(displayPages > 0, getErrorMsg("displayPages", displayPages));
		Assert.isTrue(pages != null && pages.size() > 0, paginateRequestParametersNullMsg.replaceAll(PaginateValidator.PARAM_NAME_HOLDER_REGEX, "pages"));
		Assert.isTrue(stopIndex <= pages.size(), paginateRequestParametersWrongMsg);
		
		return pages.subList(startIndex, stopIndex);
	}
	
	private String getErrorMsg(String paramName, int value) {
		return paginateRequestParametersIntegerMsg.replaceAll(PaginateValidator.PARAM_NAME_HOLDER_REGEX, paramName).replaceAll(PaginateValidator.PARAM_VALUE_HOLDER_REGEX, "" + value);
	}

	static int getStartIndex(int pageNumber, int displayPages) {
		return displayPages * (pageNumber - 1);
	}
}
