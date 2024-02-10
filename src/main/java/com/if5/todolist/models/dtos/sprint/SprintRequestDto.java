package com.if5.todolist.models.dtos.sprint;

import java.time.LocalDateTime;
import java.util.List;

import com.if5.todolist.models.entities.EtatTache;
import com.if5.todolist.models.entities.Projet;
import com.if5.todolist.models.entities.Sprint;
import com.if5.todolist.models.entities.Tache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SprintRequestDto {
	
	private String libelle;
	
	private String description;
	
	private Long projetId;
	
    private LocalDateTime dateDebut;
	
	private LocalDateTime dateFin;
	
	private List<Long> tachesId ;

    private List<Long> etatId;
	
	
	public static  Sprint buildEntityFromDto(SprintRequestDto s,Projet p, List<Tache> taches, List<EtatTache> e) {
		return Sprint.builder()
				   .libelle(s.getLibelle())
				   .description(s.getDescription())
				   .dateDebut(s.getDateDebut())
				   .dateFin(s.getDateFin())
				   .projet(p)
				   .taches(taches)
				   .etatTaches(e)
				   .build();
	}

}
