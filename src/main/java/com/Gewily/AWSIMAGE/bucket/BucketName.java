package com.Gewily.AWSIMAGE.bucket;

public enum BucketName {
	PROFILE_IMAGE("gw-image-upload");

	private BucketName(String bucketName) {
		this.bucketName=bucketName;
	}
	
	private final String bucketName;

	public String getBucketName() {
		return bucketName;
	}
	
	
}
