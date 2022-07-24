package com.cognizant.authentication.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cognizant.authentication.entity.UserInformation;
import com.cognizant.authentication.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserInformation userInformation = userRepository.findByUserName(username);
		BCryptPasswordEncoder bcrypto = new BCryptPasswordEncoder();
		
		if (username!=null) {
			System.out.println(userInformation.getPassword());
			return new User(userInformation.getUserName(), bcrypto.encode(userInformation.getPassword()) ,new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}