package com.if5.todolist.models.dtos.tache;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CalculNombreHeureResponseDto {

	private String message;
	private long temps;
	private Object donnee;
	
}
