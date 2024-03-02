package com.if5.todolist.services.interfaces.systeme;

import com.if5.todolist.models.dtos.responses.systeme.ShortModeleImpressionDTO;
import com.if5.todolist.models.entities.systeme.ModeleImpression;
import com.if5.todolist.services.interfaces.generics.CommonGenericCrudService;

import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:22 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.interfaces.systeme
 **/
public interface ModeleImpressionService  extends CommonGenericCrudService<ModeleImpression> {
    /**
     * @param idEtat Identifiant de l'etat imprimable
     * @return la liste java.util.List<com.nfl.smartschool.dto.systeme.etat.ShortModeleImpressionDTO>
     * @implSpec liste des modèles d'impression sur la base d'un état
     */
    List<ShortModeleImpressionDTO> findAllByEtatImprimableId(Long idEtat);
}
