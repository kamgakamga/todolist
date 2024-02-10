package com.if5.todolist.models.dtos.tache;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NombreRequestDto {
	
	    private Date dateDebut;

	    private Date dateFin;

	    public String statut;
	    
	    public Long sprint;
	    
	    private Long reponsableTacheId;

}

