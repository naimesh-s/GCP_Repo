package com.externalmodeller.gcp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.externalmodeller.gcp.util.CsvParser;
import com.externalmodeller.gcp.util.CsvVo;
import com.google.api.gax.paging.Page;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@RestController
@RequestMapping(value = "/campaign")
public class GcpStorageController {

	@Value("${input.file.storage}")
	private Resource localInputFilePath;

	@Value("${output.file.storage}")
	private Resource localOutputFilePath;

	@Value("${spring.cloud.gcp.credentials.location}")
	private String credentialsFilePath;

	@Value("${spring.cloud.gcp.projectid}")
	private String projectid;

	@Value("${spring.cloud.gcp.modelid}")
	private String modelid;

	@Value("${spring.cloud.gcp.clientId}")
	private String clientId;

	@Value("${spring.cloud.gcp.clientSecret}")
	private String clientSecret;

	@Value("${spring.cloud.gcp.authUri}")
	private String auth_uri;

	@Value("${spring.cloud.gcp.redirectUri}")
	private String redirectUri;
	
	@RequestMapping(path = { "bucket/{bucketName}/write-file/{fileName}" }, method = { RequestMethod.GET })
	public String writeFileToBucket(@PathVariable(name = "bucketName") String bucketName,
			@PathVariable(name = "fileName") String fileName) throws Exception {
		
		long startTime = System.currentTimeMillis();
		String message = "";
		Credentials credentials = null;
		try {
			credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsFilePath));
		} catch (IOException ioException) {
			System.err.println("IOException occured >> ");
			ioException.printStackTrace();
		}
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId(projectid).build()
				.getService();

		BlobId blobId = BlobId.of(bucketName, fileName);
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
		File fileToRead = new File(localInputFilePath.getFile(), fileName);
		byte[] data = Files.readAllBytes(Paths.get(fileToRead.toURI()));
		Blob blob = storage.create(blobInfo, data);
		long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println("elapsedTime >> "+ elapsedTime);
		if (blob != null) {
			message = "Successfully Created";
		}
		return message;
	}

	@RequestMapping(path = { "bucket/{bucketName}" }, method = { RequestMethod.GET })
	public String getScorePredictionData(@PathVariable(name = "bucketName") String bucketName) throws Exception {

		String message = "";
		Credentials credentials = null;
		try {
			credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsFilePath));
		} catch (IOException ioException) {
			System.err.println("IOException occured >> ");
			ioException.printStackTrace();
		}
		Storage storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId(projectid).build()
				.getService();

//		String objectName = "prediction-IrisDataset_202181773719-2021_08_17T01_39_36_439Z/prediction.results-00000-of-00020.csv";

		Page<Blob> blobs = storage.list(bucketName);

		for (Blob blob : blobs.iterateAll()) {
			System.out.println(blob.getName());
			Blob blob1 = storage.get(BlobId.of(bucketName, blob.getName()));
			if (blob1 != null) {
				String fileName = blob.getName().split("/")[1];
				File file = new File("C:\\D_Drive\\GCPStorageFiles\\output_score_predictions\\" + fileName);
				boolean fileCreationStatus = file.createNewFile();
				if (fileCreationStatus) {
					blob1.downloadTo(Paths.get("C:\\D_Drive\\GCPStorageFiles\\output_score_predictions\\" + fileName));
				}
			} else {
				message = "Download Unsuccessfull !!";
				return message;
			}
			message = "Downloaded Successfully !!";
			System.out.println("Downloaded object " + blob.getName() + " from bucket name " + bucketName + " to "
					+ localOutputFilePath);
		}

		// Writing all output files into single csv
		generateOutputScoresCSV();

		return message;
	}

	private void generateOutputScoresCSV() throws IOException {
		CsvParser csvparser = new CsvParser();
		final File folder = new File("C:\\D_Drive\\GCPStorageFiles\\output_score_predictions");
		csvparser.listFilesForFolder(folder);
		List<CsvVo> allCsvRecords = new ArrayList<>();
		List<String> csvHeaders;
		Set<String> uniqueHeaders = null;
		for (File fileName : csvparser.getFilenames()) {
			csvHeaders = CsvParser.getHeadersFromACsv(fileName);
			uniqueHeaders = new LinkedHashSet<>(csvHeaders);

			List<CsvVo> csvRecords = CsvParser.getRecodrsFromACsv(fileName, csvHeaders);
			allCsvRecords.addAll(csvRecords);
		}

		CsvParser.writeToCsv(new File("C:\\D_Drive\\GCPStorageFiles\\output_prediction_results.csv"), uniqueHeaders,
				allCsvRecords);
	}

	/*@RequestMapping(path = {
			"inputBucket/{inputBucketName}/input-data/{inputFilePath}/outputBucket/{outputBucketName}/output-result/{outputFilePath}" }, method = {
					RequestMethod.GET })
	public void initiateModelProcessing(@PathVariable(name = "inputBucketName") String inputBucketName,
			@PathVariable(name = "inputFilePath") String inputFilePath,
			@PathVariable(name = "outputBucketName") String outputBucketName,
			@PathVariable(name = "outputFilePath") String outputFilePath) throws Exception {
		System.out.println("Starting processing of score modelling ...");
		String inputUri = "gs://" + inputBucketName + "/" + inputFilePath;
		String outputUri = "gs://" + outputBucketName + "/" + outputFilePath;
		ScoreModelPredictionService.createBatchPredictionJobSample(projectid, "Model16_Job", "Naimesh_Model16", "csv", inputUri, "jsonl", outputUri);
		BatchPredictResult batchPredictResponse = ScoreModelPredictionService.batchPredict(projectid, modelid, inputUri,
				outputUri);

	}*/
}
