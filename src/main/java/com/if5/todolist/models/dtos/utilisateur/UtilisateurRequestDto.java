package com.if5.todolist.models.dtos.utilisateur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.if5.todolist.models.entities.Attribution;
import com.if5.todolist.models.entities.Role;
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
public class UtilisateurRequestDto {
	
    private Long id;
    
    @NotNull
    @Column(unique = true)
    private String userName;

    private String nom;

    private String prenom;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;
    
    private String photos;

    private String confirmationPassword;

    private String villeDeResidence;

    private String paysOrigine;

    private String lieuDeNaissance;

    private LocalDate dateDeNaissance;

    private Set<Long> roles ;
   
    private List<Long> listTacheUtilisateur;
    


    public static Utilisateur buildUserFromDto(UtilisateurRequestDto dto, Set<Role> listRole, List<Attribution> listTacheUtilisateur){

        return Utilisateur.UtilisateurBuilder.anUtilisateur()
                     .userName(dto.getUserName())
                     .nom(dto.getNom())
                     .prenom(dto.getPrenom())
                     .email(dto.getEmail())
                     .password(dto.getPassword())
                     .photos(dto.getPhotos())
                     .villeDeResidence(dto.getVilleDeResidence())
                     .paysOrigine(dto.getPaysOrigine())
                     .lieuDeNaissance(dto.getLieuDeNaissance())
                     .dateDeNaissance(dto.getDateDeNaissance())
                     .roles(listRole == null ? new HashSet<>() : listRole)
                     .listTacheUtilisateur(listTacheUtilisateur == null? new ArrayList<>() : listTacheUtilisateur )
                   //s  .listTache(listTache == null ? new ArrayList<>() : listTache)
                     .build();
    }
    
    public static Utilisateur buildUpdateUser(UtilisateurRequestDto dto, Utilisateur u ){
                    u.setUserName(dto.getUserName());
                    u.setNom(dto.getNom());
                    u.setPrenom(dto.getPrenom());
                    u.setEmail(dto.getEmail());
                    u.setPassword(dto.getPassword());
                    u.setVilleDeResidence(dto.getVilleDeResidence());
                    u.setPaysOrigine(dto.getPaysOrigine());
                    u.setLieuDeNaissance(dto.getLieuDeNaissance());
                    u.setDateDeNaissance(dto.getDateDeNaissance());
             return u;
    	
    }
}
