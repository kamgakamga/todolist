package com.if5.todolist.models.dtos.responses.systeme;

import com.if5.todolist.models.entities.systeme.ActionSysteme;
import com.if5.todolist.models.entities.systeme.Fenetre;
import lombok.Builder;
import lombok.Data;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 2:03 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.dtos.system
 **/

@Data
@Builder
public class EtatActionDTO {
    private Long id;
    @Builder.Default
    private int type = 1; //  <0=Fenêtre, 1=Etat>
    private String libelle;
    private String libelle_en;
    private String description;
    private String description_en;
    private String groupe;
    private String chemin;
    @Builder.Default
    private int exportable = 1; //  <0=Non, 1=Oui>
    private Fenetre fenetre;
    private ActionSysteme actionSysteme;

    public EtatActionDTO(Long id, int type, String libelle, String description,String groupe, String chemin, int exportable, Fenetre fenetre, ActionSysteme actionSysteme) {
        this.id = id;
        this.type = type;
        this.libelle = libelle;
        this.description = description;
        this.groupe = groupe;
        this.chemin = chemin;
        this.exportable = exportable;
        this.fenetre = fenetre;
        this.actionSysteme = actionSysteme;
    }

    public EtatActionDTO(Long id, int type, String libelle, String libelle_en, String description, String description_en,String groupe, String chemin, int exportable, Fenetre fenetre, ActionSysteme actionSysteme) {
        this.id = id;
        this.type = type;
        this.libelle = libelle;
        this.libelle_en = libelle_en;
        this.description = description;
        this.description_en= description_en;
        this.groupe = groupe;
        this.chemin = chemin;
        this.exportable = exportable;
        this.fenetre = fenetre;
        this.actionSysteme = actionSysteme;
    }
}
