package com.retail.ordering.exception.mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.retail.ordering.exception.DatabaseException;
import com.retail.ordering.exception.OrderNotFoundException;

@Provider
public class OrderNotFoundExceptionMapper implements ExceptionMapper<OrderNotFoundException>{

	public Response toResponse(final OrderNotFoundException e) {
		return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
	}

}
