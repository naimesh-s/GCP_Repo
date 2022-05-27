package com.externalmodeller.gcp.controller;

import java.io.IOException;

import javax.ws.rs.QueryParam;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.externalmodeller.gcp.model.TokenModel;
import com.externalmodeller.gcp.service.GCPAccessTokenService;

@RestController
public class GcpAccessTokenController {

	@Autowired
	private GCPAccessTokenService accessTokenService;
	
	@RequestMapping(value="/generateAccessToken", method = {RequestMethod.GET})
	public ResponseEntity<TokenModel> generateAccessToken(@QueryParam(value="code") String code) throws ClientProtocolException, IOException{
		return new ResponseEntity<TokenModel>(accessTokenService.generateAccessToken(code), HttpStatus.OK);
	}
}
