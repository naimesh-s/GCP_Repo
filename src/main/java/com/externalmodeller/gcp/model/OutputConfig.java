package com.externalmodeller.gcp.model;

import java.io.Serializable;

public class OutputConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	private String predictionsFormat;
	private GcsDestination gcsDestination;
	
	public String getPredictionsFormat() {
		return predictionsFormat;
	}
	public void setPredictionsFormat(String predictionsFormat) {
		this.predictionsFormat = predictionsFormat;
	}
	public GcsDestination getGcsDestination() {
		return gcsDestination;
	}
	public void setGcsDestination(GcsDestination gcsDestination) {
		this.gcsDestination = gcsDestination;
	}
	
}
