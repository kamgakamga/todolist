package com.if5.todolist.services.interfaces.systeme;

import com.if5.todolist.models.entities.systeme.Filtre;
import com.if5.todolist.services.interfaces.generics.CommonGenericCrudService;

import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:21 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.interfaces.systeme
 **/
public interface FiltreService  extends CommonGenericCrudService<Filtre> {
    /**
     * @param libelle libelle a verifier
     * @return true si ca existe et false le cas echeant
     * @implSpec verifie si un libelle existe deja en BD
     */
    boolean existsByLibelle(String libelle);

    /**
     * @param filtre objet a modifier
     * @return La valeur mise a jour
     * @implSpec Effectue la mise a jour d'un filtre
     */
    Filtre update(Filtre filtre);


    /**
     * @param idEtat ID de l'etat imprimable
     * @return La liste de tous les filtres disponibles pour un etat imprimable
     * @implSpec Recherche tous les filtres disponibles pour un etat imprimable
     */
    List<Filtre> listeFiltresDispoPourEtat(Long idEtat);

    List<Filtre> listeFiltres();
}
