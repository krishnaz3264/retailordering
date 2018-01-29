package com.retail.ordering.client.brick;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import com.retail.ordering.OrderDetails;
import com.retail.ordering.entity.UserDetails;
import com.retail.ordering.service.orderdetails.OrderDetailsService;

@Path("bricks")
public class BrickResource {
	
	@Inject
	private OrderDetailsService orderDetailsService;
	
	@Context
	private SecurityContext securityContext;

	@POST
	@Path("order")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response newOrder(@FormParam("quantity") final int quantity) {
		OrderDetails orderDetails = orderDetailsService.newOrder((UserDetails) securityContext.getUserPrincipal(), quantity);
		return Response.status(Status.CREATED).entity(orderDetails).build();
	}
	
	@POST
	@Path("order/details")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderDetails> getOrderDetails(final List<String> referenceNumbers) {
		return orderDetailsService.getOrderDetails((UserDetails) securityContext.getUserPrincipal(), referenceNumbers);
	}
	
	@POST
	@Path("order/update/quantity")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateQuantity(final OrderDetails orderDetails) {
		orderDetails.setUserDetails((UserDetails) securityContext.getUserPrincipal());
		return Response.status(Status.OK).entity(orderDetailsService.updateQuantity(orderDetails)).build();
	}
	
	@POST
	@Path("order/update/status")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateStatus(final OrderDetails orderDetails) {
		orderDetails.setUserDetails((UserDetails) securityContext.getUserPrincipal());
		return Response.status(Status.OK).entity(orderDetailsService.updateStatus(orderDetails)).build();
	}
}
