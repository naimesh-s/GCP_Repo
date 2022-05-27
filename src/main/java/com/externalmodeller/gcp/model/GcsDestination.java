package com.externalmodeller.gcp.model;

import java.io.Serializable;

public class GcsDestination  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String outputUriPrefix;

	public String getOutputUriPrefix() {
		return outputUriPrefix;
	}

	public void setOutputUriPrefix(String outputUriPrefix) {
		this.outputUriPrefix = outputUriPrefix;
	}
	
}
