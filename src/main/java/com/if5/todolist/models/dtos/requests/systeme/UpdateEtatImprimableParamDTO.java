package com.if5.todolist.models.dtos.requests.systeme;

import com.if5.todolist.models.entities.systeme.EtatImprimable;
import com.if5.todolist.models.entities.systeme.Fenetre;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 2:11 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.dtos.requests.systeme
 **/
@Data
@Builder
public class UpdateEtatImprimableParamDTO {
    @NotNull
    @ApiModelProperty(name = "id", value = "Idenifiant de l'etat Imprimable a modifier")
    private Long id;

    @NotNull
    @ApiModelProperty(name = "type", value = "Type de l'etat Imprimable <0=Fenêtre, 1=Etat>")
    private Integer type; //  <0=Fenêtre, 1=Etat>

    @NotNull
    @ApiModelProperty(name = "libelle", value = "libelle de l'etat Imprimable")
    private String libelle;

    @NotNull
    @ApiModelProperty(name = "description", value = "description de l'etat Imprimable <0=Fenêtre, 1=Etat>")
    private String description;

    @ApiModelProperty(name = "groupe", value = "groupe de l'etat Imprimable <0=Fenêtre, 1=Etat>")
    private String groupe;

    @NotNull
    @ApiModelProperty(name = "chemin", value = "chemin de l'etat Imprimable <0=Fenêtre, 1=Etat>")
    private String chemin;

    @NotNull
    @ApiModelProperty(name = "exportable", value = "statut definissant si l'etat est exportable <0=Non, 1=Oui>")
    private Integer exportable; //  <0=Non, 1=Oui>

    @NotNull
    @ApiModelProperty(name = "idfenetre", value = "Identifiant de la fenetre a laquelle cet etat appartien")
    private Long idFenetre;

    public static EtatImprimable buildFromDTO(UpdateEtatImprimableParamDTO dto) {
        return EtatImprimable.builder()
                .id(dto.getId())
                .type(dto.getType())
                .libelle(dto.getLibelle())
                .description(dto.getDescription())
                .groupe(dto.getGroupe())
                .chemin(dto.getChemin())
                .exportable(dto.getExportable())
                .fenetre(Fenetre.builder().id(dto.idFenetre).build())
                .build();
    }
}
