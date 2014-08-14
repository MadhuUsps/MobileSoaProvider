package com.usps.api.rest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

public class SimpleCORSFilter implements Filter {
    private final Logger log = Logger.getLogger(this.getClass());
    private String allowOrigin = "*";
    private String allowMethods = "POST, GET, OPTIONS, DELETE";
    private String allowHeaders = "test-header, Content-Type";
    private String maxAgeSeconds = "" + (60 * 60);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String tempAllowOrigin = filterConfig.getInitParameter("allowOrigin");
		String tempAllowMethods = filterConfig.getInitParameter("allowMethods");
		String tempAllowHeaders = filterConfig.getInitParameter("allowHeaders");
		String tempMaxAgeSeconds = filterConfig.getInitParameter("maxAgeSeconds");
		
		log.debug("Initializing SimpleCORSFilter...");
		
		if (!StringUtils.isEmpty(tempAllowOrigin)) {
			allowOrigin = tempAllowOrigin;
			log.debug("Setting allowOrigin = " + allowOrigin);
		}
		
		if (!StringUtils.isEmpty(tempAllowMethods)) {
			allowMethods = tempAllowMethods;
			log.debug("Setting allowMethods = " + allowMethods);
		}
		
		if (!StringUtils.isEmpty(tempAllowHeaders)) {
			allowHeaders = tempAllowHeaders;
			log.debug("Setting allowHeaders = " + allowHeaders);
		}
		
		if (!StringUtils.isEmpty(tempMaxAgeSeconds)) {
			maxAgeSeconds = tempMaxAgeSeconds;
			log.debug("Setting maxAgeSeconds = " + maxAgeSeconds);
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {	
		HttpServletResponse response = (HttpServletResponse) res;
		
		String allowOriginParameter = "Access-Control-Allow-Origin";
		String allowMethodsParameter = "Access-Control-Allow-Methods";
		String allowHeadersParameter = "Access-Control-Allow-Headers";
		String maxAgeSecondsParameter = "Access-Control-Max-Age";
		response.setHeader(allowOriginParameter, allowOrigin);
		response.setHeader(allowMethodsParameter, allowMethods);
		response.setHeader(allowHeadersParameter, allowHeaders);
		response.setHeader(maxAgeSecondsParameter, maxAgeSeconds);

		log.debug("Setup CORS [" + allowOriginParameter + "=" + allowOrigin + "," + allowMethodsParameter + "=" + allowMethods + ","
					+ allowHeadersParameter + "=" + allowHeaders + "," + maxAgeSecondsParameter + "=" + maxAgeSeconds + "]");
		chain.doFilter(req, res);	
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}
