package com.if5.todolist.models.dtos.etatTache;

import java.util.List;
import java.util.stream.Collectors;

import com.if5.todolist.models.entities.EtatTache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EtatTacheResponseDto {

	private Long id;
	
	private String libelle;
	
    //private SprintResponseDto sprint;
	
   // private ProjetResponseDto projet;
	
	

	public static  EtatTacheResponseDto buildDtoFromEntity(EtatTache e) {
		return EtatTacheResponseDto.builder()
				   .id(e.getId())
				   .libelle(e.getLibelle())
				  // .projet(ProjetResponseDto.buildDtoFromEntity(e.getProjet()))
				 //  .sprint(SprintResponseDto.buildDtoFromEntity(e.getSprint()))
				   .build();
	}
	
	 public static List<EtatTacheResponseDto> buildListDtoFromListEtatTache(List<EtatTache> listEtat){
	        return listEtat.stream().map(EtatTacheResponseDto::buildDtoFromEntity).collect(Collectors.toList());
	                     
	    }
	
}
