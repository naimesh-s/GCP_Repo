package com.externalmodeller.gcp.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

import com.externalmodeller.gcp.model.TokenModel;
import com.google.common.collect.ImmutableMap;

public class RestUtils {
	
	private static Map<String, TokenModel> tokenCache = new ConcurrentHashMap<String, TokenModel>();
	
	public static Map<String, TokenModel> getTokenCache() {
		return tokenCache;
	}

	private final static String clientId = "439378483548-l995nd0fgpqvvl95br9tf67nl0l10a45.apps.googleusercontent.com";
	private final static String clientSecret = "PQ3ys3p5IMusablkG0UKZ72X";
	
	public static TokenModel getGCPAccessTokenDetails(String code) throws ClientProtocolException, IOException {
		String redirectUri = "http://localhost:8080/gcpcallback";
		
		StringBuilder authorizeUri = new StringBuilder("https://www.googleapis.com/oauth2/v4/token")
				.append("?code="+code)
				.append("&redirect_uri="+redirectUri)
				.append("&client_id="+clientId)
				.append("&client_secret="+clientSecret)
				.append("&scope=")
				.append("&grant_type=authorization_code");
		
		
		// get the access token by post to Google
		String body = post(authorizeUri.toString(), ImmutableMap.<String,String>builder()
				.put("token_uri", "https://www.googleapis.com/oauth2/v4/token")
			     .put("code", code)
			     .build());

		JSONObject jsonObject = null;

		// get the access token from json and request info from Google
		try {
			jsonObject = (JSONObject) new JSONParser().parse(body);
		} catch (ParseException e) {
			throw new RuntimeException("Unable to parse json " + body);
		}
		
		TokenModel tokenModel = new TokenModel();
		if( jsonObject != null ) {
			tokenModel.setAccess_token((String) jsonObject.get("access_token"));
			tokenModel.setExpires_in((Long) jsonObject.get("expires_in"));
			tokenModel.setRefresh_token((String) jsonObject.get("refresh_token"));
			tokenModel.setScope((String) jsonObject.get("scope"));
			tokenModel.setToken_type((String) jsonObject.get("token_type"));
		}
		
		//Putting the TokenModel into token cache for subsequent GCP API request
		tokenCache.put(GCPConstants.TOKEN, tokenModel);
		
		return tokenModel;
	}
	
	// makes a POST request to url with form parameters and returns body as a string
	public static String post(String url, Map<String, String> formParameters) throws ClientProtocolException, IOException {
		HttpPost request = new HttpPost(url);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();

		for (String key : formParameters.keySet()) {
			nvps.add(new BasicNameValuePair(key, formParameters.get(key)));
		}

		request.setEntity(new UrlEncodedFormEntity(nvps));

		return execute(request);
	}
	
	// makes request and checks response code for 200
	private static String execute(HttpRequestBase request) throws ClientProtocolException, IOException {
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
