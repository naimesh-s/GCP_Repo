package com.externalmodeller.gcp.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.api.gax.longrunning.OperationFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.automl.v1beta1.BatchPredictInputConfig;
import com.google.cloud.automl.v1beta1.BatchPredictOutputConfig;
import com.google.cloud.automl.v1beta1.BatchPredictRequest;
import com.google.cloud.automl.v1beta1.BatchPredictResult;
import com.google.cloud.automl.v1beta1.GcsDestination;
import com.google.cloud.automl.v1beta1.GcsSource;
import com.google.cloud.automl.v1beta1.ModelName;
import com.google.cloud.automl.v1beta1.OperationMetadata;
import com.google.cloud.automl.v1beta1.PredictionServiceClient;
import com.google.cloud.automl.v1beta1.PredictionServiceSettings;

public class ScoreModelPredictionService {

	public static BatchPredictResult batchPredict(String projectId, String modelId, String inputUri, String outputUri)
			throws IOException, ExecutionException, InterruptedException {
		// Initialize client that will be used to send requests. This client only needs
		// to be created
		// once, and can be reused for multiple requests. After completing all of your
		// requests, call
		// the "close" method on the client to safely clean up any remaining background
		// resources.
		GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("C:\\Users\\naimesh.shah\\Downloads\\GCPApp\\src\\main\\resources\\firm-link-320906-cdc6ec7e7941.json"));
	 
		PredictionServiceSettings predictionServiceSettings =
				PredictionServiceSettings.newBuilder()
			          .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
			          .build();
		
		BatchPredictResult response = null;
		try (PredictionServiceClient client = PredictionServiceClient.create(predictionServiceSettings)) {
			// Get the full path of the model.
			ModelName name = ModelName.of(projectId, "us-central1", modelId);

			// Configure the source of the file from a GCS bucket
			GcsSource gcsSource = GcsSource.newBuilder().addInputUris(inputUri).build();
			BatchPredictInputConfig inputConfig = BatchPredictInputConfig.newBuilder().setGcsSource(gcsSource).build();

			// Configure where to store the output in a GCS bucket
			GcsDestination gcsDestination = GcsDestination.newBuilder().setOutputUriPrefix(outputUri).build();
			BatchPredictOutputConfig outputConfig = BatchPredictOutputConfig.newBuilder()
					.setGcsDestination(gcsDestination).build();

			// Build the request that will be sent to the API
			BatchPredictRequest request = BatchPredictRequest.newBuilder().setName(name.toString())
					.setInputConfig(inputConfig).setOutputConfig(outputConfig).build();

			// Start an asynchronous request
			OperationFuture<BatchPredictResult, OperationMetadata> future = client.batchPredictAsync(request);
			System.out.println("Waiting for operation to complete...");
			response = future.get();
			System.out.println("Batch Prediction results saved to specified Cloud Storage bucket.");
			System.out.println("BatchPredictResult Response >> "+ response);
		}
		
		return response;
	}
	
	
	
	
	
	
	
	
	
//	public static void createBatchPredictionJobSample(
//		      String project,
//		      String displayName,
//		      String model,
//		      String instancesFormat,
//		      String gcsSourceUri,
//		      String predictionsFormat,
//		      String gcsDestinationOutputUriPrefix)
//		      throws IOException {
//		    JobServiceSettings settings =
//		        JobServiceSettings.newBuilder()
//		            .setEndpoint("us-central1-aiplatform.googleapis.com:443")
//		            .build();
//		    String location = "us-central1";
//
//		    // Initialize client that will be used to send requests. This client only needs to be created
//		    // once, and can be reused for multiple requests. After completing all of your requests, call
//		    // the "close" method on the client to safely clean up any remaining background resources.
//		    try (JobServiceClient client = JobServiceClient.create(settings)) {
//
//		      // Passing in an empty Value object for model parameters
//		      Value modelParameters = ValueConverter.EMPTY_VALUE;
//
//		      GcsSource gcsSource = GcsSource.newBuilder().addUris(gcsSourceUri).build();
//		      BatchPredictionJob.InputConfig inputConfig =
//		          BatchPredictionJob.InputConfig.newBuilder()
//		              .setInstancesFormat(instancesFormat)
//		              .setGcsSource(gcsSource)
//		              .build();
//		      GcsDestination gcsDestination =
//		          GcsDestination.newBuilder().setOutputUriPrefix(gcsDestinationOutputUriPrefix).build();
//		      BatchPredictionJob.OutputConfig outputConfig =
//		          BatchPredictionJob.OutputConfig.newBuilder()
//		              .setPredictionsFormat(predictionsFormat)
//		              .setGcsDestination(gcsDestination)
//		              .build();
//		      MachineSpec machineSpec =
//		          MachineSpec.newBuilder()
//		              .setMachineType("n1-standard-2")
//		              .setAcceleratorType(AcceleratorType.NVIDIA_TESLA_K80)
//		              .setAcceleratorCount(1)
//		              .build();
//		      BatchDedicatedResources dedicatedResources =
//		          BatchDedicatedResources.newBuilder()
//		              .setMachineSpec(machineSpec)
//		              .setStartingReplicaCount(1)
//		              .setMaxReplicaCount(1)
//		              .build();
//		      String modelName = ModelName.of(project, location, model).toString();
//		      BatchPredictionJob batchPredictionJob =
//		          BatchPredictionJob.newBuilder()
//		              .setDisplayName(displayName)
//		              .setModel(modelName)
//		              .setModelParameters(modelParameters)
//		              .setInputConfig(inputConfig)
//		              .setOutputConfig(outputConfig)
//		              .setDedicatedResources(dedicatedResources)
//		              .build();
//		      LocationName parent = LocationName.of(project, location);
//		      BatchPredictionJob response = client.createBatchPredictionJob(parent, batchPredictionJob);
//		      System.out.format("response: %s\n", response);
//		      System.out.format("\tName: %s\n", response.getName());
//		    }
//		  }
//		}
}
