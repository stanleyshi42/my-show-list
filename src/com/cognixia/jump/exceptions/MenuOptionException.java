package com.cognixia.jump.exceptions;

public class MenuOptionException extends Exception {

	private static final boolean isInt = true;
	
	public MenuOptionException() {
		super("Option is not a valid integer");
	}
	
}
