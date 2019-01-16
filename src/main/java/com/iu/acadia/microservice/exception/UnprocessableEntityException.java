package com.iu.acadia.microservice.exception;

/**
 * Created by Vinutha on 7/12/18.
 */
public class UnprocessableEntityException extends RuntimeException {

	private static final long serialVersionUID = 2704593895329765748L;

	public UnprocessableEntityException(String message) { super(message); }
    
}
