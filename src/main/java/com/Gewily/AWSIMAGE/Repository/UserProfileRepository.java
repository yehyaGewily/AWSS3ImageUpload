package com.Gewily.AWSIMAGE.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Gewily.AWSIMAGE.model.UserProfile;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
