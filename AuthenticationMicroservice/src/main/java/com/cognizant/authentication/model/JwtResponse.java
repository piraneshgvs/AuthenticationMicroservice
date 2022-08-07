package com.cognizant.authentication.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String contactNumber;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getJwttoken() {
		return jwttoken;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	@Override
	public String toString() {
		return "JwtResponse [jwttoken=" + jwttoken + ", contactNumber=" + contactNumber + "]";
	}
	public JwtResponse(String jwttoken, String contactNumber) {
		super();
		this.jwttoken = jwttoken;
		this.contactNumber = contactNumber;
	}

}