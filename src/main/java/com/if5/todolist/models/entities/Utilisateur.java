package com.if5.todolist.models.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.if5.todolist.models.enumerations.Sexe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,  property="id")
public class Utilisateur extends AuditModel  {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    private String nom;

    private String prenom;

    @Column(nullable = false, unique = true, length = 50)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 5)
    private Sexe sexe;
    
    @Column(nullable = true, length = 64)
    private String photos;

    private String password;

    private String villeDeResidence;

    private String paysOrigine;

    private String lieuDeNaissance;

    private LocalDate dateDeNaissance;
    
    @ManyToMany(fetch = FetchType.EAGER,
    		cascade = {CascadeType.MERGE,
    				CascadeType.PERSIST
    				})
    private List<Role> roles = new ArrayList<>();
    
    @OneToMany(mappedBy = "utilisateur", fetch = FetchType.LAZY)
    private List<Attribution> listTacheUtilisateur;
    
    @Column(name = "verification_code", length = 64)
    private String verificationCode;
     
    private boolean enabled;
    
    private boolean deleted= Boolean.FALSE;
    

    @Transient
    public String getPhotosImagePath() {
        if (photos == null || id == null) return null;
         
        return "/user-photos/" + id + "/" + photos;
    }


    public void addRole(Role role) {
        this.roles.add(role);
        role.getUtilisateurs().add(this);
      }
      
      public void removeRole(long roleId) {
        Role role = this.roles.stream().filter(r -> r.getId() == roleId).findFirst().orElse(null);
        if (role != null) {
          this.roles.remove(role);
          role.getUtilisateurs().remove(this);
        }
}
}
