package com.if5.todolist.models.entities.applications;

import com.if5.todolist.models.entities.AuditModel;
import com.if5.todolist.models.entities.Projet;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 1:25 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.entities.applications
 **/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "commentaire")
public class Commentaire extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    @ManyToOne(fetch = FetchType.LAZY)
    private Projet projet;

    @OneToMany (fetch = FetchType.LAZY, targetEntity = Commentaire.class, mappedBy = "commentaire")
    private List<Commentaire> commentaires;

    @ManyToOne (fetch = FetchType.LAZY, targetEntity = Commentaire.class)
    private Commentaire commentaire;

    public static final class CommentaireBuilder {
        private Long id;
        private String libelle;
        private Projet projet;

        private CommentaireBuilder() {
        }

        public static CommentaireBuilder aCommentaire() {
            return new CommentaireBuilder();
        }

        public CommentaireBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public CommentaireBuilder libelle(String libelle) {
            this.libelle = libelle;
            return this;
        }

        public CommentaireBuilder projet(Projet projet) {
            this.projet = projet;
            return this;
        }

        public Commentaire build() {
            Commentaire commentaire = new Commentaire();
            commentaire.setId(id);
            commentaire.setLibelle(libelle);
            commentaire.setProjet(projet);
            return commentaire;
        }
    }
}
