package com.if5.todolist.models.entities;

import lombok.*;

import java.util.List;

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
