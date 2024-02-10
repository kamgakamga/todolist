package com.if5.todolist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired protected UserDetailsService userDetailsService;
	@Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userDetailsService)
		     .passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//cette ligne permet de desactiver le token csrf utililiser lors de l'authentification en memoire ou basique
				http.csrf().disable()
				    .httpBasic().disable()
				    .formLogin();
				//cette ligne permet d'activer l'authentification sans etat tout en desactivant l"authantification baser sur les session
				/*http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
				http.authorizeRequests()
				     
				    .antMatchers("/login/**","/swagger-resources/**","/swagger-resources","/swagger-ui/**","/v3/api-docs/**")
			        .permitAll();
				http.authorizeRequests().antMatchers(HttpMethod.POST,"/userRegister/**").hasAnyAuthority("ADMIN","MANAGER","USER");
				http.authorizeRequests()
				    .antMatchers(HttpMethod.PUT, "/toDoList/v1/role-to-user").hasAuthority("ADMIN")
				    .antMatchers(HttpMethod.POST, "/toDoList/v1/save-role").hasAuthority("ADMIN")
				    .antMatchers(HttpMethod.GET, "/toDoApp/v1/tache-details/").hasAuthority("ADMIN")
				    .anyRequest()
				   .authenticated();
				  http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
				  http.addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);*/
	}
	
	
	/*@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
		
	}*/
}

