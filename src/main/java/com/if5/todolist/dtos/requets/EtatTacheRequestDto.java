package com.if5.todolist.dtos.requets;

import com.if5.todolist.models.entities.EtatTache;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class EtatTacheRequestDto {
	

	private String libelle;
	
	
	public static  EtatTache buildEntityFromDto(EtatTacheRequestDto e) {
		return EtatTache.builder()
				   .libelle(e.getLibelle())
				   .build();
	}
}
