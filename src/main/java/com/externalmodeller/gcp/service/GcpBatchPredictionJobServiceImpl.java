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

import com.externalmodeller.gcp.model.GcpBatchPredictionJobRequest;
import com.externalmodeller.gcp.model.GcpBatchPredictionJobResponse;
import com.externalmodeller.gcp.model.TokenModel;
import com.externalmodeller.gcp.util.GCPConstants;
import com.externalmodeller.gcp.util.RestUtils;

@Service
public class GcpBatchPredictionJobServiceImpl {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public  GcpBatchPredictionJobResponse createGcpBatchPredictionJob(GcpBatchPredictionJobRequest batchPredictionJobRequest, String projectId, String region) {
		
		StringBuilder theUrl = new StringBuilder(GCPConstants.GCP_HOST_URI).append("projects/").append(projectId).append("/locations/").append(region).append("/batchPredictionJobs");
		System.out.println("URI >> "+ theUrl.toString());
		
		HttpEntity<GcpBatchPredictionJobRequest> entity = populateGcpApiRequestHeadersEntity(batchPredictionJobRequest);		
			
		ResponseEntity<GcpBatchPredictionJobResponse> response = restTemplate.exchange(theUrl.toString(), HttpMethod.POST, entity, GcpBatchPredictionJobResponse.class);
		System.out.println(theUrl + " --> Result - status ("+ response.getStatusCode() + ") has body: " + response.hasBody());
		GcpBatchPredictionJobResponse batchPredictionJob = (GcpBatchPredictionJobResponse) response.getBody();
		return batchPredictionJob;
	}
	
	private HttpEntity<GcpBatchPredictionJobRequest> populateGcpApiRequestHeadersEntity(GcpBatchPredictionJobRequest batchPredictionJobRequest) {
		TokenModel token = getToken();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set(GCPConstants.AUTHORIZATION, "Bearer "+token.getAccess_token());
		HttpEntity<GcpBatchPredictionJobRequest> entity = new HttpEntity<>(batchPredictionJobRequest, headers);
		return entity;
	}

	private TokenModel getToken() {
		Map<String, TokenModel> tokencache = RestUtils.getTokenCache();
		TokenModel tokenModel = tokencache.get(GCPConstants.TOKEN);
		return tokenModel;
	}
}
