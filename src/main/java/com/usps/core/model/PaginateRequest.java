package com.usps.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.usps.api.annotation.Paginate;

@Paginate
public class PaginateRequest implements Serializable {
	private static final long serialVersionUID = 4129428057486751637L;
	private String pageNumber;
    private String displayPages;
    private List<String> pages = new ArrayList<String>();

    public PaginateRequest() {
    }
    
	public PaginateRequest(PaginateRequest request) {
		super();
		this.pageNumber = request.getPageNumber();
		this.displayPages = request.getDisplayPages();
		this.pages.addAll(request.getPages());
	}

	public PaginateRequest(String pageNumber, String displayPages,
			List<String> pages) {
		super();
		this.pageNumber = pageNumber;
		this.displayPages = displayPages;
		this.pages = pages;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getDisplayPages() {
		return displayPages;
	}

	public void setDisplayPages(String displayPages) {
		this.displayPages = displayPages;
	}

	public List<String> getPages() {
		return pages;
	}

	public void setPages(List<String> pages) {
		this.pages = pages;
	}

	@Override
	public String toString() {
		return "PaginateRequest [pageNumber=" + pageNumber + ", displayPages="
				+ displayPages + ", pages=" + pages + "]";
	}
}
