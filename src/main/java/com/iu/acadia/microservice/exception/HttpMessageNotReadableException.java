package com.iu.acadia.microservice.exception;

/**
 * Created by Vinutha on 7/12/18.
 */
public class HttpMessageNotReadableException extends RuntimeException {

	private static final long serialVersionUID = -9199526495740773145L;

	public HttpMessageNotReadableException(String message) {
        super(message);
    }
    
}
