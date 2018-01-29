package com.retail.ordering.service.orderdetails;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import com.retail.ordering.OrderDetails;
import com.retail.ordering.dao.orderdetails.OrderDetailsDao;
import com.retail.ordering.entity.UserDetails;
import com.retail.ordering.exception.OrderNotFoundException;
import com.retail.ordering.exception.UpdateFailedException;
import com.retail.ordering.exception.ValidationException;

public class OrderDetailsService {
	
	@Inject
	private OrderDetailsDao orderDetailsDao;

	public OrderDetails newOrder(final UserDetails userDetails, final int quantity) {
		
		if(quantity < 1) {
			throw new ValidationException("Invalid quantity.");
		}
		
		return orderDetailsDao.newOrder(userDetails, quantity);
	}

	public List<OrderDetails> getOrderDetails(final UserDetails userDetails, final List<String> referenceNumbers) {
		
		if(referenceNumbers == null || referenceNumbers.isEmpty()) {
			throw new ValidationException("No reference numbers found in the request payload.");
		}
		
		return orderDetailsDao.getOrderDetails(userDetails, referenceNumbers);
	}

	public OrderDetails updateQuantity(final OrderDetails orderDetails) {
		
		validateUpdateQuantity(orderDetails);
		final OrderDetails updatedOrderDetails = orderDetailsDao.updateQuantity(orderDetails);
		if(updatedOrderDetails == null) {
			throw new UpdateFailedException("Cannot update a dispatched/delivered order. Please create a new order");
		}
		return updatedOrderDetails;
	}

	private void validateUpdateQuantity(final OrderDetails orderDetails) {

		if(orderDetails == null) {
			throw new ValidationException("No order details found in the request payload");
		}
		
		if(orderDetails.getReferenceNumber() == null || orderDetails.getReferenceNumber().trim().isEmpty()) {
			throw new ValidationException("Invalid reference number.");
		}
		
		if(orderDetails.getQuantity() < 1) {
			throw new ValidationException("Invalid quantity.");
		}
	}

	public OrderDetails updateStatus(final OrderDetails orderDetails) {
	
		validateUpdateStatus(orderDetails);
		
		final List<OrderDetails> _orderDetails = orderDetailsDao.getOrderDetails(orderDetails.getUserDetails(), Arrays.asList(orderDetails.getReferenceNumber()));
		if(_orderDetails == null || _orderDetails.isEmpty()) {
			throw new OrderNotFoundException("Order not found.");
		}
		
		return orderDetailsDao.updateStatus(_orderDetails.get(0));
	}

	private void validateUpdateStatus(final OrderDetails orderDetails) {
		if(orderDetails == null) {
			throw new ValidationException("No order details found in the request payload");
		}
		
		if(orderDetails.getReferenceNumber() == null || orderDetails.getReferenceNumber().trim().isEmpty()) {
			throw new ValidationException("Invalid reference number.");
		}
	}

}
