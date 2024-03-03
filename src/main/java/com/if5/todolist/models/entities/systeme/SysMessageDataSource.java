package com.if5.todolist.models.entities.systeme;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "sys_src_data_com")
public class SysMessageDataSource extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_src_data_com")
    private Long id;

    @Column(name = "label", length = 150)
    private String label;
    @Column(name = "modele_message", length = 500)
    private String modeleMessage;
    /**
     * Exemple de modèle de message : ...%s ... %s ... %s ....
     */
    @Column(name = "sql_query", length = 15000)
    @JsonIgnore
    private String sqlQuery; /* Exemple de SQL_QUERY SELECT NUMTEL_DEST, MESSAGE_PARAM_1, MESSAGE_PARAM_2, ..., MESSAGE_PARAM_N FROM */
}
