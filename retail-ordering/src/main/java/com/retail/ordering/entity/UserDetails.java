package com.retail.ordering.entity;

import java.security.Principal;

public class UserDetails implements Principal {

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getName() {
		return username;
	}
	
}
