package com.usps.api.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.usps.api.annotation.Paginate;
import com.usps.core.model.PaginateRequest;

@Component
public class PaginateValidator implements ConstraintValidator<Paginate, PaginateRequest> {
	public static final String PARAM_NAME_HOLDER_REGEX = "\\{paramName}";
	public static final String PARAM_VALUE_HOLDER_REGEX = "\\{value}";

	private final Logger log = Logger.getLogger(this.getClass());	
	@Value("${error.validation.paginate_request_parameter_null}")
	private String paginateRequestParametersNullMsg;
	@Value("${error.validation.paginate_request_parameter_integer}")
	private String paginateRequestParametersIntegerMsg;
	@Value("${error.validation.paginate_request_parameter_wrong}")
	private String paginateRequestParametersWrongMsg;
	@Value("${error.validation.paginate_view_name_display_pages}")
	private String viewDisplayPages;
	@Value("${error.validation.paginate_view_name_page_number}")
	private String viewPageNumber;
	@Value("${error.validation.paginate_view_name_pages}")
	private String viewPages;
	
	@Override
	public void initialize(Paginate paginate) {
		// nothing
	}

	@Override
	public boolean isValid(PaginateRequest paginateRequest, ConstraintValidatorContext cvc) {
		log.debug("Paginate validating...");

		if (!isInteger(paginateRequest.getDisplayPages(), viewDisplayPages, cvc) ||
				!isInteger(paginateRequest.getPageNumber(), viewPageNumber, cvc)) {
			return false;
		}
		else {
			List<String> pages = paginateRequest.getPages();
			if (pages == null || pages.size() == 0) {
				String errorParam = viewPages;
				String errorMsg = paginateRequestParametersNullMsg.replaceAll(PARAM_NAME_HOLDER_REGEX, errorParam);
				setupError(errorParam, errorMsg, cvc);		
				return false;
			}			
			else {
				int displayPages = Integer.parseInt(paginateRequest.getDisplayPages());
				int pageNumber = Integer.parseInt(paginateRequest.getPageNumber());
				int total = displayPages + pageNumber - 1;
				
				if (total > pages.size()) {
					setupError("wrong setup", paginateRequestParametersWrongMsg, cvc);		
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * 
	 * @param number - String that represents a number, assumes
	 * @return True - string is not a number, False - otherwise
	 */
	boolean isInteger(String number, String errorParam, ConstraintValidatorContext cvc) {
		if (StringUtils.isEmpty(number)) {
			String errorMsg = paginateRequestParametersNullMsg.replaceAll(PARAM_NAME_HOLDER_REGEX, errorParam);
			setupError(errorParam, errorMsg, cvc);
			return false;
		}
		
		try {
			int testInt = Integer.parseInt(number);
			if (testInt <= 0) {
				throw new NumberFormatException();
			}
		} catch (NumberFormatException e) {
			String errorMsg = paginateRequestParametersIntegerMsg.replaceAll(PARAM_NAME_HOLDER_REGEX, errorParam).replaceAll(PARAM_VALUE_HOLDER_REGEX, number);
			setupError(errorParam, errorMsg, cvc);
			return false;
		}
		
		return true;
	}
 	
	private void setupError(String errorParam, String errorMsg, ConstraintValidatorContext cvc) {
		log.error("Error validating " + errorParam);

		// this allows HTTP GET methods to use this validator too, 
		// since it doesn't use the @Valid which auto-creates the cvc variable in the HTTP POST scenario
		if (cvc != null) {
			cvc.disableDefaultConstraintViolation();
			cvc.buildConstraintViolationWithTemplate(errorMsg).addConstraintViolation();					
		}
	}
}