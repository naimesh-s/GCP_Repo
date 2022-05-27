package com.externalmodeller.gcp.exception;

public class SessionException extends Exception{

	private static final long serialVersionUID = 1L;
		
	private String message;
	
	public SessionException(String message) {
		this.message = message;
	}
	
	public String toString() {
	      return ("Session Exception Occured : " + message);
	}
}
