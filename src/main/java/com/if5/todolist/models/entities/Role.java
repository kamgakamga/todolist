package com.if5.todolist.models.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Role extends AuditModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @ManyToMany(fetch = FetchType.LAZY,
	mappedBy = "roles",
	cascade = {CascadeType.MERGE,
			CascadeType.PERSIST
			})
    @JsonIgnore
    private List<Utilisateur> utilisateurs;
    
  
    public void addUtilisateur(Utilisateur utilisateur) {
      this.utilisateurs.add(utilisateur);
      utilisateur.getRoles().add(this);
    }
    
    public void removeTag(long utilisateurId) {
    	Utilisateur utilisateur = this.utilisateurs.stream().filter(t -> t.getId() == utilisateurId).findFirst().orElse(null);
      if (utilisateur != null) {
        this.utilisateurs.remove(utilisateur);
        utilisateur.getRoles().remove(this);
      }
}
}