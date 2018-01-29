package com.retail.ordering.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.retail.ordering.dao.DaoBase;
import com.retail.ordering.entity.UserDetails;
import com.retail.ordering.exception.DatabaseException;

public class UserDao extends DaoBase {

	private final static String SALT = "!ad#@123sdfskmho&(jhgjh)";

	private static final Logger LOGGER = Logger.getLogger(UserDao.class);

	public UserDetails getUser(final String username, final String password) {

		try (final Connection conn = getConnection()) {
			
			final PreparedStatement stmt = conn
					.prepareStatement("select username, shipping_address from user_details where username = ? and password = ?");
			stmt.setString(1, username);
			stmt.setString(2, DigestUtils.md5Hex(SALT + password));
			final ResultSet resultSet = stmt.executeQuery();
			if (resultSet.next()) {
				final UserDetails user = new UserDetails();
				user.setUsername(resultSet.getString("username"));
				return user;
			}
		} catch (final SQLException e) {
			throw new DatabaseException("Server internal error. Please try again later or contact system administrator", e);
		}

		return null;
	}

}
