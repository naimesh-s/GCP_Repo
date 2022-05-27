package com.externalmodeller.gcp.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.externalmodeller.gcp.model.GcpVertexAIModels;
import com.externalmodeller.gcp.model.Model;
import com.externalmodeller.gcp.model.TokenModel;
import com.externalmodeller.gcp.util.GCPConstants;
import com.externalmodeller.gcp.util.RestUtils;

@Service
public class GcpModelService {

	@Autowired
	private RestTemplate restTemplate;
	
	public  GcpVertexAIModels getGcpModels(String projectId, String region) {
		System.out.println(projectId +" --- "+region);
		
		StringBuilder theUrl = new StringBuilder(GCPConstants.GCP_HOST_URI).append("projects/").append(projectId).append("/locations/").append(region).append("/models");
		System.out.println("URI >> "+ theUrl.toString());
		
		HttpEntity<String> entity = populateGcpApiRequestHeaders();		
			
		ResponseEntity<GcpVertexAIModels> response = restTemplate.exchange(theUrl.toString(), HttpMethod.GET, entity, GcpVertexAIModels.class);
		System.out.println(theUrl + " --> Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
		GcpVertexAIModels aiModels = (GcpVertexAIModels) response.getBody();
		return aiModels;
	}
	
	public  Model getGcpModel(String name) {
		System.out.println(name);
		
		StringBuilder theUrl = new StringBuilder(GCPConstants.GCP_HOST_URI).append(name);
		System.out.println("URI >> "+ theUrl.toString());
		
		HttpEntity<String> entity = populateGcpApiRequestHeaders();		
			
		ResponseEntity<Model> response = restTemplate.exchange(theUrl.toString(), HttpMethod.GET, entity, Model.class);
		System.out.println(theUrl + " --> Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
		Model aiModel = (Model) response.getBody();
		return aiModel;
	}
	
	private HttpEntity<String> populateGcpApiRequestHeaders() {
		TokenModel token = getToken();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set(GCPConstants.AUTHORIZATION, "Bearer "+token.getAccess_token());
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		return entity;
	}

	private TokenModel getToken() {
		Map<String, TokenModel> tokencache = RestUtils.getTokenCache();
		TokenModel tokenModel = tokencache.get(GCPConstants.TOKEN);
		return tokenModel;
	}

}
