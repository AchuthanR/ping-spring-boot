package com.incedo.ping.auth_service.exception;

public class ResourceAlreadyExistsException extends Exception {

	private static final long serialVersionUID = -8700714653208306026L;
	
	public ResourceAlreadyExistsException(String message) {
		super(message);
	}

}
