package com.if5.todolist.models.dtos.requests.systeme;

import com.if5.todolist.models.entities.systeme.Filtre;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:19 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.dtos.requests.systeme
 **/
@Data
@Builder
@ToString
public class SimpleFiltreDTO {
    @NotNull
    private Long id;

    private int type; //  <0=Fenêtre, 1=Etat>

    private String libelle;

    private String label;

    private String description;

    private int typeSelection; //  <0=Zone de texte simple, 1=Zone de texte avec autocomplétion, 2=Liste simple,
    // 3=Liste avec auto complétion, 4=Sélecteur de date, 5=Boite à cocher, 6=Bouton radio>

    private int selectionEnPlage; //  <0=Non, 1=Oui>

    private String labelFinPlage;

    private String sourceDeDonnees;

    private String nomParametreEtat;

    private String nomParametreEtatFinPlage;

    public static Filtre buildSimpleFiltreFromDTO(SimpleFiltreDTO dto) {
        return Filtre.builder()
                .id(dto.getId())
                .type(dto.getType())
                .libelle(dto.getLibelle())
                .label(dto.getLabel())
                .description(dto.getDescription())
                .typeSelection(dto.getTypeSelection())
                .selectionEnPlage(dto.getSelectionEnPlage())
                .labelFinPlage(dto.getLabelFinPlage())
                .sourceDeDonnees(dto.getSourceDeDonnees())
                .nomParametreEtat(dto.getNomParametreEtat())
                .nomParametreEtatFinPlage(dto.getNomParametreEtatFinPlage())
                .build();
    }

    public static SimpleFiltreDTO buildFromEntity(Filtre entity) {
        return SimpleFiltreDTO.builder()
                .id(entity.getId())
                .type(entity.getType())
                .libelle(entity.getLibelle())
                .label(entity.getLabel())
                .description(entity.getDescription())
                .typeSelection(entity.getTypeSelection())
                .selectionEnPlage(entity.getSelectionEnPlage())
                .labelFinPlage(entity.getLabelFinPlage())
                .sourceDeDonnees(entity.getSourceDeDonnees())
                .nomParametreEtat(entity.getNomParametreEtat())
                .nomParametreEtatFinPlage(entity.getNomParametreEtatFinPlage())
                .build();
    }
}
