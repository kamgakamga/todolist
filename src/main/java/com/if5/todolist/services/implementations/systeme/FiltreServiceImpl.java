package com.if5.todolist.services.implementations.systeme;

import com.if5.todolist.exceptions.ResourceNotFoundException;
import com.if5.todolist.models.entities.systeme.Filtre;
import com.if5.todolist.repositories.systeme.FiltreRepository;
import com.if5.todolist.services.implementations.generics.CommonGenericCrudServiceImpl;
import com.if5.todolist.services.interfaces.systeme.FiltreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:35 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.implementations.systeme
 **/
@Service
public class FiltreServiceImpl extends CommonGenericCrudServiceImpl<FiltreRepository, Filtre> implements FiltreService {

    private final FiltreRepository filtreRepository;

    public FiltreServiceImpl(FiltreRepository repository) {
        super(repository);
        filtreRepository = repository;
    }

    /**
     * @param libelle libelle a verifier
     * @return true si ca existe et false le cas echeant
     * @implSpec verifie si un libelle existe deja en BD
     */
    @Override
    public boolean existsByLibelle(String libelle) {
        return filtreRepository.existsByLibelle(libelle);
    }

    /**
     * @param filtre objet a modifier
     * @return La valeur mise a jour
     * @implSpec Effectue la mise a jour d'un filtre
     */
    @Override
    @Transactional
    public Filtre update(Filtre filtre) {
        Filtre stored = filtreRepository.findById(filtre.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Filtre", "id", filtre.getId()));
        stored.setType(filtre.getType());
        stored.setLibelle(filtre.getLibelle());
        stored.setLabel(filtre.getLabel());
        stored.setDescription(filtre.getDescription());
        stored.setTypeSelection(filtre.getTypeSelection());
        stored.setSelectionEnPlage(filtre.getSelectionEnPlage());
        stored.setLabelFinPlage(filtre.getLabelFinPlage());
        stored.setSourceDeDonnees(filtre.getSourceDeDonnees());
        stored.setNomParametreEtat(filtre.getNomParametreEtat());
        stored.setNomParametreEtatFinPlage(filtre.getNomParametreEtatFinPlage());
        return filtreRepository.save(stored);
    }

    /**
     * @param idEtat ID de l'etat imprimable
     * @return La liste de tous les filtres disponibles pour un etat imprimable
     * @implSpec Recherche tous les filtres disponibles pour un etat imprimable
     */
    @Override
    public List<Filtre> listeFiltresDispoPourEtat(Long idEtat) {
        return filtreRepository.listeFiltresDispoPourEtat(idEtat);
    }

    @Override
    public List<Filtre> listeFiltres() {
        return filtreRepository.findAll();
    }
}
