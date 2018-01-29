package com.retail.ordering.service.orderdetails;

import javax.inject.Inject;

import com.retail.ordering.OrderDetails;
import com.retail.ordering.dao.orderdetails.OrderDetailsDao;
import com.retail.ordering.entity.UserDetails;
import com.retail.ordering.exception.ValidationException;

public class OrderDetailsService {
	
	@Inject
	private OrderDetailsDao orderDetailsDao;

	public OrderDetails order(final UserDetails userDetails, final int quantity) {
		
		if(quantity < 1) {
			throw new ValidationException("Invalid quantity.");
		}
		
		return orderDetailsDao.order(userDetails, quantity);
	}

}
