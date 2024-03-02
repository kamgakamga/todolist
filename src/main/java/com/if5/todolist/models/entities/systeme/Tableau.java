package com.if5.todolist.models.entities.systeme;

import com.if5.todolist.models.entities.AuditModel;
import com.if5.todolist.models.entities.Role;
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
@Table(name = "sys_tableau")
public class Tableau extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tableau")
    private Long id;

    @Column(name = "libelle_tableau", nullable = false, length = 100)
    private String libelleTableau;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(name = "source_donnees", nullable = false, length = 5000)
    private String sourceDonnees;


    @Column(name = "id_objet")
    private String idObjet; // a definir relation
}
