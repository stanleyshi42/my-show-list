package com.cognixia.jump.exceptions;

public class LoginException extends Exception{
	
	private static final boolean loggedIn = true;
	
	public LoginException() {
		super("Invalid login attempt");
	}
}
