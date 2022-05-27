package com.externalmodeller.gcp.model;

import org.json.simple.JSONObject;

public class ExplanationSpec {

	private JSONObject parameters;
	private JSONObject metadata;
	
	public JSONObject getParameters() {
		return parameters;
	}
	public void setParameters(JSONObject parameters) {
		this.parameters = parameters;
	}
	public JSONObject getMetadata() {
		return metadata;
	}
	public void setMetadata(JSONObject metadata) {
		this.metadata = metadata;
	}
}
