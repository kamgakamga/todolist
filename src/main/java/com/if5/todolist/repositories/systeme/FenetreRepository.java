package com.if5.todolist.repositories.systeme;

import com.if5.todolist.models.entities.systeme.Fenetre;
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
public interface FenetreRepository extends JpaRepository<Fenetre, Long> {
    boolean existsByLibelleFenetre(String libelle);

    List<Fenetre> findAllByModuleIdOrderByLibelleFenetre(Long module_id);

    @Query("select a.fenetre from ActionSysteme a where a.typeObjet = 5")
    List<Fenetre> listeFenetresAutorisantEtat();
}
