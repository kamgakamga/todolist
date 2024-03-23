package com.if5.todolist.dtos.requets.application.tache;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class PeriodRequestDto {
	
	 
    private Date dateDebut;
    
    private String statut;

    private Date dateFin;

}
