package com.if5.todolist.repositories.systeme;

import com.if5.todolist.models.entities.systeme.ActionSysteme;
import org.springframework.data.jpa.repository.JpaRepository;
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
public interface ActionSystemeRepository extends JpaRepository<ActionSysteme, Long> {
    List<ActionSysteme> findAllByFenetreId(Long idFenetre);

    boolean existsByLibelleAction(String libelle);

    @Query("SELECT A FROM ActionSysteme A WHERE A.id NOT IN (SELECT RS.action.id FROM RoleAction RS WHERE RS.role.id= :idRole) ORDER BY A.fenetre.id")
    List<ActionSysteme> listeActionsRoleDisponible(Long idRole);

    @Query("select a from ActionSysteme a where a.typeObjet = :idType AND a.fenetre.id= :idFenetre and a.id in (SELECT R.action.id FROM RoleAction R, RoleUtilisateur U WHERE R.role.id = U.role.id AND U.utilisateur.id= :idUser)")
    List<ActionSysteme> listeActionsParTypeObjetParFenetrePourUtilisateur(int idType, Long idUser, Long idFenetre);

    @Query("select a from ActionSysteme a where a.typeObjet = :idType and a.id in (SELECT R.action.id FROM RoleAction R, RoleUtilisateur U WHERE R.role.id = U.role.id AND U.utilisateur.id= :idUser)")
    List<ActionSysteme> listeActionsParTypeObjetPourUtilisateur(int idType, Long idUser);
}
