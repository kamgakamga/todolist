package com.if5.todolist.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.if5.todolist.models.entities.Utilisateur;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {

	@Query("SELECT U FROM Utilisateur U WHERE U.userName=?1")
    public Utilisateur findByUserName(String username);
	
    public Utilisateur  findByEmail(String email);
    @Query("SELECT U FROM Utilisateur U WHERE U.verificationCode=?1")
    public Utilisateur findByVerificationCode(String code);
    
    //public Utilisateur findByUserNameAndNomConteaints();
    
    List<Utilisateur> findUtilisateursByRolesId(Long roleId);
}
