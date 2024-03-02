package com.if5.todolist.repositories.systeme;

import com.if5.todolist.models.entities.systeme.Tableau;
import com.if5.todolist.models.entities.systeme.UtilisateurColonneTableauFenetre;
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
public interface UtilisateurColonneTableauFenetreRepository extends JpaRepository<UtilisateurColonneTableauFenetre, Long> {
    boolean existsByUtilisateurIdAndColonneTableauFenetreId(Long utilisateur_id, Long colonneTableauFenetre_id);

    void deleteAllByUtilisateurId(Long idUtilisateur);

    // liste des colonnes affectées à un utilisateur sur un tableau
    List<UtilisateurColonneTableauFenetre> findAllByUtilisateurIdAndColonneTableauFenetreTableauFenetreId(Long idUtilisateur, Long idTableauFenetred);

    @Query("select u from UtilisateurColonneTableauFenetre u where u.utilisateur.id = ?1 and u.colonneTableauFenetre.tableauFenetre.id = ?2 and u.colonneTableauFenetre.colReadable = 1")
    List<UtilisateurColonneTableauFenetre> findUserColumnsReadable(Long idUtilisateur, Long idTableauFenetre);

    @Query("select u from UtilisateurColonneTableauFenetre u where u.utilisateur.id = ?1 and u.colonneTableauFenetre.tableauFenetre.id = ?2 and u.colonneTableauFenetre.colReadable = 1 and u.colonneTableauFenetre.colJoined = 1")
    List<UtilisateurColonneTableauFenetre> findUserColumnsReadableAndJoinable(Long idUtilisateur, Long idTableauFenetre);

}
