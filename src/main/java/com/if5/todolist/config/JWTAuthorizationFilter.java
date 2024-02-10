package com.if5.todolist.config;

import static com.if5.todolist.config.SecurityConstants.APPLICATION_JSON_VALUE;
import static com.if5.todolist.config.SecurityConstants.SECRET;
import static com.if5.todolist.config.SecurityConstants.TOKEN_PREFIX;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.if5.todolist.config.JWTAuthorizationFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	if(request.getServletPath().equals("/login")) {
		
		filterChain.doFilter(request, response);
		
	}else {
		String authorizationHeader = request.getHeader(AUTHORIZATION); 
		   if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
			  
			   try {

				   String token = authorizationHeader.substring(TOKEN_PREFIX.length() );
				   Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes());
				   JWTVerifier verifier = JWT.require(algorithm).build();
				   DecodedJWT decodedJWT = verifier.verify(token); 
				   String username = decodedJWT.getSubject();
				   String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
				   Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
				   stream(roles).forEach(role -> {
					   authorities.add(new SimpleGrantedAuthority(role));
				   });
				   
				   UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null,authorities);
				   SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				   filterChain.doFilter(request, response);
				
			} catch (Exception ex) {
				
				log.info("Error logging in: {}", ex.getMessage());
				response.setHeader("error", ex.getMessage());
				response.setStatus(HttpStatus.FORBIDDEN.value());
				//response.sendError(HttpStatus.FORBIDDEN.value());
				Map<String, String> error = new HashMap<>();
		    	   error.put("error_message ", ex.getMessage());
		    	   response.setContentType(APPLICATION_JSON_VALUE);
		    	   new ObjectMapper().writeValue(response.getOutputStream(), error);
			}
		   }else {
			   filterChain.doFilter(request, response);
		   }
	    }
		
	}


}