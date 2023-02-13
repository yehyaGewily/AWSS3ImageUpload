package com.Gewily.AWSIMAGE.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

public interface FileStoreService {
	
	public void save(String path, String fileName,Optional<Map<String,String>>metadata,InputStream inputStream);

	public byte[] download(String imgPath, String key) throws IOException;

}
