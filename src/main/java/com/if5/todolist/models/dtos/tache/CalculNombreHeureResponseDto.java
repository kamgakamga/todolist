package com.if5.todolist.models.dtos.tache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
