package com.Gewily.AWSIMAGE.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfig {
	@Autowired
	Environment env;
	
	@Bean
	public AmazonS3 s3() {
		
		AWSCredentials awsCredesntials= new BasicAWSCredentials(env.getProperty("aws.accesskeyId"),env.getProperty("aws.secretAccesskey"));
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCredesntials))
				.withRegion(env.getProperty("aws.region")).build();
		
	}

}
