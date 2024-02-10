package com.if5.todolist;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.session.RedisSessionProperties.ConfigureAction;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.util.SerializationUtils;

import com.if5.todolist.models.entities.EtatTache;
import com.if5.todolist.models.enumerations.Sexe;
import com.if5.todolist.repositories.EtatTacheRepository;
import com.if5.todolist.services.interfaces.EtatTacheServiceInter;

@SpringBootApplication
@EnableJpaAuditing
public class TodolistApplication implements CommandLineRunner {

	@Autowired private EtatTacheServiceInter etatTacheServiceInter;
	@Autowired private EtatTacheRepository  etatTacheRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(TodolistApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		//System.out.println(System.getProperty("user.home"));
		
		System.out.println("Valeur= "+Sexe.values());
		String nom = "Serges"; 
		System.out.println("=> "+Base64.getEncoder().encodeToString(nom.getBytes()));
		
		List<String> listEtat = Arrays.asList("EN_ATTENTE","EN_COURS","REALISEE","ANNULLEE","EN_RETARD");
		
		for(String s: listEtat) {
			EtatTache etatTache = new EtatTache();
			
			EtatTache e = etatTacheServiceInter.getByLibelle(s);
			if(e == null) {
				etatTache.setDefaultValue(true);
				etatTache.setLibelle(s);
				etatTacheRepository.save(etatTache);
			}
		}
	}

}
