package com.prodapt.cmsprojectmain.exceptions;

public class FeatureNotFoundException extends Exception {

	public FeatureNotFoundException() {
		super();
		
	}

	public FeatureNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public FeatureNotFoundException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public FeatureNotFoundException(String message) {
		super(message);
		
	}

	public FeatureNotFoundException(Throwable cause) {
		super(cause);
		
	}
	
}
