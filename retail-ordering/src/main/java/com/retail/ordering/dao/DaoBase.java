package com.retail.ordering.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.retail.ordering.exception.DatabaseException;

public class DaoBase {

	@Resource(lookup = "jdbc/RetailOrdering")
	private DataSource dataSource;
	
	protected Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (final SQLException e) {
			throw new DatabaseException("Server internal error. Please try again later or contact system administrator", e);
		}
	}
}
