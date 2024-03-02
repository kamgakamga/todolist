package com.if5.todolist.models.entities.systeme;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "prm_etat_imprimable")
public class EtatImprimable extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_etat_imprimable")
    private Long id;

    @Column(name = "type_filtre")
    private int type; //  <0=Fenêtre, 1=Etat>

    @Column(nullable = false, length = 100)
    private String libelle;

    @Column(nullable = false, length = 100)
    private String libelle_en;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false, length = 5000)
    private String description_en;

    @Column(length = 500)
    private String groupe;

    @Column(nullable = false, length = 5000)
    private String chemin;

    private int exportable; //  <0=Non, 1=Oui>

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JoinColumn(name = "id_fenetre", foreignKey = @ForeignKey(name = "fk_fenetre_etat_imprimable"))
    private Fenetre fenetre;
}
