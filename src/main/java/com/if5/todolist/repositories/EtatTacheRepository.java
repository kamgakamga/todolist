package com.if5.todolist.repositories;

import com.if5.todolist.models.entities.EtatTache;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtatTacheRepository  extends JpaRepository<EtatTache, Long>{
	
	public EtatTache findByLibelleIgnoreCase(String e);
	public List<EtatTache> findAllByDefaultValue(Boolean value);


}
