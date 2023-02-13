package com.Gewily.AWSIMAGE.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Gewily.AWSIMAGE.Repository.UserProfileRepository;
import com.Gewily.AWSIMAGE.bucket.BucketName;
import com.Gewily.AWSIMAGE.model.UserProfile;

import jdk.internal.org.jline.utils.Log;
@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired 
	UserProfileRepository repo;
	@Autowired
	private  FileStoreService fileStoreService;

	@Override
	public UserProfile getUserById(Long id) {
		UserProfile userprofile=(repo.findById(id)).isPresent()?(repo.findById(id)).get():null;
		return userprofile;
	}

	@Override
	public ResponseEntity<?> uploadUserProfileImage(Long id, MultipartFile file) {
		
		//check if user exists
				UserProfile user= getUserById(id);
				if(user==null) {
					return new ResponseEntity<>("User Profile not found wit id: "+id,HttpStatus.NOT_FOUND);
				}
		//extract metadata
				Map<String,String>metadata= new HashMap<String,String>();
				metadata.put("ContentType", file.getContentType());
				metadata.put("FileSize", String.valueOf(file.getSize()));
		
				String path= BucketName.PROFILE_IMAGE.getBucketName()+"/"+user.getUserId();
				String fileName=file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf("."))+"-"+System.currentTimeMillis();
				try {
					fileStoreService.save(path, fileName,Optional.of(metadata) , file.getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
					return new ResponseEntity<>("failed to upload image: ",HttpStatus.INTERNAL_SERVER_ERROR);

				}
				//update user image in database 
				user.setImg(fileName);
				repo.save(user);
				return new ResponseEntity<>("image uploaded successfully",HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> downloadUserProfileImage(Long id) {
		//check if user exists
		UserProfile user= getUserById(id);
		if(user==null) {
			return new ResponseEntity<>("User Profile not found wit id: "+id,HttpStatus.NOT_FOUND);
		}		
		String imgPath= BucketName.PROFILE_IMAGE.getBucketName()+"/"+user.getUserId();
		if(user.getImg()==null||user.getImg()=="") {
			return new ResponseEntity<>("no picture assigned to this user ",HttpStatus.NOT_FOUND);

		}
		try {
			byte[]img=fileStoreService.download(imgPath,user.getImg());
			System.out.println(img);
			return new ResponseEntity<>(img,HttpStatus.OK);
		}catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>("failed to download image: ",HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
	}

}
