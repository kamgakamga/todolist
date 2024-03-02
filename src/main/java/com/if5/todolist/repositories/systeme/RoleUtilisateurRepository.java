package com.if5.todolist.repositories.systeme;

import com.if5.todolist.models.entities.systeme.RoleAction;
import com.if5.todolist.models.entities.systeme.RoleUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 1:38 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.repositories.systeme
 **/
public interface RoleUtilisateurRepository extends JpaRepository<RoleUtilisateur, Long> {
    @Query("select (count(r) > 0) from RoleUtilisateur r where r.role.id = ?1")
    boolean existsByRoleId();

    List<RoleUtilisateur> findByUtilisateurId(Long id);

    @Query("select r from RoleUtilisateur r where r.utilisateur.id = ?1 and r.role.id = ?2")
    List<RoleUtilisateur> findAllByUtilisateurIdAndRoleId(Long utilisateur_id, Long role_id);

    boolean existsByUtilisateurIdAndRoleId(Long utilisateur_id, Long role_id);

    void deleteAllByUtilisateurIdAndRoleId(Long utilisateur_id, Long role_id);

    void deleteAllByUtilisateurId(Long utilisateur_id);
}
