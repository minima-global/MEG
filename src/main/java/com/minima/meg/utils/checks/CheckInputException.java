package com.minima.meg.utils.checks;

public class CheckInputException extends Exception {
	private static final long serialVersionUID = 1L;

	public CheckInputException(String zError) {
		super(zError);
	}
	
	public CheckInputException(Exception zError) {
		super(zError);
	}
}
