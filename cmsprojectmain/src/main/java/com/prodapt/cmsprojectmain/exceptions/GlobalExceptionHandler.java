package com.prodapt.cmsprojectmain.exceptions;
import javax.management.relation.RoleNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prodapt.cmsprojectmain.security.payload.response.MessageResponse;

 
@RestControllerAdvice
public class GlobalExceptionHandler {
 
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
 
	@ExceptionHandler({ ProductNotFoundException.class, FeatureNotFoundException.class,
			ParameterNotFoundException.class, QuotationNotFoundException.class })
	public ResponseEntity<String> handleNotFoundException(Exception ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
 
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {
		return new ResponseEntity<>("An error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
 
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<MessageResponse> handleBadCredentialsException(BadCredentialsException ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse(ex.getMessage()));
	}
 
	@ExceptionHandler(UsernameAlreadyExistsException.class)
	public ResponseEntity<MessageResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
		return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
	}
 
	@ExceptionHandler(RoleNotFoundException.class)
	public ResponseEntity<MessageResponse> handleRoleNotFoundException(RoleNotFoundException ex) {
		return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
	}
 
}