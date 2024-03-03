package com.if5.todolist.repositories;

import com.if5.todolist.models.entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {

	@Query("SELECT U FROM Utilisateur U WHERE U.userName=?1")
    public Utilisateur findByUserName(String username);
	
    public Utilisateur  findByEmail(String email);
    @Query("SELECT U FROM Utilisateur U WHERE U.verificationCode=?1")
    public Utilisateur findByVerificationCode(String code);
    
    //public Utilisateur findByUserNameAndNomContaints();
    @Query("SELECT data FROM Utilisateur data")
    Page<Utilisateur> findAllByKeyWord(String keyWord, Pageable pageable);

    List<Utilisateur> findUtilisateursByRolesId(Long roleId);
}
