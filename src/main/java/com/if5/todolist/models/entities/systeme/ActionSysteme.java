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
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "sys_action")
public class ActionSysteme extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_action")
    private Long id;

    @Column(name = "id_objet")
    private String idObjet;

    @Column(name = "libelle_action", nullable = false, length = 100)
    private String libelleAction;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(name = "type_objet")
    private int typeObjet; // <0=Menu, 1=Item_Menu, 2=Bouton, 3=Zone_de_saisie, 4=Zone_de_liste>, 5=Etat_imprimable>

    @JoinColumn(name="id_fenetre", foreignKey = @ForeignKey(name = "fk_fenetre_action_systeme"))
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne (targetEntity = Fenetre.class)
    private Fenetre fenetre;
}
