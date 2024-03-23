package com.if5.todolist.services.interfaces.systeme;

import com.if5.todolist.dtos.requets.systeme.AddFiltresEtatDTO;
import com.if5.todolist.dtos.responses.systeme.ListeFiltreEtatDTO;
import com.if5.todolist.models.entities.systeme.FiltreEtatImprimable;
import com.if5.todolist.services.interfaces.generics.CommonGenericCrudService;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:17 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.interfaces.systeme
 **/
public interface FiltreEtatImprimableService  extends CommonGenericCrudService<FiltreEtatImprimable> {
    /**
     * @param filtresEtat objet contenant l'Id de letat et les filtres a lui ajouter
     * @return com.nfl.smartschool.dto.systeme.etat.AddFiltresEtatDTO liste des filtres a ajouter a cet etat
     * @implSpec Ajoute des filtres a un etat
     */
    ListeFiltreEtatDTO ajouterFiltresEtat(AddFiltresEtatDTO filtresEtat);

    /**
     * @param updateFiltreEtat objet contenant les valeurs a modifier
     * @return FiltreEtatImprimable Objet mis a jour
     * @implSpec Mets a jour un FiltreEtatImprimable
     */
    FiltreEtatImprimable update(FiltreEtatImprimable updateFiltreEtat);

    /**
     * @param idEtat Id de letat
     * @return com.nfl.smartschool.dto.parametre.output.ListeFiltreEtatDTO liste des filtres de cet etat
     * @implSpec recherche la liste des filtres affectes a une etat
     */
    ListeFiltreEtatDTO findByIdEtat(Long idEtat);
}
