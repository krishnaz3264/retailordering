package com.retail.ordering.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.retail.ordering.exception.ValidationException;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException>{

	public Response toResponse(final ValidationException e) {
		return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
	}

}
