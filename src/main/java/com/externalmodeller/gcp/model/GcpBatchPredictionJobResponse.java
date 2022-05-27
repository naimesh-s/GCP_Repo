package com.externalmodeller.gcp.model;

import java.io.Serializable;

public class GcpBatchPredictionJobResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private String displayName;
	private String model;
	private InputConfig inputConfig;
	private OutputConfig outputConfig;
	private DedicatedResources dedicatedResources;
	private ManualBatchTuningParameters manualBatchTuningParameters;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public InputConfig getInputConfig() {
		return inputConfig;
	}
	public void setInputConfig(InputConfig inputConfig) {
		this.inputConfig = inputConfig;
	}
	public OutputConfig getOutputConfig() {
		return outputConfig;
	}
	public void setOutputConfig(OutputConfig outputConfig) {
		this.outputConfig = outputConfig;
	}
	public DedicatedResources getDedicatedResources() {
		return dedicatedResources;
	}
	public void setDedicatedResources(DedicatedResources dedicatedResources) {
		this.dedicatedResources = dedicatedResources;
	}
	public ManualBatchTuningParameters getManualBatchTuningParameters() {
		return manualBatchTuningParameters;
	}
	public void setManualBatchTuningParameters(ManualBatchTuningParameters manualBatchTuningParameters) {
		this.manualBatchTuningParameters = manualBatchTuningParameters;
	}
	
}
