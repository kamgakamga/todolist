package com.if5.todolist.repositories.systeme;

import com.if5.todolist.models.entities.systeme.ModuleSysteme;
import com.if5.todolist.models.entities.systeme.RoleAction;
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
public interface RoleActionRepository extends JpaRepository<RoleAction, Long> {
    //    @Query("select r from RoleAction r ORDER BY r.role.name")
    @Modifying
    @Query("delete from RoleAction r where r is null or (r.role is null or r.role.id not in (select r.id from Role r)) or (r.action is null OR r.action.id not in ( select a.id from ActionSysteme a))")
    void supprimerRoleActionInexistant();

    @Modifying
    @Query("delete from RoleAction r where (r.role is null ) or (r.role.id = :role_id and (r.action is null OR r.action.id is null))")
    void supprimerRoleActionInexistant(Long role_id);

    @Query("select r from RoleAction r ORDER BY r.role.nom")
    List<RoleAction> findAllOrderByRole();

    @Query("select r from RoleAction r where r.role.id = ?1 order by r.action.fenetre.libelleFenetre")
    List<RoleAction> findAllByRoleId(Long idRole);

    @Query("select r from RoleAction r where r.role.id = ?1 and r.action.fenetre.id = ?2")
    List<RoleAction> listeActionsParRoleEtFenetre(Long role_id, Long fenetre_id);

    void deleteAllByRoleId(Long role_id);

    List<RoleAction> findAllByRoleIdOrderByActionLibelleAction(Long idRole);

    boolean existsByActionIdAndRoleId(long idAction, long idRole);

    boolean existsByActionId(long idAction);

    void deleteByRoleIdAndActionId(Long role_id, Long action_id);
}
