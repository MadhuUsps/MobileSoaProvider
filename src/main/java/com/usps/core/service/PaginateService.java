package com.usps.core.service;

import java.util.List;

import com.usps.core.model.PaginateRequest;

public interface PaginateService {
	List<String> paginate(PaginateRequest request);
	List<String> paginate(int pageNumber, int displayPages, List<String> pages);
}