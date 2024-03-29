package com.cognizant.authentication.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.authentication.config.JwtTokenUtil;
import com.cognizant.authentication.entity.UserInformation;
import com.cognizant.authentication.model.JwtRequest;
import com.cognizant.authentication.model.JwtResponse;
import com.cognizant.authentication.model.Messenger;
import com.cognizant.authentication.repository.UserRepository;
import com.cognizant.authentication.service.JwtUserDetailsService;
import com.cognizant.authentication.service.RegisterService;
import com.cognizant.authenticationo.exception.UnauthorizedException;

import org.slf4j.Logger;


@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private RegisterService registerService;
	
	@Autowired
	private Messenger messenger;
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@ExceptionHandler(UnauthorizedException.class)
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)throws  Exception {

			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		UserInformation userInformation = userRepository.findByUserName(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		logger.info("Token : "+token);

		return new ResponseEntity<>(new JwtResponse(token,userInformation.getContactNumber()),HttpStatus.OK);
	}
	
	@PostMapping(value="/register")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserInformation userInformation) throws Exception{
		messenger.setMessage(registerService.createUser(userInformation));
		return new ResponseEntity<>(messenger,HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	
	@GetMapping("/validate")
	public ResponseEntity<String> validateToken(@RequestHeader(name="Authorization", required = true) String token){
		String jwtToken = token.substring(8,token.length()-1);
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		if(username!=null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			
			try{
				if(jwtTokenUtil.validateToken(jwtToken, userDetails)) {
					logger.info("Valid token");
				return new ResponseEntity<String>("Valid Token", HttpStatus.OK);
				}
			}
			catch(Exception e) {
				return  new ResponseEntity<String>("Not a valid token",HttpStatus.UNAUTHORIZED);
			}
		}
		return  new ResponseEntity<String>("Not a valid token",HttpStatus.UNAUTHORIZED);
	}

}
