package com.if5.todolist.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.if5.todolist.models.enumerations.Sexe;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
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

    // bi-directional many-to-many association to Role
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany
    @JoinTable(name = "utilisateur_roles",
            joinColumns = {@JoinColumn(name = "utilisateurs_id")},
            inverseJoinColumns = {@JoinColumn(name = "roles_id")})
    private Set<Role> roles;
    
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


    public static final class UtilisateurBuilder {
        private Long id;
        private String userName;
        private String nom;
        private String prenom;
        private String email;
        private Sexe sexe;
        private String photos;
        private String password;
        private String villeDeResidence;
        private String paysOrigine;
        private String lieuDeNaissance;
        private LocalDate dateDeNaissance;
        private Set<Role> roles;
        private List<Attribution> listTacheUtilisateur;
        private String verificationCode;
        private boolean enabled;
        private boolean deleted;

        private UtilisateurBuilder() {
        }

        public static UtilisateurBuilder anUtilisateur() {
            return new UtilisateurBuilder();
        }

        public UtilisateurBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UtilisateurBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public UtilisateurBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public UtilisateurBuilder prenom(String prenom) {
            this.prenom = prenom;
            return this;
        }

        public UtilisateurBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UtilisateurBuilder sexe(Sexe sexe) {
            this.sexe = sexe;
            return this;
        }

        public UtilisateurBuilder photos(String photos) {
            this.photos = photos;
            return this;
        }

        public UtilisateurBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UtilisateurBuilder villeDeResidence(String villeDeResidence) {
            this.villeDeResidence = villeDeResidence;
            return this;
        }

        public UtilisateurBuilder paysOrigine(String paysOrigine) {
            this.paysOrigine = paysOrigine;
            return this;
        }

        public UtilisateurBuilder lieuDeNaissance(String lieuDeNaissance) {
            this.lieuDeNaissance = lieuDeNaissance;
            return this;
        }

        public UtilisateurBuilder dateDeNaissance(LocalDate dateDeNaissance) {
            this.dateDeNaissance = dateDeNaissance;
            return this;
        }

        public UtilisateurBuilder roles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public UtilisateurBuilder listTacheUtilisateur(List<Attribution> listTacheUtilisateur) {
            this.listTacheUtilisateur = listTacheUtilisateur;
            return this;
        }

        public UtilisateurBuilder verificationCode(String verificationCode) {
            this.verificationCode = verificationCode;
            return this;
        }

        public UtilisateurBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UtilisateurBuilder deleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Utilisateur build() {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setId(id);
            utilisateur.setUserName(userName);
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setEmail(email);
            utilisateur.setSexe(sexe);
            utilisateur.setPhotos(photos);
            utilisateur.setPassword(password);
            utilisateur.setVilleDeResidence(villeDeResidence);
            utilisateur.setPaysOrigine(paysOrigine);
            utilisateur.setLieuDeNaissance(lieuDeNaissance);
            utilisateur.setDateDeNaissance(dateDeNaissance);
            utilisateur.setRoles(roles);
            utilisateur.setListTacheUtilisateur(listTacheUtilisateur);
            utilisateur.setVerificationCode(verificationCode);
            utilisateur.setEnabled(enabled);
            utilisateur.setDeleted(deleted);
            return utilisateur;
        }
    }
}
