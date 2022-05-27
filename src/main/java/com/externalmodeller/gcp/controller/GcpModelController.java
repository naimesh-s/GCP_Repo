package com.externalmodeller.gcp.controller;

import javax.websocket.server.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.externalmodeller.gcp.model.GcpVertexAIModels;
import com.externalmodeller.gcp.model.Model;
import com.externalmodeller.gcp.service.GcpModelService;

@RestController
public class GcpModelController {

	@Autowired
	private GcpModelService gcpModelService;
	
	@SuppressWarnings("null")
	@RequestMapping(value = "/getModels", method = { RequestMethod.GET })
	public ResponseEntity<GcpVertexAIModels> getModels(@QueryParam(value = "projectid") String projectid,
			@QueryParam(value = "region") String region) {
		
		GcpVertexAIModels gcpVertexAIModels = null;
		if (projectid != null || !projectid.isEmpty() && region != null || !region.isEmpty()) {
			gcpVertexAIModels = gcpModelService.getGcpModels(projectid, region);
		} else {
			System.out.println("Project Id or Region is unavailable.. and it is required to get models!!");
		}
		return new ResponseEntity<GcpVertexAIModels>(gcpVertexAIModels, HttpStatus.OK);
	}
	
	@SuppressWarnings("null")
	@RequestMapping(value = "/getModel", method = { RequestMethod.GET })
	public ResponseEntity<Model> getModel(@PathParam(value = "name") String name) {
		Model model = null;
		if (name != null || !name.isEmpty()) {
			model = gcpModelService.getGcpModel(name);
		} else {
			System.out.println("Project Id or Region is unavailable.. and it is required to get models!!");
		}
		return new ResponseEntity<Model>(model, HttpStatus.OK);
	}
	
}
