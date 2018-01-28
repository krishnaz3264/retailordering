package com.retail.ordering.exception;

import java.sql.SQLException;

public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DatabaseException(String message, SQLException e) {
		super(message, e);
	}

}
