package com.if5.todolist.models.dtos.tache;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PeriodRequestDto {
	
	 
    private Date dateDebut;
    
    private String statut;

    private Date dateFin;

}
