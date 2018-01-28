package com.retail.ordering.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.retail.ordering.exception.AuthenticationFailureException;

@Provider
public class AuthenticationFailureExceptionMapper implements ExceptionMapper<AuthenticationFailureException>{

	public Response toResponse(final AuthenticationFailureException e) {
		return Response.status(Status.UNAUTHORIZED).entity(e.getMessage()).build();
	}

}
