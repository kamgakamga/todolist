package com.if5.todolist.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.if5.todolist.models.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long>{

    public Optional<Role> findByNom(String nom);
    List<Role> findRolesByUtilisateursId(Long utilisateurId);
  
}
