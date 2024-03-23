package com.if5.todolist.dtos.responses.systeme;

import com.if5.todolist.models.entities.systeme.EtatImprimable;
import com.if5.todolist.models.entities.systeme.FiltreEtatImprimable;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 3:56 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.dtos.responses.systeme
 **/
@Data
@Builder
public class ListeFiltreEtatDTO {
    EtatimprimableResponseDTO etat;
    List<FiltreEtatDTO> filtres;

    public static ListeFiltreEtatDTO buildFromEntities(EtatImprimable etatImprimable, List<FiltreEtatImprimable> filtreslist) {
        return ListeFiltreEtatDTO.builder()
                .etat(EtatimprimableResponseDTO.buildFromEntity(etatImprimable))
                .filtres(FiltreEtatDTO.buildFromEntityList(filtreslist))
                .build();
    }

    public static ListeFiltreEtatDTO buildFromEtatsAndFiltreEtatDTO(EtatImprimable etatImprimable, List<FiltreEtatDTO> filtres) {
        return ListeFiltreEtatDTO.builder()
                .etat(EtatimprimableResponseDTO.buildFromEntity(etatImprimable))
                .filtres(filtres)
                .build();
    }
}
