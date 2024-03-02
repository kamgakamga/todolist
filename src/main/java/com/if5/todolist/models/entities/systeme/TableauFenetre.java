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
@Table(name = "sys_tableau_fenetre")
public class TableauFenetre extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tableau_fenetre")
    private Long id;

    @JoinColumn(name="id_fenetre", foreignKey = @ForeignKey(name = "fk_fenetre_tableau_fenetre"))
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne (targetEntity = Fenetre.class)
    private Fenetre fenetre;

    @JoinColumn(name="id_tableau", foreignKey = @ForeignKey(name = "fk_tableau_tableau_fenetre"))
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne (targetEntity = Tableau.class)
    private Tableau tableau;
}
