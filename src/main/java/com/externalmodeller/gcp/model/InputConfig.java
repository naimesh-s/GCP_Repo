package com.externalmodeller.gcp.model;

import java.io.Serializable;

public class InputConfig implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String instancesFormat;
	private GcsResource gcsSource;
	
	public String getInstancesFormat() {
		return instancesFormat;
	}
	public void setInstancesFormat(String instancesFormat) {
		this.instancesFormat = instancesFormat;
	}
	public GcsResource getGcsSource() {
		return gcsSource;
	}
	public void setGcsSource(GcsResource gcsSource) {
		this.gcsSource = gcsSource;
	}
	
}
