package com.nrifintech.medico.request;

import javax.validation.constraints.Pattern;

public class UserLoginRequest {
	/* USERNAME Pattern Description
	 * 
	 * username is 8-20 characters long
	 * no _ or . at the beginning
	 * no __ or _. or ._ or .. inside
	 * allowed characters - a-z, A-Z, 0-9, ., _
	 * no _ or . at the end
	 */
	//@Pattern(regexp = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", message = "Username must be 8-20 characters long with allowed characters - a-z, A-Z, 0-9, ., _")
	
	private String username;
	
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*
	 * PASSWORD Pattern - Same as username pattern
	 */
//	@Pattern(regexp = "^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", message = "Password must be 8-20 characters long with allowed characters - a-z, A-Z, 0-9, ., _")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
