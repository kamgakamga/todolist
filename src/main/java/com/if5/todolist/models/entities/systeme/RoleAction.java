package com.if5.todolist.models.entities.systeme;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.if5.todolist.models.entities.AuditModel;
import com.if5.todolist.models.entities.Role;
import lombok.*;
import static com.if5.todolist.utils.StringsUtils.FALSE;

import javax.persistence.*;
import java.util.Objects;

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
@Table(name = "lnk_role_action")
public class RoleAction extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role_action")
    private Long id;

    @Column(name = "condition_actif", length = 5000)
    private String conditionActif;

    @Column(name = "condition_editable", length = 5000)
    private String conditionEditable;

    @Column(name = "condition_visible", length = 5000)
    private String conditionVisible;

    @JoinColumn(name = "id_role", foreignKey = @ForeignKey(name = "fk_role_role_action"))
    @ManyToOne(targetEntity = Role.class)
    private Role role;

    @JoinColumn(name = "id_action", foreignKey = @ForeignKey(name = "fk_action_role_action"))
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne(targetEntity = ActionSysteme.class)
    private ActionSysteme action;

    public static RoleAction buildEntity(ActionSysteme action, Role role) {
        return RoleAction.builder()
                .role(role)
                .action(action)
                .conditionActif(FALSE)
                .conditionEditable(FALSE)
                .conditionVisible(FALSE)
                .build();
    }
}
