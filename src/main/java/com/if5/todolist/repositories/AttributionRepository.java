package com.if5.todolist.repositories;

import com.if5.todolist.models.entities.Attribution;
import com.if5.todolist.models.entities.Tache;
import com.if5.todolist.models.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributionRepository extends JpaRepository<Attribution, Long>{
	
	@Query("SELECT AT.tache FROM Attribution AT WHERE AT.utilisateur.id=?1")
    public List<Tache> findAlltaskUser(Long userId);
	
	@Query("SELECT AT.utilisateur FROM Attribution AT WHERE AT.tache.id=?1")
	public Utilisateur findResponsableTache(Long id);
	
	 @Query(value = "SELECT AT.tache FROM Attribution AT WHERE AT.utilisateur.id=?1")
	public List<Tache> getAllTacheForUser(Long utilisateurId);
	
}
