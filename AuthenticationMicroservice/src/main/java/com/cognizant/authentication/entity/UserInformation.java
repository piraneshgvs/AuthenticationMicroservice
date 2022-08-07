package com.cognizant.authentication.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="USER_DETAILS")
@Component
public class UserInformation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private String name;
	private String password;
	private String userName;
	private String contactNumber;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	@Override
	public String toString() {
		return "UserInformation [id=" + id + ", name=" + name + ", password=" + password + ", userName=" + userName
				+ ", contactNumber=" + contactNumber + "]";
	}
	public UserInformation(Long id, String name, String password, String userName, String contactNumber) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.userName = userName;
		this.contactNumber = contactNumber;
	}
	public UserInformation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	

}
