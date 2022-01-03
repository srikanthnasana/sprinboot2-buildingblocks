package com.stacksimply.restservices.filter;

import java.io.IOException;
/**
 * This Filter add at same position of UsernamePasswordAuthenticationFilter and this filter
 * just print log info authentication in progress
 * @author LENOVO
 *
 */
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AuthoritiesLoggingAtFilter implements Filter {
	private final Logger LOG = Logger.getLogger(AuthoritiesLoggingAtFilter.class.getName());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOG.info("Authentication Validation is in progress");
		chain.doFilter(request, response);
	}
}
