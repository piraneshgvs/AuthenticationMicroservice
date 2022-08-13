package com.cognizant.authentication.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.authentication.config.JwtTokenUtil;
import com.cognizant.authentication.entity.UserInformation;
import com.cognizant.authentication.model.JwtRequest;
import com.cognizant.authentication.model.JwtResponse;
import com.cognizant.authentication.repository.UserRepository;
import com.cognizant.authentication.service.JwtUserDetailsService;
import com.cognizant.authenticationo.exception.UnauthorizedException;

import feign.FeignException.Unauthorized;



@CrossOrigin
@RestController
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
	
	@ExceptionHandler(UnauthorizedException.class)
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws  Exception {

			authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		UserInformation userInformation = userRepository.findByUserName(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		System.out.println("Inside authenticate "+token);

		return ResponseEntity.ok(new JwtResponse(token,userInformation.getContactNumber()));
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
		System.out.println("Inside validate token");
		String jwtToken = token.substring(8,token.length()-1);
		//System.out.println(jwtToken);
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
		if(username!=null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			
			try{
				if(jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				System.out.println("Valid token");
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
