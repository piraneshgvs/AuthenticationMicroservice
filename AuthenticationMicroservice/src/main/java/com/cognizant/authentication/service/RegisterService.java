package com.cognizant.authentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.authentication.entity.UserInformation;
import com.cognizant.authentication.repository.UserRepository;

@Service
public class RegisterService {
	
	@Autowired
	UserRepository userRepository;
	
	public String createUser(UserInformation userInformation) {
		if(checkForUser(userInformation.getUserName())) {
			return "User Name already exsist";
		}
		else if(checkForEmail(userInformation.getEmailId())) {
			return "Email Id is already exsist";
		}
		else if(checkForContactNumber(userInformation.getContactNumber())) {
			return "Contact Number already exist";
		}
		userRepository.save(userInformation);
		return "Success";
		
	}
	
	private boolean checkForUser(String userName) {
		if(userRepository.findByUserName(userName)==null) {
			return false;
		}
		return true;
		
	}
 
	private boolean checkForEmail(String emailId) {
		if(userRepository.findByEmailId(emailId)==null) {
			return false;
		}
		return true;
		
	}
	
	private boolean checkForContactNumber(String contactNumber) {
		if(userRepository.findByContactNumber(contactNumber)==null) {
			return false;
		}
		return true;
		
	}


}
