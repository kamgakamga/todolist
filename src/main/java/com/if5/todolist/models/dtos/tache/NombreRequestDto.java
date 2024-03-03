package com.if5.todolist.models.dtos.tache;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

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

