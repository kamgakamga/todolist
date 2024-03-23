package com.if5.todolist.dtos.requets.systeme;

import com.if5.todolist.models.entities.systeme.EtatImprimable;
import com.if5.todolist.models.entities.systeme.Fenetre;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 2:08 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.dtos.requests.systeme
 **/
@Data
@Builder
@ToString
@ApiModel(description = "Cette classe permet de remplir les valeurs d'un etat imprimable")
public class EtatImprimableParamDTO {
    @NotNull
    @ApiModelProperty(name = "type", notes = "Type de l'etat Imprimable <0=Fenêtre, 1=Etat>")
    private Integer type; //  <0=Fenêtre, 1=Etat>

    @NotNull
    @ApiModelProperty(name = "libelle", notes = "libelle de l'etat Imprimable")
    private String libelle;

    @NotNull
    @ApiModelProperty(name = "description", notes = "description de l'etat Imprimable <0=Fenêtre, 1=Etat>")
    private String description;

    @ApiModelProperty(name = "groupe", notes = "groupe de l'etat Imprimable <0=Fenêtre, 1=Etat>")
    private String groupe;

    @NotNull
    @ApiModelProperty(name = "chemin", notes = "chemin de l'etat Imprimable <0=Fenêtre, 1=Etat>")
    private String chemin;

    @NotNull
    @ApiModelProperty(name = "exportable", notes = "statut definissant si l'etat est exportable <0=Non, 1=Oui>")
    private Integer exportable; //  <0=Non, 1=Oui>

    public static EtatImprimable buildFromDTO(Long idFenetre, EtatImprimableParamDTO dto) {
        return EtatImprimable.builder()
                .type(dto.getType())
                .libelle(dto.libelle)
                .description(dto.getDescription())
                .groupe(dto.getGroupe())
                .chemin(dto.getChemin())
                .exportable(dto.getExportable())
                .fenetre(Fenetre.builder().id(idFenetre).build())
                .build();
    }

    public static List<EtatImprimable> buildFromDTOList(Long idFenetre, List<EtatImprimableParamDTO> dtoList) {
        return dtoList.stream().map(dto -> EtatImprimableParamDTO.buildFromDTO(idFenetre, dto)).collect(Collectors.toList());
    }
}
