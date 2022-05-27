package com.externalmodeller.gcp.listner;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GcpServletContextListner implements ServletContextListener {

	Map<String, String> demomap;
	
	@Override
	public void contextInitialized(ServletContextEvent e) {
		System.out.println("MyServletContextListener Context Initialized");
		demomap = new HashMap<String, String>();
		demomap.put("Name", "Naimesh");
	}

	@Override
	public void contextDestroyed(ServletContextEvent e) {
		System.out.println("MyServletContextListener Context Destroyed");
	}

}
