package com.kkomiding.davena.common.hash;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class FileManager {

	public static final String FILE_UPLOAD_PATH ="/Users/mikyeong/Documents/davena";
	
	public static String saveFile(String userId, MultipartFile file) {
		
		if(file == null) {
			return null;
		}
		
		String directoryName = "/" + userId + "_" + System.currentTimeMillis();
		String directoryPath = FILE_UPLOAD_PATH + directoryName;
		
		File directory = new File(directoryPath);
		
		if(!directory.mkdir()) {
			return null;
		}
		
		String filePath = directoryPath + "/" + file.getOriginalFilename();
		
		byte[] bytes;
		try {
			bytes = file.getBytes();
			Path path = Paths.get(filePath);
			Files.write(path,  bytes);
		} catch (IOException e) {
			return null;
		}
		
		return "/images" + directoryName + "/" + file.getOriginalFilename();
	}
}
