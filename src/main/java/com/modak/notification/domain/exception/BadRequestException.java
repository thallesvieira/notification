package com.modak.notification.domain.exception;

import lombok.Getter;

/* Class to use exceptions
 */
public class BadRequestException extends RuntimeException {
	@Getter
	private final String code;
	@Getter
	private final String message;
	
	public BadRequestException(String message) {
		super(message);
		this.message = "";
		this.code = "404";
    }
}
