package com.if5.todolist.models.entities.systeme;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "lnk_role_utilisateur")
public class RoleUtilisateur extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role_utilisateur")
    private Long id;

    @ManyToOne(targetEntity = Utilisateur.class)
    @JoinColumn(name = "id_utilisateur", foreignKey = @ForeignKey(name = "fk_utilisateur_role_utilisateur"))
    private Utilisateur utilisateur;

    @ManyToOne(targetEntity = Role.class)
    @JoinColumn(name = "id_role", foreignKey = @ForeignKey(name = "fk_role_role_utilisateur"))
    private Role role;

    @Column(name = "date_alloc")
    private LocalDateTime dateAlloc = LocalDateTime.now();

    public static RoleUtilisateur buildFromIdUserIdRole(Long idUser, Long idRole) {
        return RoleUtilisateur.builder()
                .utilisateur(Utilisateur.UtilisateurBuilder.anUtilisateur().id(idUser).build())
                .role(Role.RoleBuilder.aRole().id(idRole).build())
                .build();
    }
}
