package com.if5.todolist.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.if5.todolist.models.entities.Utilisateur;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.if5.todolist.config.SecurityConstants.APPLICATION_JSON_VALUE;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	
 private AuthenticationManager authenticationManager;
 
 
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
	super();
	this.authenticationManager = authenticationManager;
}

	@Override
    	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
    			throws AuthenticationException {
    	
    	Utilisateur utilisateur=null;
		try {
			utilisateur = new ObjectMapper().readValue(request.getInputStream(), Utilisateur.class);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		     System.out.println("-------------------------------------------------------------");
		     System.out.println("--------UserName: "+utilisateur.getUserName());
		     System.out.println("--------Password: "+utilisateur.getPassword());
		     
    		return authenticationManager.authenticate(
    				new UsernamePasswordAuthenticationToken(utilisateur.getUserName(), utilisateur.getPassword()));
    	}
    
    @Override
    	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
    			Authentication authResult) throws IOException, ServletException {
    		
    	User springUser=(User) authResult.getPrincipal();
    	
    	 System.out.println("--------SpringUserName: "+springUser.getUsername());
	     System.out.println("--------SpringPassword: "+springUser.getPassword());
    	
    	Algorithm  algorithm = Algorithm.HMAC256(SecurityConstants.SECRET);
    	   String access_token=JWT.create()
    			   .withSubject(springUser.getUsername())
    			   .withExpiresAt(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
    			   .withIssuer(request.getRequestURL().toString())
    			   .withClaim("roles", springUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
    			   .sign(algorithm);
    	   
    	   String refresh_token=JWT.create()
    			   .withSubject(springUser.getUsername())
    			   .withExpiresAt(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME+10 * 60 * 1000))
    			   .withIssuer(request.getRequestURL().toString())
    			   .withClaim("roles", springUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
    			   .sign(algorithm);
    			
    	   
    	 //  response.setHeader("access_token",access_token );
    	 //  response.setHeader("refresh_token",refresh_token );
    	   
    	   Map<String, String> tokens = new HashMap<>();
    	   tokens.put("access_token", access_token);
    	   tokens.put("refresh_token",refresh_token);
    	   response.setContentType(APPLICATION_JSON_VALUE);
    	   new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    	    
    			   
    	}

}
