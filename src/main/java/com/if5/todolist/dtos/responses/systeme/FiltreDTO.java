package com.if5.todolist.dtos.responses.systeme;

import com.if5.todolist.models.entities.systeme.Filtre;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:00 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.dtos.responses.systeme
 **/
@Data
@Builder
@ToString
public class FiltreDTO {
    private Long id;

    @NotNull
    private int type; //  <0=Fenêtre, 1=Etat>

    @Column(nullable = false, length = 100)
    private String libelle;

    @Column(nullable = false, length = 100)
    private String libelleEn;

    @NotNull
    private String label;

    @NotNull
    private String description;

    @NotNull
    private int typeSelection; //  <0=Zone de texte simple, 1=Zone de texte avec autocomplétion, 2=Liste simple,
    // 3=Liste avec auto complétion, 4=Sélecteur de date, 5=Boite à cocher, 6=Bouton radio>

    @NotNull
    private int selectionEnPlage; //  <0=Non, 1=Oui>

    private String labelFinPlage;

    private String sourceDeDonnees;

    @NotNull
    private String nomParametreEtat;

    private String nomParametreEtatFinPlage;

    public static Filtre buildFromDTO(FiltreDTO dto) {
        return Filtre.builder()
                .id(dto.getId())
                .type(dto.getType())
                .libelle(dto.getLibelle())
                .libelleEn(dto.getLibelleEn())
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

    public static FiltreDTO buildFromEntity(Filtre entity) {
        return FiltreDTO.builder()
                .id(entity.getId())
                .type(entity.getType())
                .libelle(entity.getLibelle())
                .libelleEn(entity.getLibelleEn())
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
