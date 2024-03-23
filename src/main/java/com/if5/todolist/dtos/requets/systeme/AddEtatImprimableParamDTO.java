package com.if5.todolist.dtos.requets.systeme;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

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
@ApiModel(value = "AddEtatImprimableParamDTO", description = "Cette classe permet d'ajouter simultanement plusieurs etats imprimables lies a une Fenetre de l'application")
public class AddEtatImprimableParamDTO {
    @NotNull @NotEmpty
    @ApiModelProperty(value = "Liste des etats imprimables a enregistrer")
    List<EtatImprimableParamDTO> etatImprimables;
    @NotNull
    @ApiModelProperty(value = "Identifiant de la fenetre pour laquelle l'etat sera enregistre")
    private Long idFenetre;
}
