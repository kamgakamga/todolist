package com.if5.todolist.models.dtos.tache;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.if5.todolist.models.entities.Tache;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TacheResponseDto {

    
    private Long id;

    private String libelle;

    private String description;
    
    private String statut;

    private double longitue;

    private double latitude;

    private String lieuDeRappel;
    
    private Date dateDebut;

    private Date dateFin;

    //private Utilisateur utilisateur;
  //  private TacheResponseDto tacheParente;
   // private List<TacheResponseDto> listSousTache ;


    public static TacheResponseDto buildTacheFromDto(Tache tache){

        return TacheResponseDto.builder()
                     .id(tache.getId() == null ? null :tache.getId())
                     .libelle(tache.getLibelle())
                     .description(tache.getDescription())
                     .statut(tache.getStatut())
                     .longitue(tache.getLongitue())
                     .latitude(tache.getLatitude())
                     .lieuDeRappel(tache.getLieuDeRappel())
                     .dateDebut(tache.getDateDebut())
                     .dateFin(tache.getDateFin())
                    // .utilisateur(tache.getUtilisateur())
                    // .tacheParente(tache.getTacheParente() == null ? null :TacheResponseDto.buildTacheFromDto(tache.getTacheParente()))
                    // .listSousTache(tache.getListSousTache() == null ? new ArrayList<>() : TacheResponseDto.buildListDtoFromListTache(tache.getListSousTache()))
                     .build();
    }
    
    
    
    public static TacheResponseDto buildStatDtoFromEntity(Tache tache){

        return TacheResponseDto.builder()
                     .id(tache.getId() == null ? null :tache.getId())
                     .libelle(tache.getLibelle())
                     .description(tache.getDescription())
                     .statut(tache.getStatut())
                     .longitue(tache.getLongitue())
                     .latitude(tache.getLatitude())
                     .lieuDeRappel(tache.getLieuDeRappel())
                     .dateDebut(tache.getDateDebut())
                     .dateFin(tache.getDateFin())
                     .build();
    }

    public static List<TacheResponseDto> buildListDtoFromListTache(List<Tache> listTache){
        return listTache.stream().map(TacheResponseDto::buildTacheFromDto).collect(Collectors.toList());
                     
    }
    
    public static List<TacheResponseDto> buildListStatDtoFromListTache(List<Tache> listTache){
        return listTache.stream().map(TacheResponseDto::buildStatDtoFromEntity).collect(Collectors.toList());
                     
    }
    
	/*
	 * public static List<TacheResponseDto>
	 * buildListTaheFromListDto(List<TacheResponseDto> listDto){ return
	 * listDto.stream().map(TacheResponseDto::buildTacheFromDto).collect(Collectors.
	 * toList());
	 * 
	 * }
	 */
    
}
