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
@Table(name = "sys_message")
public class MessageSysteme extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private Long id;

    @Column(name = "type_message")
    private int typeMessage; // <0=Erreur système, 1=Message métier, 2=Avertissement métier, 3=Erreur métier, 4=Erreur inconnue>

    @Column(name = "code_message", nullable = false, length = 100)
    private int codeMessage;

    @Column(name = "valeur_fr", length = 5000)
    private String valeurFr;

    @Column(name = "valeur_en", length = 5000)
    private String valeurEn;
}
