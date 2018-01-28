package com.retail.ordering.service.user;

import java.util.Base64;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.retail.ordering.dao.user.UserDao;
import com.retail.ordering.entity.UserDetails;
import com.retail.ordering.exception.AuthenticationFailureException;

public class UserService {

	private static final Logger LOGGER = Logger.getLogger(UserService.class);

	@Inject
	private UserDao userDao;

	public void authenticateUser(final String authorizationHeader) {

		final String[] credentials = validateHeader(authorizationHeader);

		final UserDetails user = userDao.getUser(credentials[0], credentials[1]);
		if (user == null) {
			LOGGER.error("User not found.");
			throw new AuthenticationFailureException("Invalid credentials. Please try again");
		}
	}

	private String[] validateHeader(final String authorizationHeader) {

		if (authorizationHeader == null || authorizationHeader.trim().isEmpty()) {
			LOGGER.error("Empty authentication header received.");
			throw new AuthenticationFailureException("Authentication failure.");
		}

		final String[] authTokens = authorizationHeader.split("Basic ");
		if (authTokens.length != 2) {
			LOGGER.error("Invalid authentication header received.");
			throw new AuthenticationFailureException("Authentication failure.");
		}

		final String base64Decoded = new String(Base64.getDecoder().decode(authTokens[1]));
		final String[] credentials = base64Decoded.split(":");
		if (credentials.length != 2) {
			LOGGER.error("Credentials not encoded properly.");
			throw new AuthenticationFailureException("Authentication failure.");
		}

		return credentials;
	}
}
