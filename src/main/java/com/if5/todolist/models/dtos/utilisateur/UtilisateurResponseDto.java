package com.if5.todolist.models.dtos.utilisateur;

import com.if5.todolist.models.dtos.role.RoleResponseDto;
import com.if5.todolist.models.entities.Utilisateur;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
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


    public static UtilisateurResponseDto buildUserDtoFromUser(Utilisateur user){

        return UtilisateurResponseDto.builder()
                     .id(user.getId())
                     .userName(user.getUserName())
                     .nom(user.getNom())
                     .prenom(user.getPrenom())
                     .villeDeResidence(user.getVilleDeResidence())
                     .paysOrigine(user.getPaysOrigine())
                     .lieuDeNaissance(user.getLieuDeNaissance())
                     .roles(RoleResponseDto.buildListDtoFromListRole(user.getRoles()))
                     .build();
    }

    public static Utilisateur buildUserFromDto(UtilisateurResponseDto dto){

        return Utilisateur.UtilisateurBuilder.anUtilisateur()
                     .userName(dto.getUserName())
                     .nom(dto.getNom())
                     .prenom(dto.getPrenom())
                     .villeDeResidence(dto.getVilleDeResidence())
                     .paysOrigine(dto.getPaysOrigine())
                     .lieuDeNaissance(dto.getLieuDeNaissance())
                     .roles(dto.getRoles() == null? new HashSet<>() : RoleResponseDto.buildListRoleFromListDto(dto.getRoles()))
                     .build();
    }

    public static List<UtilisateurResponseDto> buildListDtoFromListUtilisateur(List<Utilisateur> listUtilisateur){
        return listUtilisateur.stream().map(UtilisateurResponseDto::buildUserDtoFromUser).collect(Collectors.toList());
    }
    public static Page<UtilisateurResponseDto> buildPageDtoFromPageEntity(Page<Utilisateur> pageEntityList) {
        return Objects.isNull(pageEntityList) ? Page.empty() : pageEntityList.map(UtilisateurResponseDto::buildUserDtoFromUser);
    }
}
