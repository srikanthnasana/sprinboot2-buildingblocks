package com.stacksimply.restservices.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * In this filter to check after successfully logging userdetails are store in the  "SecurityContextHolder" or not.If details
 * are stored we are logging that details
 * This Filter add after UsernamePasswordAuthenticationFilter in security filter chain.
 * @author LENOVO
 *
 */

public class AuthoritiesLogingAfterFilter implements Filter {

	private final Logger LOG = Logger.getLogger(AuthoritiesLogingAfterFilter.class.getName());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null != authentication) {
			LOG.info("User " + authentication.getName() + " is successfully authenticated and " + "has the authorities "
					+ authentication.getAuthorities().toString());
		}

		chain.doFilter(request, response);
	}

}
