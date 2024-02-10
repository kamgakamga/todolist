package com.if5.todolist.models.dtos.projet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.if5.todolist.models.entities.EtatTache;
import com.if5.todolist.models.entities.Projet;
import com.if5.todolist.models.entities.Utilisateur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.exception.DataException;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProjetRequestDto {
	private Long id;
	private String nomProjet;
	
	private String description;
	//@JsonFormat(pattern = "yyyy-MM-dd" , shape = JsonFormat.Shape.STRING)
    private Date dateDebut;
//	@JsonFormat(pattern = "yyyy-MM-dd" , shape = JsonFormat.Shape.STRING)
	private Date dateFin;
	
	private List<Long> utilisateurs;
	
	private Long responsableId;
	
	 private List<Long> etatId;
	
	
	public static  Projet buildEntityFromDto(ProjetRequestDto p, List<Utilisateur> utilisateur, List<EtatTache> e,Utilisateur u) {
		return Projet.builder()
				   .nomProjet(p.getNomProjet())
				   .utilisateurs(utilisateur == null ? new ArrayList<>() : utilisateur )
				   .description(p.getDescription())
				   .dateDebut(p.getDateDebut())
				   .dateFin(p.getDateFin())
				   .responsable(u)
				   .etatTaches(e == null ? new ArrayList<>() : e)
				   .build();
	}
	
	public static  Projet buildUpdate(ProjetRequestDto p,Projet projet) {
				projet.setNomProjet(p.getNomProjet());
		        projet.setDescription(p.getDescription());
		        projet.setDateDebut(p.getDateDebut());
		        projet.setDateFin(p.getDateFin());
				return projet;
	}

}
