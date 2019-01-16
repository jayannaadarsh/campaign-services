package com.iu.acadia.microservice.exception;

/**
 * Created by Vinutha on 7/12/18.
 */
public class UnAuthorizedException extends RuntimeException {

	private static final long serialVersionUID = 5913734517775631897L;

	public UnAuthorizedException(String message) {
        super(message);
    }
}
