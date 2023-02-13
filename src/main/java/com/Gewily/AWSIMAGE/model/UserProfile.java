package com.Gewily.AWSIMAGE.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="user_profile")
public class UserProfile {
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long userId;
	@Column(name="user_name")
	private String userName;
	@Column(name="img")
	private String img;
	
	
	public UserProfile(long userId, String userName, String img) {
		
		this.userId = userId;
		this.userName = userName;
		this.img = img;
	}
	public UserProfile(){
		
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(img, userId, userName);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfile other = (UserProfile) obj;
		return Objects.equals(img, other.img) && Objects.equals(userId, other.userId)
				&& Objects.equals(userName, other.userName);
	}
	

}
