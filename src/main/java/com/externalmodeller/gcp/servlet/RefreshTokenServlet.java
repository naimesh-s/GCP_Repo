package com.externalmodeller.gcp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.externalmodeller.gcp.exception.SessionException;
import com.externalmodeller.gcp.model.TokenModel;
import com.google.common.collect.ImmutableMap;

public class RefreshTokenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final String clientId = "439378483548-l995nd0fgpqvvl95br9tf67nl0l10a45.apps.googleusercontent.com";
	private final String clientSecret = "PQ3ys3p5IMusablkG0UKZ72X";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = (HttpSession) req.getSession(false);
		
		try {
			if (session != null) {
				TokenModel tokenModel = (TokenModel) session.getAttribute("tokenModelObject");

				StringBuilder authorizeUri = new StringBuilder("https://oauth2.googleapis.com/token")
						.append("?client_secret=" + clientSecret).append("&grant_type=refresh_token")
						.append("&refresh_token=" + tokenModel.getRefresh_token()).append("&client_id=" + clientId);

				// get the access token by post to Google
				String body = post(authorizeUri.toString(),
						ImmutableMap.<String, String>builder().put("token_uri", "https://oauth2.googleapis.com/token")
								.put("refresh_token", tokenModel.getRefresh_token()).build());
				
				JSONObject jsonObject = null;

				// get the access token from json and request info from Google
				try {
					jsonObject = (JSONObject) new JSONParser().parse(body);
				} catch (ParseException e) {
					throw new RuntimeException("Unable to parse json " + body);
				}
				
				if( jsonObject != null ) {
					tokenModel.setAccess_token((String) jsonObject.get("access_token"));
					tokenModel.setExpires_in((Long) jsonObject.get("expires_in"));
					tokenModel.setScope((String) jsonObject.get("scope"));
					tokenModel.setToken_type((String) jsonObject.get("token_type"));
				}
				
				req.getSession().setAttribute("tokenModelObject", tokenModel);
//				resp.sendRedirect("/home.jsp");
//				
				 PrintWriter out = resp.getWriter(); out.println ("<html>\r\n" +
				 "    <head>\r\n" +
				 "        <title>GCP Campaign External Modeller Application</title>\r\n" +
				 "    </head>\r\n" + "    <body>\r\n" + "    <h1>Token Info</h1>\r\n" +
				 "    <div>Access Token : "+ tokenModel.getAccess_token()+ "</div>\r\n" +
				 "    <div>Expires_In : " + tokenModel.getExpires_in()+ "</div>\r\n" +
				 "    <div>Token Type : " + tokenModel.getToken_type()+ "</div>\r\n" +
				 "    <form action=\"/refreshTokenServlet\" method=\"get\">\r\n" +
				 "    	<input type=\"submit\" name=\"Refresh Token\" value=\"Refresh Token\" />\r\n"
				 + "	</form>\r\n" + "    </body>\r\n" + "</html>");
				
				
//				GCPRefreshTokenThread gcpRefreshTokenThread = new GCPRefreshTokenThread();
//				Thread th = new Thread(gcpRefreshTokenThread);
//				th.start();
//				doGet(req, resp);
				
//				ApiPoller poller = new ApiPoller();
//		       ScheduledExecutorService schedular = Executors.newScheduledThreadPool(1);
		       //specify the time duration
//		       schedular.scheduleAtFixedRate(gcpRefreshTokenThread, 0,1, TimeUnit.MINUTES);
			} else {
				throw new SessionException("Existing Session Not Found");
			}
		} catch (SessionException sessionException) {
			sessionException.getStackTrace();
			System.out.println(sessionException.getMessage());
		}

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
