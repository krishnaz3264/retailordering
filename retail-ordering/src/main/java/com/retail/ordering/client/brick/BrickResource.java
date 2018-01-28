package com.retail.ordering.client.brick;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.retail.ordering.Order;
import com.retail.ordering.service.brick.BrickService;

@Path("bricks")
public class BrickResource {
	
	@Inject
	private BrickService brickService;

	@GET
	@Path("order/{quantity}")
	@Produces(MediaType.APPLICATION_JSON)
	public Order order(@PathParam("quantity") final int quantity) {
		return new Order();
	}
}
