package com.if5.todolist.models.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Role extends AuditModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom", length = 100)
    private String nom;

    @Column(name = "description", length = 5000)
    private String description;

    // bi-directional many-to-many association to User
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Utilisateur> utilisateurs;
    
  
    public void addUtilisateur(Utilisateur utilisateur) {
      this.utilisateurs.add(utilisateur);
      utilisateur.getRoles().add(this);
    }
    
    public void removeTag(long utilisateurId) {
    	Utilisateur utilisateur = this.utilisateurs.stream().filter(u -> u.getId() == utilisateurId).findFirst().orElse(null);
      if (utilisateur != null) {
        this.utilisateurs.remove(utilisateur);
        utilisateur.getRoles().remove(this);
      }
}


    public static final class RoleBuilder {
        private Long id;
        private String nom;
        private String description;
        private Set<Utilisateur> utilisateurs;

        private RoleBuilder() {
        }

        public static RoleBuilder aRole() {
            return new RoleBuilder();
        }

        public RoleBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public RoleBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public RoleBuilder description(String description) {
            this.description = description;
            return this;
        }

        public RoleBuilder utilisateurs(Set<Utilisateur> utilisateurs) {
            this.utilisateurs = utilisateurs;
            return this;
        }

        public Role build() {
            Role role = new Role();
            role.setId(id);
            role.setNom(nom);
            role.setDescription(description);
            role.setUtilisateurs(utilisateurs);
            return role;
        }
    }
}
