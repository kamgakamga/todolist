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

	private boolean estDemarrer;

    private LocalDateTime dateDebut;
	
	private LocalDateTime dateFin;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Projet projet;
	
	@OneToMany(mappedBy = "sprint" , fetch = FetchType.LAZY)
	private List<Tache> taches ;
	
	@ManyToMany(fetch = FetchType.LAZY)
    private List<EtatTache> etatTaches;


	public static final class SprintBuilder {
		private Long id;
		private String libelle;
		private String description;
		private boolean estDemarrer;
		private LocalDateTime dateDebut;
		private LocalDateTime dateFin;
		private Projet projet;
		private List<Tache> taches;
		private List<EtatTache> etatTaches;

		private SprintBuilder() {
		}

		public static SprintBuilder aSprint() {
			return new SprintBuilder();
		}

		public SprintBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public SprintBuilder libelle(String libelle) {
			this.libelle = libelle;
			return this;
		}

		public SprintBuilder description(String description) {
			this.description = description;
			return this;
		}

		public SprintBuilder estDemarrer(boolean estDemarrer) {
			this.estDemarrer = estDemarrer;
			return this;
		}

		public SprintBuilder dateDebut(LocalDateTime dateDebut) {
			this.dateDebut = dateDebut;
			return this;
		}

		public SprintBuilder dateFin(LocalDateTime dateFin) {
			this.dateFin = dateFin;
			return this;
		}

		public SprintBuilder projet(Projet projet) {
			this.projet = projet;
			return this;
		}

		public SprintBuilder taches(List<Tache> taches) {
			this.taches = taches;
			return this;
		}

		public SprintBuilder etatTaches(List<EtatTache> etatTaches) {
			this.etatTaches = etatTaches;
			return this;
		}

		public Sprint build() {
			Sprint sprint = new Sprint();
			sprint.setId(id);
			sprint.setLibelle(libelle);
			sprint.setDescription(description);
			sprint.setEstDemarrer(estDemarrer);
			sprint.setDateDebut(dateDebut);
			sprint.setDateFin(dateFin);
			sprint.setProjet(projet);
			sprint.setTaches(taches);
			sprint.setEtatTaches(etatTaches);
			return sprint;
		}
	}
}
