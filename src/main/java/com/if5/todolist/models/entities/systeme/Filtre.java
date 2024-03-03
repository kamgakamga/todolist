package com.if5.todolist.models.entities.systeme;

import com.if5.todolist.models.entities.AuditModel;
import lombok.*;

import javax.persistence.*;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 12:26 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.entities.systeme
 **/
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "sys_filtre")
public class Filtre extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_filtre")
    private Long id;

    @Column(name = "type_filtre")
    private int type; //  <0=Fenêtre, 1=Etat, 2=MessageDataSource>

    @Column(nullable = false, length = 100)
    private String libelle;

    @Column(nullable = false, length = 100)
    private String libelleEn;

    @Column(nullable = false, length = 100)
    private String label;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(name = "type_selection")
    private int typeSelection; //  <0=Zone de texte simple, 1=Zone de texte avec autocomplétion, 2=Liste simple,
    // 3=Liste avec auto complétion, 4=Sélecteur de date, 5=Boite à cocher, 6=0>

    @Column(name = "selection_en_plage")
    private int selectionEnPlage; //  <0=Non, 1=Oui>

    @Column(name = "label_fin_plage", length = 100)
    private String labelFinPlage;

    @Column(name = "source_de_donnees", length = 5000)
    private String sourceDeDonnees;

    @Column(name = "nom_parametre_etat", nullable = false, length = 100)
    private String nomParametreEtat;

    @Column(name = "nom_parametre_etat_fin_plage", length = 100)
    private String nomParametreEtatFinPlage;
}
