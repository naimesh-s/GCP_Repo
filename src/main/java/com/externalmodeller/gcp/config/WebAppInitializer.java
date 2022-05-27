package com.externalmodeller.gcp.config;
//package com.GCPApp.GCPApp;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//public class WebAppInitializer implements WebApplicationInitializer {
//
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		
//		 AnnotationConfigWebApplicationContext ctx
//         = new AnnotationConfigWebApplicationContext();
//       ctx.register(WebMvcConfigurer.class);
//       ctx.setServletContext(container);
//
//       ServletRegistration.Dynamic servlet = container.addServlet(
//         "dispatcherExample", new DispatcherServlet(ctx));
//       servlet.setLoadOnStartup(1);
//       servlet.addMapping("/");
//    }
//	}
//
//}
