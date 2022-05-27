package com.externalmodeller.gcp.config;

import javax.servlet.ServletContextListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.externalmodeller.gcp.listner.GcpServletContextListner;
import com.externalmodeller.gcp.servlet.CallbackServlet;
import com.externalmodeller.gcp.servlet.RefreshTokenServlet;
import com.externalmodeller.gcp.servlet.SigninServlet;

@SpringBootApplication
@PropertySource("classpath:application.properties")
@RestController(value = "/campaign")
public class GcpAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GcpAppApplication.class, args);
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	// Register Servlet
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean signinServletBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new SigninServlet(), "/gcpsignin");
		return bean;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean callbackServletBean() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new CallbackServlet(), "/gcpcallback");
		return bean;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public ServletRegistrationBean refreshTokenServlet() {
		ServletRegistrationBean bean = new ServletRegistrationBean(new RefreshTokenServlet(), "/refreshTokenServlet");
		return bean;
	}

	// Register ServletContextListener
	@Bean
	public ServletListenerRegistrationBean<ServletContextListener> listenerRegistrationBean() {
		ServletListenerRegistrationBean<ServletContextListener> bean = new ServletListenerRegistrationBean<>();
		bean.setListener(new GcpServletContextListner());
		return bean;

	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	  return new PropertySourcesPlaceholderConfigurer();
	}
	
	@RequestMapping(value="/", method= {RequestMethod.GET})
	public String getInit() {
		return "Welcome to GCP App !!";
	}
	

}
