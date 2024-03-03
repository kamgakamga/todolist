package com.if5.todolist.models.dtos.tache;

import com.if5.todolist.models.entities.Attribution;
import com.if5.todolist.models.entities.Sprint;
import com.if5.todolist.models.entities.Tache;
import com.if5.todolist.models.entities.Utilisateur;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacheRequestDto {
	private Long id;
    private String libelle;

    private String description;
    
    private Date dateDebut;
    
    private String statut;

    private Date dateFin;
    
    private  Attribution responsableTache;

    private Long sprintId;

    private Long utilisateur;

    private Long tacheParente;


    public static Tache builTacheFromDto(TacheRequestDto dto, Utilisateur utilisateur,Tache tacheParente, Sprint sprint) {

        return Tache.builder()
                 .libelle(dto.getLibelle())
                 .description(dto.getDescription())
                 .dateDebut(dto.getDateDebut())
                 .dateFin(dto.getDateFin())
                 .statut(dto.getStatut())
                // .utilisateur(utilisateur)
                 .sprint(sprint)
                 .tacheParente(tacheParente)
                .build();
        
    }
    

    public static Tache builUpdateTache(TacheRequestDto dto,Tache tache) {
    	
    	tache.setLibelle(dto.getLibelle());
    	tache.setDescription(dto.getDescription());
    	tache.setDateDebut(dto.getDateDebut());
    	tache.setDateFin(dto.getDateFin());
        return tache;
        
    }
}
