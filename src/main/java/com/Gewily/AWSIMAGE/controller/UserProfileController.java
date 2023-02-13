package com.Gewily.AWSIMAGE.controller;

import java.io.IOException;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Gewily.AWSIMAGE.model.UserProfile;
import com.Gewily.AWSIMAGE.services.UserProfileService;

@RestController
@RequestMapping("/user")
public class UserProfileController {

	@Autowired
	private UserProfileService userProfileService;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		
		try {
			UserProfile user= userProfileService.getUserById(id);
			if(user!=null) {
				return new ResponseEntity<>(user,HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>("User Profile not found",HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Failed to get User Profile",HttpStatus.BAD_REQUEST);

		}
	}
	
	@PostMapping("/img/{id}/upload")
	public ResponseEntity<?> uploadProfileImage(@PathVariable Long id,
			@RequestBody MultipartFile file) throws IOException{
		
		long eligiableSize=1024*1024*30;
		String[]imgType= {"IMAGE/JPEG","IMAGE/PNG","IMAGE/GIF"};
		//check if picture is valid 
		
		if(file.isEmpty()||file.getBytes().length>eligiableSize) {
			return new ResponseEntity<>("Image size can not be empty or greater than 30MB ",HttpStatus.BAD_REQUEST);
		}
		if(!(Arrays.asList(imgType).contains(file.getContentType().toUpperCase()))) {
			return new ResponseEntity<>(file.getContentType().toUpperCase()+"...Image type can only be JPEG ,PNG ,GIF ",HttpStatus.BAD_REQUEST);

		}
		
		try {
			return userProfileService.uploadUserProfileImage(id, file);
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>("Failed to upload pictture",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/img/{id}/download")
	public ResponseEntity<?> uploadProfileImage(@PathVariable Long id) throws IOException{
		
		
		try {
			return userProfileService.downloadUserProfileImage(id);
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>("Failed to upload pictture",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
