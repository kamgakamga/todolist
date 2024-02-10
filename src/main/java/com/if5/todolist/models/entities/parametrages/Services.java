package com.if5.todolist.models.entities.parametrages;

import com.if5.todolist.models.entities.AuditModel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "services")
@NoArgsConstructor
@AllArgsConstructor
public class Services extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", length = 50)
    private String code;

    @Column(name = "libelle", length = 150)
    private String libelle;

    @Column(name = "description", length = 250)
    private String description;
}
