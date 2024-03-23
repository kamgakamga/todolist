package com.if5.todolist.dtos.responses.application.tache;

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
