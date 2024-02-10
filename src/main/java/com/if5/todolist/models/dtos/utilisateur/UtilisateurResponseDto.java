package com.if5.todolist.models.dtos.utilisateur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.if5.todolist.models.dtos.role.RoleResponseDto;
import com.if5.todolist.models.entities.Utilisateur;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UtilisateurResponseDto {
    
    
    private Long id;

    private String userName;

    private String nom;

    private String prenom;


    private String villeDeResidence;

    private String paysOrigine;

    private String lieuDeNaissance;

    private LocalDate dateDeNaissance;

    private List<RoleResponseDto> roles ;

   // private List<TacheResponseDto> listTache ;
    
    //private List<TacheUtilisateur> listTacheUtilisateur;


    public static UtilisateurResponseDto buildUserDtoFromUser(Utilisateur user){

        return UtilisateurResponseDto.builder()
                     .id(user.getId())
                     .userName(user.getUserName())
                     .nom(user.getNom())
                     .prenom(user.getPrenom())
                     .villeDeResidence(user.getVilleDeResidence())
                     .paysOrigine(user.getPaysOrigine())
                     .lieuDeNaissance(user.getLieuDeNaissance())
                     .roles(RoleResponseDto.buildListDtoFromListRole(user.getRoles() ))
                    // .listTache(TacheResponseDto.buildListDtoFromListTache(user.getListTache()))
                     .build();
    }

    public static Utilisateur buildUserFromDto(UtilisateurResponseDto dto){

        return Utilisateur.builder()
                     .userName(dto.getUserName())
                     .nom(dto.getNom())
                     .prenom(dto.getPrenom())
                     .villeDeResidence(dto.getVilleDeResidence())
                     .paysOrigine(dto.getPaysOrigine())
                     .lieuDeNaissance(dto.getLieuDeNaissance())
                     .roles(dto.getRoles() == null? new ArrayList<>() : RoleResponseDto.buildListRoleFromListDto(dto.getRoles()))
                     .build();
    }

    public static List<UtilisateurResponseDto> buildListDtoFromListUtilisateur(List<Utilisateur> listUtilisateur){
        return listUtilisateur.stream().map(UtilisateurResponseDto::buildUserDtoFromUser).collect(Collectors.toList());
                     
    }

}
