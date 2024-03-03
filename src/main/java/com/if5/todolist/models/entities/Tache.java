package com.if5.todolist.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property="id")
public class Tache extends AuditModel {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    private String description;

    private String statut;

    private double longitue;

    private double latitude;

    private String lieuDeRappel;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;

    @OneToMany(mappedBy = "tache")
    private List<Attribution> responsableTache;

    @ManyToOne(fetch = FetchType.LAZY)
   //@JsonBackReference
    private Tache tacheParente;

    
   //@JsonManagedReference
    @OneToMany(mappedBy = "tacheParente",fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Tache> listSousTache = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Sprint sprint;

}
