package com.if5.todolist.models.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property="id")
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
