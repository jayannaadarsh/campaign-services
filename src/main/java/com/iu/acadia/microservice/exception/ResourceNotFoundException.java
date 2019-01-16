package com.iu.acadia.microservice.exception;

/**
 * Created by Vinutha on 7/12/18.
 */
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -9199526495740773143L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
    
}
