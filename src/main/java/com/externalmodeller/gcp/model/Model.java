package com.externalmodeller.gcp.model;

import java.io.Serializable;
import java.util.List;

import org.json.simple.JSONObject;

public class Model implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String displayName;
	private PredictSchemata predictSchemata;
	private String metadataSchemaUri;
	private JSONObject metadata;
	private String trainingPipeline;
	private List<String> supportedDeploymentResourcesTypes;
	private List<String> supportedInputStorageFormats;
	private List<String> supportedOutputStorageFormats;
	private String createTime;
	private String updateTime;
	private String etag;
	private Dataset dataset;
	private List<SupportedExportFormats> supportedExportFormats;
	private String objective;
	private ExplanationSpec explanationSpec;
	private String uiState;
	
	public PredictSchemata getPredictSchemata() {
		return predictSchemata;
	}
	public void setPredictSchemata(PredictSchemata predictSchemata) {
		this.predictSchemata = predictSchemata;
	}
	public String getMetadataSchemaUri() {
		return metadataSchemaUri;
	}
	public void setMetadataSchemaUri(String metadataSchemaUri) {
		this.metadataSchemaUri = metadataSchemaUri;
	}
	public JSONObject getMetadata() {
		return metadata;
	}
	public void setMetadata(JSONObject metadata) {
		this.metadata = metadata;
	}
	public List<String> getSupportedDeploymentResourcesTypes() {
		return supportedDeploymentResourcesTypes;
	}
	public void setSupportedDeploymentResourcesTypes(List<String> supportedDeploymentResourcesTypes) {
		this.supportedDeploymentResourcesTypes = supportedDeploymentResourcesTypes;
	}
	public List<SupportedExportFormats> getSupportedExportFormats() {
		return supportedExportFormats;
	}
	public void setSupportedExportFormats(List<SupportedExportFormats> supportedExportFormats) {
		this.supportedExportFormats = supportedExportFormats;
	}
	public String getObjective() {
		return objective;
	}
	public void setObjective(String objective) {
		this.objective = objective;
	}
	public ExplanationSpec getExplanationSpec() {
		return explanationSpec;
	}
	public void setExplanationSpec(ExplanationSpec explanationSpec) {
		this.explanationSpec = explanationSpec;
	}
	public String getUiState() {
		return uiState;
	}
	public void setUiState(String uiState) {
		this.uiState = uiState;
	}
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
	public String getTrainingPipeline() {
		return trainingPipeline;
	}
	public void setTrainingPipeline(String trainingPipeline) {
		this.trainingPipeline = trainingPipeline;
	}
	public List<String> getSupportedInputStorageFormats() {
		return supportedInputStorageFormats;
	}
	public void setSupportedInputStorageFormats(List<String> supportedInputStorageFormats) {
		this.supportedInputStorageFormats = supportedInputStorageFormats;
	}
	public List<String> getSupportedOutputStorageFormats() {
		return supportedOutputStorageFormats;
	}
	public void setSupportedOutputStorageFormats(List<String> supportedOutputStorageFormats) {
		this.supportedOutputStorageFormats = supportedOutputStorageFormats;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getEtag() {
		return etag;
	}
	public void setEtag(String etag) {
		this.etag = etag;
	}
	public Dataset getDataset() {
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
}
