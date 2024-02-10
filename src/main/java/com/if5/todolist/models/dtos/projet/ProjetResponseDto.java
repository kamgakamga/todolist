package com.if5.todolist.models.dtos.projet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.if5.todolist.models.dtos.etatTache.EtatTacheResponseDto;
import com.if5.todolist.models.dtos.utilisateur.UtilisateurResponseDto;

import com.if5.todolist.models.entities.Projet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProjetResponseDto {

	private Long id; 
	
	private String nomProjet;
	
	private String description;
	
    private Date dateDebut;
	
	private Date dateFin;

	private UtilisateurResponseDto responsable;
	
	//private List<SprintResponseDto> sprint;
	
	//private List<UtilisateurResponseDto> utilisateurs;
	
	private List<EtatTacheResponseDto> etatTaches;

	
	public static  ProjetResponseDto buildDtoFromEntity(Projet p) {
		return ProjetResponseDto.builder()
				   .id(p.getId())
				   .nomProjet(p.getNomProjet())
				   .description(p.getDescription())
				   .dateDebut(p.getDateDebut())
				   .dateFin(p.getDateFin())
				 //  .utilisateurs(p.getUtilisateurs()== null? new ArrayList<>():UtilisateurResponseDto.buildListDtoFromListUtilisateur(p.getUtilisateurs()))
				   .responsable(p.getResponsable() == null ? null :UtilisateurResponseDto.buildUserDtoFromUser(p.getResponsable()))
				   .etatTaches(p.getEtatTaches()== null? new ArrayList<>():EtatTacheResponseDto.buildListDtoFromListEtatTache(p.getEtatTaches()))
				  // .sprint(p.getSprints()== null? new ArrayList<>():SprintResponseDto.buildListDtoFromListSprint(p.getSprints()))
				   .build();
	}
	
	 public static List<ProjetResponseDto> buildListDtoFromListProjet(List<Projet> listProjet){
	        return listProjet.stream().map(ProjetResponseDto::buildDtoFromEntity).collect(Collectors.toList());
	                     
	    }
}
