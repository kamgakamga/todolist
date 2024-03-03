package com.if5.todolist.repositories.systeme;

import com.if5.todolist.models.entities.systeme.ColonneTableauFenetre;
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
public interface ColonneTableauFenetreRepository extends JpaRepository<ColonneTableauFenetre, Long> {
    // Liste des colonnes disponibles pour un utilisateur sur un tableau
    @Query("SELECT C FROM ColonneTableauFenetre C WHERE C.tableauFenetre.id = :idTableauFenetre AND C.id NOT IN (SELECT U.colonneTableauFenetre.id FROM UtilisateurColonneTableauFenetre U WHERE U.utilisateur.id= :idUser)")
    List<ColonneTableauFenetre> listeColonnesDisponible(Long idUser, Long idTableauFenetre);

    // Liste des colonnes apr defaut qui sont encore disponible pour un utilisateur sur un tableau
    @Query("SELECT C FROM ColonneTableauFenetre C WHERE C.colDefaut = 1 AND C.tableauFenetre.id = :idTableauFenetre AND C.id NOT IN (SELECT U.colonneTableauFenetre.id FROM UtilisateurColonneTableauFenetre U WHERE U.utilisateur.id= :idUser)")
    List<ColonneTableauFenetre> listeColonnesParDefautDisponible(Long idUser, Long idTableauFenetre);

    @Query("select c from ColonneTableauFenetre c where c.colDefaut = 1")
    List<ColonneTableauFenetre> listeColonnesParDefaut();
}
