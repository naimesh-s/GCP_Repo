package com.externalmodeller.gcp.config;

import org.springframework.stereotype.Component;

public class AuthResponse {
	
	private boolean success;
	private String authorize_uri;
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getAuthorize_uri() {
		return authorize_uri;
	}
	public void setAuthorize_uri(String authorize_uri) {
		this.authorize_uri = authorize_uri;
	}
	
	
}
