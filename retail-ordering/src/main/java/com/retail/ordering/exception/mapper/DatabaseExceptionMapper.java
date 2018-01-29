package com.retail.ordering.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.retail.ordering.exception.DatabaseException;

@Provider
public class DatabaseExceptionMapper implements ExceptionMapper<DatabaseException>{

	public Response toResponse(final DatabaseException e) {
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
	}

}
