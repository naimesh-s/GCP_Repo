package com.externalmodeller.gcp.config;

import org.springframework.stereotype.Component;

@Component
public class AuthRequestPayload {
	
	private String gcp_uri;
	private String scope;
	private String redirect_uri;
	private String client_id;
	private String access_type;
	
	public String getAccess_type() {
		return access_type;
	}

	public void setAccess_type(String access_type) {
		this.access_type = access_type;
	}

	public String getGcp_uri() {
		return gcp_uri;
	}

	public void setGcp_uri(String gcp_uri) {
		this.gcp_uri = gcp_uri;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public String getRedirect_uri() {
		return redirect_uri;
	}

	public void setRedirect_uri(String redirect_uri) {
		this.redirect_uri = redirect_uri;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
