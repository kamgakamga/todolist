package com.if5.todolist.models.entities.systeme;

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
@Table(name = "sys_modele_impression")
public class ModeleImpression extends AuditModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modele_impression")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_etat_imprimable", foreignKey = @ForeignKey(name = "fk_etat_imprimable_modele_impression"))
    private EtatImprimable etatImprimable;

    @Column(name = "nom_modele", length = 100)
    private String nomModele;

    @Column(name = "id_transaction_imprimable")
    private Long idTransactionImprimable;

    /* On doit avoir un seul modèle par défaut sur une transaction imprimable */
    @Column(name = "modele_defaut")
    private int modeleDefaut; //  <0=Non, 1=Oui>
}
