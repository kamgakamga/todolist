package com.if5.todolist.repositories.systeme;

import com.if5.todolist.dtos.responses.systeme.EtatActionDTO;
import com.if5.todolist.models.entities.systeme.EtatImprimable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
public interface EtatImprimableRepository extends JpaRepository<EtatImprimable, Long> {
    boolean existsByLibelle(String libelle);

    @Query("select e from EtatImprimable e order by e.groupe, e.libelle")
    Page<EtatImprimable> listeEtats(Pageable pageable);

/*    @Query("select new com.if5.todolist.models.dtos.responses.systeme.output.EtatActionDTO(e.id, e.type, e.libelle,e.libelle_en, e.description, e.description_en, e.groupe, e.chemin, e.exportable, e.fenetre, a) " +
            "from EtatImprimable e left join ActionSysteme a on (e.fenetre = a.fenetre and a.typeObjet=5 and TRIM(concat(e.id, '')) = a.idObjet ) order by e.groupe, e.libelle")
    Page<EtatActionDTO> findByAllEtatAction(Pageable pageable);*/
@Query("select new com.if5.todolist.dtos.responses.systeme.EtatActionDTO(e.id, e.type, e.libelle,e.libelle_en, e.description, e.description_en, e.groupe, e.chemin, e.exportable, e.fenetre, a) " +
            "from EtatImprimable e left join ActionSysteme a on (e.fenetre = a.fenetre and a.typeObjet=5 and TRIM(concat(e.id, '')) = a.idObjet ) order by e.groupe, e.libelle")
    Page<EtatActionDTO> findByAllEtatAction(Pageable pageable);
}
