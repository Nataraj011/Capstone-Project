package com.prodapt.cmsprojectmain.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class CredentialsExceptions extends BadCredentialsException{

	public CredentialsExceptions(String msg, Throwable cause) {
		super(msg, cause);
		
	}

	public CredentialsExceptions(String msg) {
		super(msg);
		
	}
	
	

}
