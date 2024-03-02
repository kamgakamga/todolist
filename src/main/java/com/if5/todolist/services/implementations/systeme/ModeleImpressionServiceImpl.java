package com.if5.todolist.services.implementations.systeme;

import com.if5.todolist.models.dtos.responses.systeme.ShortModeleImpressionDTO;
import com.if5.todolist.models.entities.systeme.ModeleImpression;
import com.if5.todolist.repositories.systeme.ModeleImpressionRepository;
import com.if5.todolist.services.implementations.generics.CommonGenericCrudServiceImpl;
import com.if5.todolist.services.interfaces.systeme.ModeleImpressionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:39 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.implementations.systeme
 **/
@Service
public class ModeleImpressionServiceImpl  extends CommonGenericCrudServiceImpl<ModeleImpressionRepository, ModeleImpression> implements ModeleImpressionService {
    public ModeleImpressionServiceImpl(ModeleImpressionRepository repository) {
        super(repository);
    }

    /**
     * @param idEtat Identifiant de l'etat imprimable
     * @return la liste java.util.List<com.nfl.smartschool.dto.systeme.etat.ShortModeleImpressionDTO>
     * @implSpec liste des modèles d'impression sur la base d'un état
     */
    @Override
    public List<ShortModeleImpressionDTO> findAllByEtatImprimableId(Long idEtat) {
        return ShortModeleImpressionDTO.buildFromEntityList(repository.findAllByEtatImprimableId(idEtat));
    }
}
