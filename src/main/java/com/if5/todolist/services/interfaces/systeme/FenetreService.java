package com.if5.todolist.services.interfaces.systeme;

import com.if5.todolist.dtos.responses.systeme.FenetreDTO;
import com.if5.todolist.dtos.responses.systeme.ModuleFenetresDTO;
import com.if5.todolist.models.entities.systeme.Fenetre;
import com.if5.todolist.services.interfaces.generics.CommonGenericCrudService;

import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:13 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.interfaces.systeme
 **/
public interface FenetreService extends CommonGenericCrudService<Fenetre> {
    /**
     * @param fenetre object to update
     * @implSpec mise a jour d'une fenetre enregistree
     * @return la fenetre mise a jour
     * */
    FenetreDTO update(FenetreDTO fenetre);

    /**
     * @param libelle libelle de la fenetre
     * @implSpec verifie si cette valeur existe deja en BD
     * @return returns true if exists and false otherwise
     * */
    boolean existsByLibelle(String libelle);

    /**
     * @param idModule identifiant du module
     * @return la liste des fenetres d'un module
     * @implSpec Liste des fenetres appartenant a un module
     */
    ModuleFenetresDTO findAllByModuleId(Long idModule);

    /**
     * @return liste des fenêtres autorisant les états
     * @implSpec liste des fenêtres autorisant les états
     */
    List<FenetreDTO> listeFenetresAutorisantEtat();
}
