package com.cognizant.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.authentication.entity.UserInformation;


@Repository
public interface UserRepository extends JpaRepository<UserInformation, Long> {
	
	UserInformation findByUserName(String userName);
	
	UserInformation findByContactNumber(String contactNumber);
	
	UserInformation findByEmailId(String emailId);

}
