package com.externalmodeller.gcp.model;

import java.util.List;

public class SupportedExportFormats {
	
	private String id;
	private List<String> exportableContents;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getExportableContents() {
		return exportableContents;
	}
	public void setExportableContents(List<String> exportableContents) {
		this.exportableContents = exportableContents;
	}
	
}
