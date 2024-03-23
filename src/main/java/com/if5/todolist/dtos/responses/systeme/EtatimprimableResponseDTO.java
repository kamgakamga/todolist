package com.if5.todolist.dtos.responses.systeme;

import com.if5.todolist.models.entities.systeme.EtatImprimable;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 3:57 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.dtos.responses.systeme
 **/
@Data
@Builder
public class EtatimprimableResponseDTO {
    private Long id;
    private Integer type; //  <0=Fenêtre, 1=Etat>
    private String libelle;
    private String description;
    private String groupe;
    private String chemin;
    private Integer exportable; //  <0=Non, 1=Oui>
    FenetreDTO fenetre;

    public static EtatimprimableResponseDTO buildFromEntity(EtatImprimable entity) {
        return EtatimprimableResponseDTO.builder()
                .id(entity.getId())
                .type(entity.getType())
                .libelle(entity.getLibelle())
                .description(entity.getDescription())
                .groupe(entity.getGroupe())
                .chemin(entity.getChemin())
                .exportable(entity.getExportable())
                .fenetre(Objects.isNull(entity.getFenetre()) ? null : FenetreDTO.buildFromEntity(entity.getFenetre()))
                .build();
    }

    public static List<EtatimprimableResponseDTO> buildFromEntityList(List<EtatImprimable> entityList) {
        return entityList.stream().map(EtatimprimableResponseDTO::buildFromEntity).collect(Collectors.toList());
    }
}
