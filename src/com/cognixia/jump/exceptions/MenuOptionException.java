package com.cognixia.jump.exceptions;

public class MenuOptionException extends Exception {
	
	public MenuOptionException() {
		super("Option is not a valid integer");
	}
}
