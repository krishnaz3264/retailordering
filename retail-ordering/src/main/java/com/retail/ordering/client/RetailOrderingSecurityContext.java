package com.retail.ordering.client;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import com.retail.ordering.entity.UserDetails;

public class RetailOrderingSecurityContext implements SecurityContext {
	
	private UserDetails userDetails;

	public RetailOrderingSecurityContext(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	@Override
	public Principal getUserPrincipal() {
		return userDetails;
	}

	@Override
	public boolean isUserInRole(String role) {
		return true;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public String getAuthenticationScheme() {
		return "BASIC";
	}

}
