package com.if5.todolist.models.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistique {
	
	private int nombreTache;
	private double pourcentage;
	private List<Tache> listTache;
	
}
