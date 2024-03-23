package com.if5.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class HashPassword {
	
	@Bean 
	public BCryptPasswordEncoder getBCP(){
	return new BCryptPasswordEncoder();
	  }
}
