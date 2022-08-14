package com.cognizant.authentication.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.stereotype.Component;

@Entity
@Table(name="USER_DETAILS")
public class UserInformation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@NotBlank(message="Name cannot be blank")
	private String name;
	@NotBlank(message="password field cannot be blank")
	private String password;
	@NotBlank(message="userName cannot be blank")
	private String userName;
	@NotBlank(message="Contact Number cannot be blank")
	@Column(length = 10)
	private String contactNumber;
	@NotBlank(message="Email Id cannot be blank")
	@Email(message="Please give the valid email id")
	private String emailId;
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
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@Override
	public String toString() {
		return "UserInformation [id=" + id + ", name=" + name + ", password=" + password + ", userName=" + userName
				+ ", contactNumber=" + contactNumber + ", emailId=" + emailId + "]";
	}
	public UserInformation(Long id, @NotBlank(message = "Name cannot be blank") String name,
			@NotBlank(message = "password field cannot be blank") String password,
			@NotBlank(message = "userName cannot be blank") String userName,
			@NotBlank(message = "Contact Number cannot be blank") String contactNumber,
			@NotBlank(message = "Email Id cannot be blank") @Email String emailId) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.userName = userName;
		this.contactNumber = contactNumber;
		this.emailId = emailId;
	}
	public UserInformation() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	

}
