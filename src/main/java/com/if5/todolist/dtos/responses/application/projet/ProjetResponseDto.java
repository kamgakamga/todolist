package com.if5.todolist.dtos.responses.application.projet;

import com.if5.todolist.dtos.responses.EtatTacheResponseDto;
import com.if5.todolist.dtos.responses.UtilisateurResponseDto;
import com.if5.todolist.models.entities.Projet;
import lombok.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
	private boolean estDemarrer;

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
				.estDemarrer(p.isEstDemarrer())
				//  .utilisateurs(p.getUtilisateurs()== null? new ArrayList<>():UtilisateurResponseDto.buildListDtoFromListUtilisateur(p.getUtilisateurs()))
				.responsable(p.getResponsable() == null ? null :UtilisateurResponseDto.buildUserDtoFromUser(p.getResponsable()))
				.etatTaches(p.getEtatTaches()== null? new ArrayList<>():EtatTacheResponseDto.buildListDtoFromListEtatTache(p.getEtatTaches()))
				// .sprint(p.getSprints()== null? new ArrayList<>():SprintResponseDto.buildListDtoFromListSprint(p.getSprints()))
				.build();
	}

	public static List<ProjetResponseDto> buildListDtoFromListProjet(List<Projet> listProjet){
		return listProjet.stream().map(ProjetResponseDto::buildDtoFromEntity).collect(Collectors.toList());

	}

	public static Page<ProjetResponseDto> buildPageDtoFromPageEntity(Page<Projet> pageEntityList) {
		return Objects.isNull(pageEntityList) ? Page.empty() : pageEntityList.map(ProjetResponseDto::buildDtoFromEntity);
	}

}
