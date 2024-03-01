package com.if5.todolist.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.if5.todolist.models.entities.Projet;

@Transactional
@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

	 Projet findByNomProjetIgnoreCase(String nomProjet);
	@Query("SELECT data FROM Projet data WHERE (data.nomProjet LIKE :keyWord OR data.description LIKE :keyWord)")
	Page<Projet> findAllByKeyWord(String keyWord, Pageable pageable);
	//@Query("SELECT data FROM Projet data WHERE (data.nomProjet LIKE :keyWord OR data.description LIKE :keyWord )")
	//Page<Projet> findAllByKeyWord(String keyWord, Pageable pageable);

}
