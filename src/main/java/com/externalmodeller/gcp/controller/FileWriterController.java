//package com.externalmodeller.gcp.controller;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.google.auth.Credentials;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.storage.Blob;
//import com.google.cloud.storage.BlobId;
//import com.google.cloud.storage.BlobInfo;
//import com.google.cloud.storage.Storage;
//import com.google.cloud.storage.StorageOptions;
//
//@RestController
//public class FileWriterController {
//	
//	@Autowired
//	private Storage storage;
//	
//	@Value("${file.storage}")
//	private Resource localFilePath;
//	
//	@Value("${spring.cloud.gcp.credentials.location}")
//	private String credentialsFilePath;
//	
//	@Value("${spring.cloud.gcp.projectid}")
//	private String projectid;
//	
//	@RequestMapping(path={"bucket/{bucketName}/write-file/{fileName}"}, method= {RequestMethod.GET})
//	public String writeFileToBucket(@PathVariable(name="bucketName")String bucketName, 
//									@PathVariable(name="fileName")String fileName) throws Exception {
//		
//		Credentials credentials = null;
//		try {
//			credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsFilePath));
//		}catch (IOException ioException) {
//			System.err.println("IOException occured >> ");
//			ioException.printStackTrace();
//		}
//		storage = StorageOptions.newBuilder().setCredentials(credentials)
//				  .setProjectId(projectid).build().getService();
//		
//		BlobId blobId = BlobId.of(bucketName, fileName);
//		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//		File fileToRead = new File(localFilePath.getFile(), fileName);
//		byte[] data = Files.readAllBytes(Paths.get(fileToRead.toURI()));
//		Blob blob = storage.create(blobInfo, data);
//		
//		String message = "Successfully Created";
//		return message;
//	}
//}
