package com.if5.todolist.models.dtos.sprint;

import com.if5.todolist.models.dtos.etatTache.EtatTacheResponseDto;
import com.if5.todolist.models.dtos.projet.ProjetResponseDto;
import com.if5.todolist.models.entities.Sprint;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SprintResponseDto {
	
	private Long id;

    private String libelle;
	
	private String description;
	
    private LocalDateTime dateDebut;
	
	private LocalDateTime dateFin;
	
	private ProjetResponseDto projet;
	
	//private List<TacheResponseDto> taches = new ArrayList<>();
	
	private List<EtatTacheResponseDto> etatTaches ;
	
	
	
	public static  SprintResponseDto buildResponseDtoFromEntity(Sprint s) {
		return SprintResponseDto.builder()
				   .id(s.getId())
				   .libelle(s.getLibelle())
				   .description(s.getDescription())
				   .dateDebut(s.getDateDebut())
				   .dateFin(s.getDateFin())
				  // .taches(TacheResponseDto.buildListDtoFromListTache(s.getTaches()))
				   .etatTaches(EtatTacheResponseDto.buildListDtoFromListEtatTache(s.getEtatTaches()))
				   //.projet(ProjetResponseDto.buildDtoFromEntity(s.getProjet()))
				   .build();
	}
	
	 public static List<SprintResponseDto> buildListDtoFromListSprint(List<Sprint> listSprint){
	        return listSprint.stream().map(SprintResponseDto::buildResponseDtoFromEntity).collect(Collectors.toList());                
	    }
}
