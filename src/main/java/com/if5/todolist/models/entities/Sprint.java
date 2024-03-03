package com.if5.todolist.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property="id")
public class Sprint extends AuditModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String libelle;
	
	private String description;
	
    private LocalDateTime dateDebut;
	
	private LocalDateTime dateFin;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Projet projet;
	
	@OneToMany(mappedBy = "sprint" , fetch = FetchType.LAZY)
	private List<Tache> taches ;
	
	@ManyToMany(fetch = FetchType.LAZY)
    private List<EtatTache> etatTaches;
	
	
}
