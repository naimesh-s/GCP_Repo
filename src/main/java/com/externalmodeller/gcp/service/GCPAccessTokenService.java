package com.externalmodeller.gcp.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.externalmodeller.gcp.model.TokenModel;
import com.externalmodeller.gcp.util.RestUtils;

@Service
public class GCPAccessTokenService {
	
	public TokenModel generateAccessToken(String code) throws ClientProtocolException, IOException {
		return RestUtils.getGCPAccessTokenDetails(code);
	}
}
