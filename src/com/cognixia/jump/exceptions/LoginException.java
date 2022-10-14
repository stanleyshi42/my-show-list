package com.cognixia.jump.exceptions;

public class LoginException extends Exception{
	
	public LoginException() {
		super("Invalid username/password");
	}
	
	public LoginException(String str) {
		super("Invalid " + str + ". Please try registering again.");
	}
	
	public LoginException(int i) {
		super("That username is already registered");
	}
}
