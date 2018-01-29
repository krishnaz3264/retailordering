package com.retail.ordering.client;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import com.retail.ordering.entity.UserDetails;
import com.retail.ordering.service.user.UserService;

@Provider
public class RequestFilter implements ContainerRequestFilter {
	
	@Inject
	private UserService userService;
	
	public void filter(final ContainerRequestContext requestContext) throws IOException {
		final UserDetails userDetails = userService.authenticateUser(requestContext.getHeaderString("Authorization"));
		requestContext.setSecurityContext(new RetailOrderingSecurityContext(userDetails));
	}

}
