package com.cognizant.authentication.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.cognizant.authentication.config.JwtTokenUtil;
import com.cognizant.authentication.entity.UserInformation;
import com.cognizant.authentication.model.JwtRequest;
import com.cognizant.authentication.model.JwtResponse;
import com.cognizant.authentication.model.Messenger;
import com.cognizant.authentication.repository.UserRepository;
import com.cognizant.authentication.service.JwtUserDetailsService;
import com.cognizant.authentication.service.RegisterService;

@SpringBootTest
public class AuthenticationControllerTest {
	
	@InjectMocks
	AuthenticationController authenticationController;
	
	@Mock
	UserDetailsService userDetailService;
	
	@Mock
	private JwtTokenUtil jwtUtil;
	
	@Mock
	AuthenticationManager authenticationManager;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	UserInformation userInformation2;
	
	@Mock
    JwtUserDetailsService jwtUserDetailsService;
	
	@Mock
	JwtTokenUtil jwtTokenUtil;
	
	@Mock
	Messenger messenger;
	
	@Mock
	RegisterService registerService;
	
	@Test
	public void createAuthenticationTokenTest() throws Exception {
		JwtRequest authenticationRequest = new JwtRequest("user", "user123");
		UserDetails userInformation = new User(authenticationRequest.getUsername(),authenticationRequest.getPassword(),new ArrayList<>());
		UserInformation userInformation1 = new UserInformation();
		userInformation1.setContactNumber("1478523692");
		when(userDetailService.loadUserByUsername("user")).thenReturn(userInformation);
		when(jwtUtil.generateToken(userInformation)).thenReturn("dummy-token");
		when(authenticationManager.authenticate(null)).thenReturn(null);
		when(userRepository.findByUserName("user")).thenReturn(userInformation1);
		when(userInformation2.getContactNumber()).thenReturn("9632587412");
		ResponseEntity<?> authenticationResponse = authenticationController
				.createAuthenticationToken(authenticationRequest);
		assertEquals(HttpStatus.OK, authenticationResponse.getStatusCode());
		
		
	}
	
	@Test
	public void validateToken() {
		JwtRequest authenticationRequest = new JwtRequest("user", "user123");
		UserDetails userInformation = new User(authenticationRequest.getUsername(),authenticationRequest.getPassword(),new ArrayList<>());
		when(jwtUtil.getUsernameFromToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")).thenReturn("pisakthi");
		when(userDetailService.loadUserByUsername("user")).thenReturn(userInformation);
		when(jwtTokenUtil.validateToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", userInformation)).thenReturn(null);
		ResponseEntity<?> result = authenticationController.validateToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
		assertEquals(HttpStatus.UNAUTHORIZED,result.getStatusCode());
	}
	
	
	@Test
	public void validateTokenSuccess() {
		JwtRequest authenticationRequest = new JwtRequest("user", "user123");
		UserDetails userInformation = new User(authenticationRequest.getUsername(),authenticationRequest.getPassword(),new ArrayList<>());
		when(jwtUtil.getUsernameFromToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")).thenReturn("pisakthi");
		when(userDetailService.loadUserByUsername("user")).thenReturn(userInformation);
		when(jwtTokenUtil.validateToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c", userInformation)).thenReturn(true);
		ResponseEntity<?> result = authenticationController.validateToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
		assertEquals(HttpStatus.UNAUTHORIZED,result.getStatusCode());
	}
	
	@Test
	public void createUser() throws Exception {
		
		UserInformation userInformationTest = new UserInformation();
		when(registerService.createUser(userInformationTest)).thenReturn("success");
		messenger.setMessage("success");
		ResponseEntity<?> result = authenticationController.createUser(userInformationTest);
		assertEquals(HttpStatus.OK,result.getStatusCode());
		
	}

}
