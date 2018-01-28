package com.retail.ordering.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.retail.ordering.entity.UserDetails;
import com.retail.ordering.exception.DatabaseException;

public class UserDao {

	private final static String SALT = "!ad#@123sdfskmho&(jhgjh)";

	private static final Logger LOGGER = Logger.getLogger(UserDao.class);

	@Resource(lookup = "jdbc/RetailOrdering")
	private DataSource dataSource;

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

	private Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (final SQLException e) {
			throw new DatabaseException("Server internal error. Please try again later or contact system administrator", e);
		}
	}

}
