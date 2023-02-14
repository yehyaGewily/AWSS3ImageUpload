package com.Gewily.AWSIMAGE.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
@Service
public class FileStoreServiceImpl implements FileStoreService {
	
	@Autowired
	private  AmazonS3 s3;
	private final Logger log=LoggerFactory.getLogger(getClass());

	@Override
	public void save(String path, String fileName, Optional<Map<String, String>> metadata, InputStream inputStream) {
		
		ObjectMetadata objectMetaData=new ObjectMetadata();
		metadata.ifPresent(map ->{ 
			if(!map.isEmpty()) {
				map.forEach((key,value)->objectMetaData.addUserMetadata(key, value));
			}
		});
		
		try {
			
			log.info(">>>>>>[Uploading img to S3]");
			log.info(">>>>>file path: "+path);
			log.info(">>>>>file name: "+fileName);
			long startTime= System.currentTimeMillis()/1000;
			s3.putObject(path,fileName,inputStream,objectMetaData);
			long endTime= System.currentTimeMillis()/1000;
			long diff=endTime-startTime;
			log.info(">>>>>>end Uploading in "+diff+" seconds");
		}
		catch(AmazonServiceException ex) {
			
			throw new IllegalStateException("failed to store file",ex);
		}
			
		

	}

	@Override
	public byte[] download(String imgPath,String key) throws IOException {
		
		
try {
			
	
			log.info(">>>>>>[Downloading img to S3]");
			log.info(">>>>>image path: "+imgPath);
			long startTime= System.currentTimeMillis()/1000;
			S3Object img=s3.getObject(imgPath, key);
			long endTime= System.currentTimeMillis()/1000;
			long diff=endTime-startTime;
			log.info(">>>>>>end downloading in "+diff+" seconds");
			S3ObjectInputStream inputStream=img.getObjectContent();
			log.info(">>>>>[img]  "+IOUtils.toByteArray(inputStream));
			return IOUtils.toByteArray(inputStream);
		}
		catch(AmazonServiceException ex) {
			
			throw new IllegalStateException("failed to store file",ex);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("error converting the file",e);

		}
}

}
