package com.if5.todolist.models.entities.parametrages;

import com.if5.todolist.models.entities.AuditModel;
import lombok.*;

import javax.persistence.*;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 1:35 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.entities.parametrages
 **/
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "prm_variable_globale")
public class VariableGlobale  extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_variable_globale")
    private Long id;

    @Column(name = "cle", nullable = false, length = 100)
    private String cle;

    @Column(name = "valeur", nullable = false, length = 5000)
    private String valeur;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "description_en", length = 5000)
    private String descriptionEn;

    @Column(name = "visible")
    private int visible; //  <0=Non, 1=Oui>

    @Column(name = "type_donnees")
    private int typeDonnees; //   (0=Chaine, 1=Numérique, 2=Booleen, 3=Date, 4=Timestamp)

    @Override
    public String toString() {
        return "VariableGlobale{" +
                "id=" + id +
                ", cle='" + cle + '\'' +
                ", valeur='" + valeur + '\'' +
                ", description='" + description + '\'' +
                ", visible=" + visible +
                ", typeDonnees=" + typeDonnees +
                '}';
    }
}
