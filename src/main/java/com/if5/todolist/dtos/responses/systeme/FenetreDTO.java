package com.if5.todolist.dtos.responses.systeme;

import com.if5.todolist.models.entities.systeme.Fenetre;
import com.if5.todolist.models.entities.systeme.ModuleSysteme;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 3:58 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.dtos.responses.systeme
 **/

@Data
@Builder
@ToString
public class FenetreDTO {
    private Long id;
    private String idObjet;
    @NotNull
    private String libelleFenetre;
    @NotNull
    private String description;
    @NotNull
    private String url;
    private int fenetreEditable; // <0=Non, 1=Oui>
    private int fenetreFiltre; // <0=Non, 1=Oui>
    private int fenetreImpression; // <0=Non, 1=Oui>
    private Long idModule;

    public static Fenetre buildFromDTO(FenetreDTO dto) {
        return Fenetre.builder()
                .id(dto.getId())
                .fenetreEditable(dto.getFenetreEditable())
                .fenetreFiltre(dto.getFenetreFiltre())
                .fenetreImpression(dto.getFenetreImpression())
                .description(dto.getDescription())
                .idObjet(dto.getIdObjet())
                .libelleFenetre(dto.getLibelleFenetre())
                .url(dto.getUrl())
                .module(ModuleSysteme.builder().id(dto.getIdModule()).build())
                .build();
    }

    public static FenetreDTO buildFromEntity(Fenetre entity) {
        return FenetreDTO.builder()
                .id(entity.getId())
                .fenetreEditable(entity.getFenetreEditable())
                .fenetreFiltre(entity.getFenetreFiltre())
                .fenetreImpression(entity.getFenetreImpression())
                .description(entity.getDescription())
                .idObjet(entity.getIdObjet())
                .libelleFenetre(entity.getLibelleFenetre())
                .url(entity.getUrl())
                .idModule(Objects.isNull(entity.getModule()) ? null : entity.getModule().getId())
                .build();
    }

    public static List<FenetreDTO> buildFromEntityList(List<Fenetre> entityList) {
        return entityList.isEmpty() ? new ArrayList<>() :
                entityList.stream().map(FenetreDTO::buildFromEntity).collect(Collectors.toList());
    }
}
