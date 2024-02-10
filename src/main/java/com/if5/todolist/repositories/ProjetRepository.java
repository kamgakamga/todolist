package com.if5.todolist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.if5.todolist.models.entities.Projet;

@Transactional
@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

	public Projet findByNomProjetIgnoreCase(String nomProjet);
	

}
