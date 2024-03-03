package com.if5.todolist.repositories;

import com.if5.todolist.models.entities.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {

	public Sprint findByLibelleIgnoreCase(String libelle);

	public List<Sprint> findAllByProjetId(Long id);

	//List<Sprint> findAllByEtat(String libelle);

}
