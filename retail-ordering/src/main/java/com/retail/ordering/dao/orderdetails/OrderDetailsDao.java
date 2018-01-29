package com.retail.ordering.dao.orderdetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.retail.ordering.OrderDetails;
import com.retail.ordering.dao.DaoBase;
import com.retail.ordering.entity.UserDetails;
import com.retail.ordering.exception.DatabaseException;
import com.retail.ordering.exception.UpdateFailedException;

public class OrderDetailsDao extends DaoBase {

	@Resource(lookup = "jdbc/RetailOrdering")
	private DataSource dataSource;

	public OrderDetails newOrder(final UserDetails userDetails, final int quantity) {

		try (final Connection conn = getConnection()) {

			final OrderDetails orderDetails = new OrderDetails();
			orderDetails.setReferenceNumber(UUID.randomUUID().toString());
			orderDetails.setUserDetails(userDetails);
			orderDetails.setQuantity(quantity);
			orderDetails.setStatus("O");
			orderDetails.setOrderedDateTime(new Timestamp(new Date().getTime()));

			conn.setAutoCommit(false);

			final PreparedStatement stmt = conn.prepareStatement(
					"insert into order_details (reference_number, username, quantity, order_datetime, status) values (?, ?, ?, ?, ?)");
			stmt.setString(1, orderDetails.getReferenceNumber());
			stmt.setString(2, userDetails.getUsername());
			stmt.setInt(3, quantity);
			stmt.setTimestamp(4, orderDetails.getOrderedDateTime());
			stmt.setString(5, orderDetails.getStatus());
			stmt.executeUpdate();
			conn.commit();

			return orderDetails;
		} catch (final SQLException e) {
			throw new DatabaseException("Server internal error. Please try again later or contact system administrator",
					e);
		}
	}

	public List<OrderDetails> getOrderDetails(final UserDetails userDetails, final List<String> referenceNumbers) {

		try (final Connection conn = getConnection()) {

			final PreparedStatement stmt = conn.prepareStatement(
					"select od.reference_number, od.quantity, od.order_datetime, od.dispatched_datetime, od.delivered_datetime, od.status, ud.username, ud.shipping_address "
							+ " from order_details od, user_details ud where od.username = ud.username "
							+ " and od.reference_number in (?) and od.username = ?");
			final StringBuilder builder = new StringBuilder();
			for (final String referenceNumber : referenceNumbers) {
				builder.append(referenceNumber).append(",");
			}
			stmt.setString(1, builder.toString().substring(0, builder.length() - 1));
			stmt.setString(2, userDetails.getUsername());
			final ResultSet resultSet = stmt.executeQuery();
			final List<OrderDetails> orders = new ArrayList<>();
			while (resultSet.next()) {

				final UserDetails _userDetails = new UserDetails();
				_userDetails.setUsername(resultSet.getString(7));
				_userDetails.setShippingAddress(resultSet.getString(8));

				final OrderDetails orderDetails = new OrderDetails();
				orderDetails.setReferenceNumber(resultSet.getString(1));
				orderDetails.setUserDetails(_userDetails);
				orderDetails.setQuantity(resultSet.getInt(2));
				orderDetails.setOrderedDateTime(resultSet.getTimestamp(3));
				orderDetails.setDispatchedDateTime(resultSet.getTimestamp(4));
				orderDetails.setDeliveredDateTime(resultSet.getTimestamp(5));
				orderDetails.setStatus(resultSet.getString(6));

				orders.add(orderDetails);
			}

			return orders;
		} catch (final SQLException e) {
			throw new DatabaseException("Server internal error. Please try again later or contact system administrator",
					e);
		}
	}

	public OrderDetails update(final OrderDetails orderDetails) {

		try (final Connection conn = getConnection()) {
			conn.setAutoCommit(false);
			final PreparedStatement stmt = conn.prepareStatement(
					"update order_details set quantity = ? where username = ? and reference_number = ? and status = 'O'");
			stmt.setInt(1, orderDetails.getQuantity());
			stmt.setString(2, orderDetails.getUserDetails().getUsername());
			stmt.setString(3, orderDetails.getReferenceNumber());
			int count = stmt.executeUpdate();
			if (count == 1) {
				conn.commit();
			} else {
				throw new UpdateFailedException("Cannot update a dispatched order. Please create a new order");
			}
			return orderDetails;
		} catch (final SQLException e) {
			throw new DatabaseException("Server internal error. Please try again later or contact system administrator",
					e);
		}
	}

	public OrderDetails updateStatus(OrderDetails orderDetails) {

		try (final Connection conn = getConnection()) {
		
			conn.setAutoCommit(false);
			final PreparedStatement stmt = conn
					.prepareStatement("update order_details set status = ? where reference_number = ?");
			final String status;
			if (orderDetails.getStatus().trim().equalsIgnoreCase("O")) {
				status = "DI";
			} else {
				status = "DL";
			}
			stmt.setString(1, status);
			stmt.setString(2, orderDetails.getReferenceNumber());
			
			stmt.executeUpdate();
			conn.commit();
			
			orderDetails.setStatus(status);
			return orderDetails;
		} catch (final SQLException e) {
			throw new DatabaseException("Server internal error. Please try again later or contact system administrator",
					e);
		}
	}

}
