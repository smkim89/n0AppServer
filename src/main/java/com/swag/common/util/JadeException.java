package com.swag.common.util;

public class JadeException extends Exception {

	public JadeException(String code, String message) {
		super(message);
		this.code = code;
	}

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
