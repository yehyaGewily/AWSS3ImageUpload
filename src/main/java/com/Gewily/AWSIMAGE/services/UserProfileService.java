package com.Gewily.AWSIMAGE.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.Gewily.AWSIMAGE.model.UserProfile;

public interface UserProfileService {
	
	public UserProfile getUserById(Long id);
	
	public ResponseEntity<?> uploadUserProfileImage(Long id,MultipartFile file);

	public ResponseEntity<?> downloadUserProfileImage(Long id);

}
