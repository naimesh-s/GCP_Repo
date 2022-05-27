package com.externalmodeller.gcp.model;

import java.io.Serializable;
import java.util.List;

public class GcsResource implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<String> uris;

	public List<String> getUris() {
		return uris;
	}

	public void setUris(List<String> uris) {
		this.uris = uris;
	}
	
}
