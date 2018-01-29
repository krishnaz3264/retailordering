package com.retail.ordering.client.brick;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.retail.ordering.OrderDetails;
import com.retail.ordering.entity.UserDetails;
import com.retail.ordering.service.orderdetails.OrderDetailsService;

@Path("bricks")
public class BrickResource {
	
	@Inject
	private OrderDetailsService brickService;
	
	@Context
	private SecurityContext securityContext;

	@GET
	@Path("order/{quantity}")
	@Produces(MediaType.APPLICATION_JSON)
	public OrderDetails order(@PathParam("quantity") final int quantity) {
		return brickService.order((UserDetails) securityContext.getUserPrincipal(), quantity);
	}
}
