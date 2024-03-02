package com.if5.todolist.models.entities.systeme;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.if5.todolist.models.entities.AuditModel;
import com.if5.todolist.models.entities.Utilisateur;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
@Table(name = "lnk_utilisateur_colonne_tableau_fenetre")
public class UtilisateurColonneTableauFenetre extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur_colonne_tableau_fenetre")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name="id_colonne_tableau_fenetre", foreignKey = @ForeignKey(name = "fk_colonne_tableau_fenetre_utilisateur_colonne_tableau_fenetre"))
    @ManyToOne(targetEntity = ColonneTableauFenetre.class)
    private ColonneTableauFenetre colonneTableauFenetre;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name="id_utilisateur", foreignKey = @ForeignKey(name = "fk_utilisateur_utilisateur_colonne_tableau_fenetre"))
    @ManyToOne(targetEntity = Utilisateur.class)
    private Utilisateur utilisateur;

    @Column(name = "date_alloc")
    private LocalDateTime dateAlloc = LocalDateTime.now();
}
