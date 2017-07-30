package com.swag.common.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TmonetExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(TmonetExceptionHandler.class);
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String pageNotFound(Exception e) {
		logger.error("What the 404 : {}" , e);
		return "exception/404";
	}
}
