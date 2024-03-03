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
@Table(name = "sys_colonne_tableau_fenetre")
public class ColonneTableauFenetre extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_colonne_tableau_fenetre")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name="id_tableau_fenetre", foreignKey = @ForeignKey(name = "fk_tableau_fenetre_colonne_tableau_fenetre"))
    @ManyToOne(targetEntity = TableauFenetre.class)
    private TableauFenetre tableauFenetre;

    @Column(name = "id_colonne")
    private Long idColonne;

    @Column(name = "nom_colonne", nullable = false, length = 100)
    private String nomColonne;

    @Column(name = "label_colonne", nullable = false, length = 100)
    private String labelColonne;

    @Column(name = "type_donnee", nullable = false)
    private int typeDonnee; // <0=Chaine, 1=Entier, 2=Double, 3=Date, 4=Timestamp, 4=Booleen>

    @Column(name = "col_defaut", nullable = false)
    private int colDefaut; // <0=Non, 1=Oui>,

    @Column(name = "col_editable", nullable = false)
    private int colEditable; // <0=Non, 1=Oui>,

    @Column(name = "col_lock", nullable = false)
    private int colLock; // <0=Non, 1=Oui>,

    @Column(name = "col_printable", nullable = false)
    private int colPrintable; // <0=Non, 1=Oui>,

    @Column(name = "col_readable", nullable = false)
    private int colReadable; // <0=Non, 1=Oui>,

    @Column(name = "col_joined", nullable = false)
    private int colJoined; // <0=Non, 1=Oui>,

    @Column(name = "col_join_table_name", length = 200)
    private String colJoinTableName;

    @Column(name = "col_join_table_label", length = 200)
    private String colJoinTableLabel;

    @Column(name = "alias_table_jointure", length = 100)
    private String aliasTableJointure;

    @Column(name = "nom_table_jointure", length = 100)
    private String nomTableJointure;

    @Column(name = "numero_ordre")
    private int numeroOrdre;

    @Column(name = "nom_table", nullable = false, length = 100)
    private String nomTable;
}
