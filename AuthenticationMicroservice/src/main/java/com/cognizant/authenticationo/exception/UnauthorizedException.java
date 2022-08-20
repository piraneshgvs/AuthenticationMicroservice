package com.cognizant.authenticationo.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedException extends RuntimeException {
    
	
	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String msg) {
        super(msg);
    }
}