package com.if5.todolist.repositories;

import com.if5.todolist.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long>{

    public Optional<Role> findByNom(String nom);
    List<Role> findRolesByUtilisateursId(Long utilisateurId);
  
}
