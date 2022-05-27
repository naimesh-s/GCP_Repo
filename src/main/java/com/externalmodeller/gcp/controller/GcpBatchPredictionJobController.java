package com.externalmodeller.gcp.controller;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.externalmodeller.gcp.model.GcpBatchPredictionJobRequest;
import com.externalmodeller.gcp.model.GcpBatchPredictionJobResponse;
import com.externalmodeller.gcp.service.GcpBatchPredictionJobServiceImpl;


@RestController
public class GcpBatchPredictionJobController {

	@Autowired
	private GcpBatchPredictionJobServiceImpl gcpBatchPredictionJobService;

	@RequestMapping(value = "/createGcpBatchPredictionJob", method = { RequestMethod.POST })
	public ResponseEntity<GcpBatchPredictionJobResponse> createBatchPredictionJob(
			@RequestBody GcpBatchPredictionJobRequest batchPredictionJobRequest,
			@QueryParam(value = "projectid") String projectid, @QueryParam(value = "region") String region) {
		GcpBatchPredictionJobResponse batchPredictionJobResponse = null;
		if (batchPredictionJobRequest != null) {
			batchPredictionJobResponse = gcpBatchPredictionJobService
					.createGcpBatchPredictionJob(batchPredictionJobRequest, projectid, region);
		}
		return new ResponseEntity<GcpBatchPredictionJobResponse>(batchPredictionJobResponse, HttpStatus.CREATED);
	}
}
