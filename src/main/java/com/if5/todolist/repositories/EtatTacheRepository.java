package com.if5.todolist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.if5.todolist.models.entities.EtatTache;

public interface EtatTacheRepository  extends JpaRepository<EtatTache, Long>{
	
	public EtatTache findByLibelleIgnoreCase(String e);
	public List<EtatTache> findAllByDefaultValue(Boolean value);


}
