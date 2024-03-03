package com.if5.todolist.services.implementations;

import com.if5.todolist.models.entities.Utilisateur;
import com.if5.todolist.repositories.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService,UserDetails {
	
	private Utilisateur utilisateur;
	@Autowired private UtilisateurRepository utilisateurRepository;
	 
	 
	    @Override
		public UserDetails loadUserByUsername(String username) {
			
			Utilisateur utilisateur = utilisateurRepository.findByUserName(username);
		
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			
			utilisateur.getRoles().forEach(r->{
			        	  
			    	  authorities.add(new SimpleGrantedAuthority(r.getNom()));
			    	  	 
			      });
			return new User(utilisateur.getUserName(), utilisateur.getPassword(), authorities);
		}


		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public String getPassword() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public boolean isAccountNonExpired() {
			// TODO Auto-generated method stub
			return false;
		}


		@Override
		public boolean isAccountNonLocked() {
			// TODO Auto-generated method stub
			return false;
		}


		@Override
		public boolean isCredentialsNonExpired() {
			// TODO Auto-generated method stub
			return false;
		}


		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return utilisateur.isEnabled();
		} 

}
