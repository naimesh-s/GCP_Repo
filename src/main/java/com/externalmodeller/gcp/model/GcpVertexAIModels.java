package com.externalmodeller.gcp.model;

import java.io.Serializable;
import java.util.List;

public class GcpVertexAIModels implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Model> models;

	public List<Model> getModels() {
		return models;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}
	
}
