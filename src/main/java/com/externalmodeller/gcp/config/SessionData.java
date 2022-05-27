package com.externalmodeller.gcp.config;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.externalmodeller.gcp.model.TokenModel;

@Component
@Scope(proxyMode=ScopedProxyMode.TARGET_CLASS, value="session")
public class SessionData {
	
	private Map<HttpSession, TokenModel> sessionDataMap;
//	private TokenModel token;

	public Map<HttpSession, TokenModel> getSessionDataMap() {
		return sessionDataMap;
	}

	public void setSessionDataMap(Map<HttpSession, TokenModel> sessionDataMap) {
		this.sessionDataMap = sessionDataMap;
	}

//	public TokenModel getToken() {
//		return token;
//	}
//
//	public void setToken(TokenModel token) {
//		this.token = token;
//	}
	
	
	
}
