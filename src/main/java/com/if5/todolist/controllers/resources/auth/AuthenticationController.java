package com.if5.todolist.controllers.resources.auth;

import com.if5.todolist.models.dtos.auth.AuthenticationRequest;
import com.if5.todolist.models.dtos.auth.AuthenticationResponse;
import com.if5.todolist.services.implementations.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
		
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword())
				);
		
		final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(request.getUsername());
		return ResponseEntity.ok(AuthenticationResponse.builder()
				.accessToken("un_token_bidon").build());
	}

}
