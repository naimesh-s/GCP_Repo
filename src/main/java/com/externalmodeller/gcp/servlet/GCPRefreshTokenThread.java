package com.externalmodeller.gcp.servlet;

import javax.servlet.http.HttpSession;

public class GCPRefreshTokenThread implements Runnable{

	@Override
	public void run() {
		
		int counter = 20000;
		try {
			Thread.sleep(counter);
//			RefreshTokenServlet refreshTokenServlet = new RefreshTokenServlet();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getRefreshToken() {
		
		
		return null;
		
	}
	
}
