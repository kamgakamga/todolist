package com.if5.todolist.models.dtos.tache;

import java.util.List;
import java.util.stream.Collectors;

import com.if5.todolist.models.entities.Statistique;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatistiqueResponseDto {

	private int nombreTache;
	private double pourcentage;
	private List<TacheResponseDto> listTache;
	
	
	public static StatistiqueResponseDto buildDtoFromEntity(Statistique s) {
	
		return StatistiqueResponseDto.builder()
				.nombreTache(s.getNombreTache())
				.pourcentage(s.getPourcentage())
				.listTache(s.getListTache() ==null ? null :TacheResponseDto.buildListStatDtoFromListTache(s.getListTache()))
				.build();
	}
	
	 public static List<StatistiqueResponseDto> buildListDtoFromListTache(List<Statistique> listStat){
	        return listStat.stream().map(StatistiqueResponseDto::buildDtoFromEntity).collect(Collectors.toList());
	                     
	    }
	
}
