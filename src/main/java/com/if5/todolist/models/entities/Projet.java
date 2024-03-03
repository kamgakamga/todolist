package com.if5.todolist.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Projet extends AuditModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	@Column(unique =  true, nullable = false)
	private String nomProjet;
	
	private String description;

	@JsonFormat(pattern = "yyyy-MM-dd" , shape = JsonFormat.Shape.STRING)
	private Date dateDebut;

	@JsonFormat(pattern = "yyyy-MM-dd" , shape = JsonFormat.Shape.STRING)
	private Date dateFin;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Utilisateur> utilisateurs;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Utilisateur responsable;
	
	@OneToMany(mappedBy = "projet" ,fetch = FetchType.LAZY)
	private List<Sprint> sprints ;
	
	@ManyToMany(fetch = FetchType.LAZY)
	private List<EtatTache> etatTaches;


}
