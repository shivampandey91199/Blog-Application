package com.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.services.FileService;

@Service
public class FileServiceimpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws Exception {

		//file name abc.png
		String name=file.getOriginalFilename();
		
		//random name  generated file
		String randomId=UUID.randomUUID().toString();
		
		String fileName=randomId.concat(name.substring(name.lastIndexOf(".")));
		
		//fullpath
		String filepath= path + File.separator + fileName;
		
		//create folder if not created
		File f=new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		//files copy
		Files.copy(file.getInputStream(), Paths.get(filepath));

		return fileName;
	}

	@Override
	public InputStream getResource(String path, String filename) throws FileNotFoundException {

		String fullpath=path+ File.separator+filename;
		InputStream is=new FileInputStream(fullpath);
		//db logic to return input
		return is;
	}

}
