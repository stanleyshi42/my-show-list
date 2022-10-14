package com.cognixia.jump.exceptions;

public class LoginException extends Exception{
	
	public LoginException() {
		super("Invalid username/password");
	}
	
	public LoginException(String str) {
		super(str + " was not valid. Please try registering again.");
	}
	
	public LoginException(int i) {
		super("User is already registered");
	}
}
