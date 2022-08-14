package com.cognizant.authentication.model;

import org.springframework.stereotype.Service;

@Service
public class Messenger {
	
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
