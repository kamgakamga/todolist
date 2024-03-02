package com.if5.todolist.models.dtos.responses.systeme;

import com.if5.todolist.models.dtos.requests.systeme.ParamEtatDTO;
import com.if5.todolist.models.entities.systeme.Filtre;
import com.if5.todolist.models.entities.systeme.FiltreEtatImprimable;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 3:59 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.dtos.responses.systeme
 **/
@Data
@Builder
public class FiltreEtatDTO {

    FiltreDTO filtre;
    private Long idFiltreEtat;
    List<ParamEtatDTO> sourceDonnees;

    public static FiltreEtatDTO buildFromEntity(FiltreEtatImprimable entity) {
        return FiltreEtatDTO.builder()
                .idFiltreEtat(entity.getId())
                .filtre(FiltreDTO.buildFromEntity(entity.getFiltre()))
                .sourceDonnees(new ArrayList<>())
                .build();
    }

    public static FiltreEtatDTO buildFromEntities(FiltreEtatImprimable entity, Filtre filtre, List<ParamEtatDTO> sourceDonnees) {
        return FiltreEtatDTO.builder()
                .idFiltreEtat(entity.getId())
                .filtre(FiltreDTO.buildFromEntity(filtre))
                .sourceDonnees((Objects.isNull(sourceDonnees) || sourceDonnees.isEmpty()) ? new ArrayList<>() : sourceDonnees)
                .build();
    }

    public static List<FiltreEtatDTO> buildFromEntityList(List<FiltreEtatImprimable> entityList) {
        return entityList.stream().map(FiltreEtatDTO::buildFromEntity).collect(Collectors.toList());
    }
}
