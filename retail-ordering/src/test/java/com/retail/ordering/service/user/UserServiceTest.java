package com.retail.ordering.service.user;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.Base64;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.retail.ordering.dao.user.UserDao;
import com.retail.ordering.entity.UserDetails;
import com.retail.ordering.exception.AuthenticationFailureException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ UserService.class, Logger.class })
public class UserServiceTest {

	private static final String AUTHORIZATION_HEADER = "Basic " + Base64.getEncoder().encodeToString("username:password".getBytes());

	@Mock
	private UserDao daoMock;

	@InjectMocks
	private UserService userService = new UserService();

	private static Logger loggerMock;

	@BeforeClass
	public static void before() {
		mockStatic(Logger.class);
		loggerMock = mock(Logger.class);
		when(Logger.getLogger(UserService.class)).thenReturn(loggerMock);
	}

	@Test
	public void authenticateUserTest() {
		when(daoMock.getUser("username", "password")).thenReturn(new UserDetails());
		userService.authenticateUser(AUTHORIZATION_HEADER);
	}

	@Test(expected = AuthenticationFailureException.class)
	public void authenticateUserWithInvalidCredentialsTest() {
		when(daoMock.getUser("username", "password")).thenReturn(null);
		userService.authenticateUser(AUTHORIZATION_HEADER);
		verify(loggerMock).error("User not found.");
	}

	@Test(expected = AuthenticationFailureException.class)
	public void authenticateUserWithEmptyAuthenticationHeaderTest() {
		userService.authenticateUser(null);
		verify(loggerMock, times(1)).error("Empty authentication header received.");
	}

	@Test(expected = AuthenticationFailureException.class)
	public void authenticateUserWithInvalidAuthenticationHeaderTest() {
		userService.authenticateUser("invalid authorization header");
		verify(loggerMock, times(1)).error("Invalid authentication header received.");
	}

	@Test(expected = AuthenticationFailureException.class)
	public void authenticateUserWithInvalidEncodingTest() {
		userService.authenticateUser("Basic " + Base64.getEncoder().encodeToString("usernamepassword".getBytes()));
		verify(loggerMock, times(1)).error("Credentials not encoded properly.");
	}
}
