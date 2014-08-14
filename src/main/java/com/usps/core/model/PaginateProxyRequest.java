package com.usps.core.model;

import com.usps.api.annotation.Paginate;

@Paginate
public class PaginateProxyRequest extends PaginateRequest {
	private static final long serialVersionUID = -4162582907464379158L;
	private String forwardURL;
	
    public PaginateProxyRequest() {
    }

	public String getForwardURL() {
		return forwardURL;
	}

	public void setForwardURL(String forwardURL) {
		this.forwardURL = forwardURL;
	}

	@Override
	public String toString() {
		return "PaginateProxyRequest [forwardURL=" + forwardURL
				+ ", toString()=" + super.toString() + "]";
	}
}
