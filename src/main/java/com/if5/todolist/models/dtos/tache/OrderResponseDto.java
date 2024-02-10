package com.if5.todolist.models.dtos.tache;

import com.if5.todolist.models.enumerations.ResponseStatus;

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
public class OrderResponseDto {

	private String message;
	private ResponseStatus statut;
	private Object donnee;
}
