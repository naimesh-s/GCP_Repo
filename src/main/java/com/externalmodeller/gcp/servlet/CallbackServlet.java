package com.externalmodeller.gcp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.externalmodeller.gcp.config.SessionData;
import com.externalmodeller.gcp.model.TokenModel;
import com.google.common.collect.ImmutableMap;

public class CallbackServlet extends HttpServlet {
	
	@Autowired
	private SessionData sessionData;

	private static final long serialVersionUID = 1L;
	
	private final String clientId = "439378483548-l995nd0fgpqvvl95br9tf67nl0l10a45.apps.googleusercontent.com";
	private final String clientSecret = "PQ3ys3p5IMusablkG0UKZ72X";

	@Override
	public void init(ServletConfig config) throws ServletException {
	    super.init(config);
	    SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
	      config.getServletContext());
	  }
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// google redirects with
		// http://localhost:8080/callback?state=this_can_be_anything_to_help_correlate_the_response%3Dlike_session_id&code=4/ygE-kCdJ_pgwb1mKZq3uaTEWLUBd.slJWq1jM9mcUEnp6UAPFm0F2NQjrgwI&authuser=0&prompt=consent&session_state=a3d1eb134189705e9acf2f573325e6f30dd30ee4..d62c

		// if the user denied access, we get back an error, ex
		// error=access_denied&state=session%3Dpotatoes

		if (req.getParameter("error") != null) {
			resp.getWriter().println(req.getParameter("error"));
			return;
		}

		// google returns a code that can be exchanged for a access token
		String code = req.getParameter("code");
		System.out.println("CODE >>> " + code);
		
		PrintWriter out = resp.getWriter();
		 out.println ("<html>\r\n" + 
				"    <head>\r\n" + 
				"        <title>GCP Campaign External Modeller Application</title>\r\n" + 
				"    </head>\r\n" + 
				"    <body>\r\n" + 
				"    <h1>Token Info</h1>\r\n" + 
				"    <div>Code: " + code+"</div>\r\n" +  
				"    </body>\r\n" + 
				"</html>");
		 
//		String redirectUri = "http://localhost:8080/gcpcallback";
//		
//		StringBuilder authorizeUri = new StringBuilder("https://oauth2.googleapis.com/token")
//				.append("?code="+code)
//				.append("&redirect_uri="+redirectUri)
//				.append("&client_id="+clientId)
//				.append("&client_secret="+clientSecret)
//				.append("&scope=")
//				.append("&grant_type=authorization_code");
//		
//		
//		// get the access token by post to Google
//		String body = post(authorizeUri.toString(), ImmutableMap.<String,String>builder()
//				.put("token_uri", "https://oauth2.googleapis.com/token")
//			     .put("code", code)
//			     .build());
//
//		// ex. returns
//		// {
////	       "access_token": "ya29.AHES6ZQS-BsKiPxdU_iKChTsaGCYZGcuqhm_A5bef8ksNoU",
////	       "token_type": "Bearer",
////	       "expires_in": 3600,
////	       "id_token": "eyJhbGciOiJSUzI1NiIsImtpZCI6IjA5ZmE5NmFjZWNkOGQyZWRjZmFiMjk0NDRhOTgyN2UwZmFiODlhYTYifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiZW1haWxfdmVyaWZpZWQiOiJ0cnVlIiwiZW1haWwiOiJhbmRyZXcucmFwcEBnbWFpbC5jb20iLCJhdWQiOiI1MDgxNzA4MjE1MDIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdF9oYXNoIjoieUpVTFp3UjVDX2ZmWmozWkNublJvZyIsInN1YiI6IjExODM4NTYyMDEzNDczMjQzMTYzOSIsImF6cCI6IjUwODE3MDgyMTUwMi5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsImlhdCI6MTM4Mjc0MjAzNSwiZXhwIjoxMzgyNzQ1OTM1fQ.Va3kePMh1FlhT1QBdLGgjuaiI3pM9xv9zWGMA9cbbzdr6Tkdy9E-8kHqrFg7cRiQkKt4OKp3M9H60Acw_H15sV6MiOah4vhJcxt0l4-08-A84inI4rsnFn5hp8b-dJKVyxw1Dj1tocgwnYI03czUV3cVqt9wptG34vTEcV3dsU8",
////	       "refresh_token": "1/Hc1oTSLuw7NMc3qSQMTNqN6MlmgVafc78IZaGhwYS-o"
//		// }
//
//		JSONObject jsonObject = null;
//
//		// get the access token from json and request info from Google
//		try {
//			jsonObject = (JSONObject) new JSONParser().parse(body);
//		} catch (ParseException e) {
//			throw new RuntimeException("Unable to parse json " + body);
//		}
//
//		// google tokens expire after an hour, but since we requested offline access we
//		// can get a new token without user involvement via the refresh token
//		
//		TokenModel tokenModel = new TokenModel();
//		if( jsonObject != null ) {
//			tokenModel.setAccess_token((String) jsonObject.get("access_token"));
//			tokenModel.setExpires_in((Long) jsonObject.get("expires_in"));
//			tokenModel.setRefresh_token((String) jsonObject.get("refresh_token"));
//			tokenModel.setScope((String) jsonObject.get("scope"));
//			tokenModel.setToken_type((String) jsonObject.get("token_type"));
//		}
//		System.out.println("accessToken >>> "+ tokenModel.getAccess_token() + " Expires_in >>> " + tokenModel.getExpires_in()
//		+ " refresh_token >>> " + tokenModel.getRefresh_token());
//
//		// you may want to store the token model in session
//		HttpSession session = req.getSession();
//		session.setAttribute("tokenModelObject", tokenModel);
//		
//		Map<HttpSession, TokenModel> sessionDataMap = new HashMap<HttpSession, TokenModel>();
//		sessionData.setSessionDataMap(sessionDataMap);
////		sessionData.setToken(tokenModel);
//		
//		PrintWriter out = resp.getWriter();
//		 out.println ("<html>\r\n" + 
//				"    <head>\r\n" + 
//				"        <title>GCP Campaign External Modeller Application</title>\r\n" + 
//				"    </head>\r\n" + 
//				"    <body>\r\n" + 
//				"    <h1>Token Info</h1>\r\n" + 
//				"    <div>Access Token : "+ tokenModel.getAccess_token()+ "</div>\r\n" + 
//				"    <div>Expires_In : " + tokenModel.getExpires_in()+ "</div>\r\n" + 
//				"    <div>Refresh Token: " + tokenModel.getRefresh_token()+"</div>\r\n" + 
//				"    <form action=\"/refreshTokenServlet\" method=\"get\">\r\n" + 
//		 		"    	<input type=\"submit\" name=\"Refresh Token\" value=\"Refresh Token\" />\r\n" + 
//		 		"	</form>\r\n" + 
//				"    </body>\r\n" + 
//				"</html>");
		
//		GCPRefreshTokenThread gcpRefreshTokenThread = new GCPRefreshTokenThread();
//		Thread t1 = new Thread(gcpRefreshTokenThread);
//		t1.setName("RefreshThread");
//		t1.start();
//		try {
//			t1.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		resp.sendRedirect("/refreshTokenServlet");
//		resp.sendRedirect(req.getContextPath() + "/home.jsp");

		//		resp.getWriter().println(tokenModel.getAccess_token());
		
//		RequestDispatcher rs = req.getRequestDispatcher("/index.html"); 
//        rs.forward(req, resp);
		// get some info about the user with the access token
		
//		StringBuilder uriRequest = new StringBuilder("https://us-central1-ml.googleapis.com/v1/projects/firm-link-320906/models/Naimesh_Model16/versions/GCPModel16_V1:predict?access_token=")
//				.append(accessToken);
		
//		String payloadBody = "{\"instances\":[[0.00632,18,2.31,0,0.538,6.575,65.2,4.09,1,296,15.3,396.9,4.98],[0.02731,0,7.07,0,0.469,6.421,78.9,4.9671,2,242,17.8,396.9,9.14],[0.02729,0,7.07,0,0.469,7.185,61.1,4.9671,2,242,17.8,392.83,4.03],[0.03237,0,2.18,0,0.458,6.998,45.8,6.0622,3,222,18.7,394.63,2.94],[0.06905,0,2.18,0,0.458,7.147,54.2,6.0622,3,222,18.7,396.9,5.33],[0.02985,0,2.18,0,0.458,6.43,58.7,6.0622,3,222,18.7,394.12,5.21],[0.08829,12.5,7.87,0,0.524,6.012,66.6,5.5605,5,311,15.2,395.6,12.43],[0.14455,12.5,7.87,0,0.524,6.172,96.1,5.9505,5,311,15.2,396.9,19.15],[0.21124,12.5,7.87,0,0.524,5.631,100,6.0821,5,311,15.2,386.63,29.93],[0.17004,12.5,7.87,0,0.524,6.004,85.9,6.5921,5,311,15.2,386.71,17.1],[0.22489,12.5,7.87,0,0.52,6.377,94.3,6.3467,5,311,15.2,392.52,20.45]]}";
		
//		String json = postRestTemplate(uriRequest.toString(), payloadBody);
//		resp.getWriter().println(json);
//		String json = post(new StringBuilder("https://us-central1-ml.googleapis.com/v1/projects/firm-link-320906/models/Naimesh_Model16/versions/GCPModel16_V1:predict?access_token=")
//				.append(accessToken).toString(),ImmutableMap.<String,String>builder().put("payload",payloadBody).build());
//
//		// now we could store the email address in session
//
//		// return the json of the user's basic info
//		resp.getWriter().println(json);
	}

	// makes a GET request to url and returns body as a string
	public String get(String url) throws ClientProtocolException, IOException {
		return execute(new HttpGet(url));
	}

	// makes a POST request to url with form parameters and returns body as a string
	public String post(String url, Map<String, String> formParameters) throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(url);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		for (String key : formParameters.keySet()) {
			nvps.add(new BasicNameValuePair(key, formParameters.get(key)));
		}

		request.setEntity(new UrlEncodedFormEntity(nvps));

		return execute(request);
	}

	// makes request and checks response code for 200
	private String execute(HttpRequestBase request) throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(request);

		HttpEntity entity = response.getEntity();
		String body = EntityUtils.toString(entity);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException(
					"Expected 200 but got " + response.getStatusLine().getStatusCode() + ", with body " + body);
		}

		return body;
	}
}
