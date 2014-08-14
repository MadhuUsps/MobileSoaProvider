package com.usps.core.model;

import java.io.Serializable;
import java.util.List;

public class PaginateResponse implements Serializable {
	private static final long serialVersionUID = 2478776269411997254L;
	private PaginateResponseStatus status;
	private List<String> results;
	
	public PaginateResponse() {
		super();
	}

	public PaginateResponse(PaginateResponseStatus status, List<String> results) {
		super();
		this.status = status;
		this.results = results;
	}

	public PaginateResponseStatus getStatus() {
		return status;
	}

	public void setStatus(PaginateResponseStatus status) {
		this.status = status;
	}

	public List<String> getResults() {
		return results;
	}

	public void setResults(List<String> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "PaginateResponse [status=" + status + ", results=" + results
				+ "]";
	}
}