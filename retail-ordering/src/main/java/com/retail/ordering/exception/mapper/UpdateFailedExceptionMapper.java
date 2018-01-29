package com.retail.ordering.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.retail.ordering.exception.UpdateFailedException;

@Provider
public class UpdateFailedExceptionMapper implements ExceptionMapper<UpdateFailedException>{

	public Response toResponse(final UpdateFailedException e) {
		return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
	}

}
