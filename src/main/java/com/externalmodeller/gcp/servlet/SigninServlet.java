package com.externalmodeller.gcp.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

public class SigninServlet extends HttpServlet {
	
	@Value("${spring.cloud.gcp.redirectUri}")
	private String redirectUri;
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("redirectUri in Signin servlet >> "+redirectUri);
		// redirect to google for authorization
		StringBuilder oauthUrl = new StringBuilder().append("https://accounts.google.com/o/oauth2/v2/auth")
				.append("?redirect_uri=http://localhost:8080/gcpcallback")
				.append("&prompt=consent") 
				.append("&response_type=code")
				.append("&client_id=439378483548-l995nd0fgpqvvl95br9tf67nl0l10a45.apps.googleusercontent.com")
				.append("&scope=https://www.googleapis.com/auth/cloud-platform") 
				.append("&access_type=offline");

		resp.sendRedirect(oauthUrl.toString());
	}

}
