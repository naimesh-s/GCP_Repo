package com.externalmodeller.gcp.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.externalmodeller.gcp.model.TokenModel;

@Component
@Order(1)
public class GcpFilter implements Filter {
	
//	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String CODE = "code";
	Map<String, String> paramMap;
	
	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("########## Initiating GCP filter ##########");
    }
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse httpResponse = (HttpServletResponse) res;

		System.out.println("MyFilter doFilter() is invoked.");
		Enumeration<String> params = req.getParameterNames();
		TokenModel tokenModel = new TokenModel();
		
		MutableHttpServletRequest request = new MutableHttpServletRequest(httpRequest);
		
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			System.out.println("Parameter:" + param + "\tValue:" + req.getParameter(param));
			if (param.equals("code")) {
//				tokenModel.setAccess_token(req.getParameter(param));
//				paramMap.put(CODE, req.getParameter(param));
			}
		}
		

//		if( jsonObject != null ) {
//			tokenModel.setAccess_token((String) jsonObject.get("access_token"));
//			tokenModel.setExpires_in((Long) jsonObject.get("expires_in"));
//			tokenModel.setRefresh_token((String) jsonObject.get("refresh_token"));
//			tokenModel.setScope((String) jsonObject.get("scope"));
//			tokenModel.setToken_type((String) jsonObject.get("token_type"));
//		}

		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {

	}

}
