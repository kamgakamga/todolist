package com.if5.todolist.services.implementations.systeme;

import com.if5.todolist.exceptions.ResourceNotFoundException;
import com.if5.todolist.models.dtos.requests.systeme.AddFiltresEtatDTO;
import com.if5.todolist.models.dtos.responses.systeme.ListeFiltreEtatDTO;
import com.if5.todolist.models.entities.systeme.EtatImprimable;
import com.if5.todolist.models.entities.systeme.Filtre;
import com.if5.todolist.models.entities.systeme.FiltreEtatImprimable;
import com.if5.todolist.repositories.systeme.EtatImprimableRepository;
import com.if5.todolist.repositories.systeme.FiltreEtatImprimableRepository;
import com.if5.todolist.repositories.systeme.FiltreRepository;
import com.if5.todolist.services.implementations.generics.CommonGenericCrudServiceImpl;
import com.if5.todolist.services.interfaces.systeme.FiltreEtatImprimableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:42 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.implementations.systeme
 **/
@Service
public class FiltreEtatImprimableServiceImpl extends CommonGenericCrudServiceImpl<FiltreEtatImprimableRepository, FiltreEtatImprimable> implements FiltreEtatImprimableService {

    private final FiltreRepository filtreRepository;
    private final FiltreEtatImprimableRepository filtreEtatRepository;
    private final EtatImprimableRepository etatImprimableRepository;

    public FiltreEtatImprimableServiceImpl(FiltreRepository filtreRepository, FiltreEtatImprimableRepository repository,
                                           EtatImprimableRepository etatImprimableRepository) {
        super(repository);
        this.filtreRepository = filtreRepository;
        filtreEtatRepository = repository;
        this.etatImprimableRepository = etatImprimableRepository;
    }

    /**
     * @param idEtat Id de letat
     * @return com.nfl.smartschool.dto.parametre.output.ListeFiltreEtatDTO liste des filtres de cet etat
     * @implSpec recherche la liste des filtres affectes a une etat
     */
    @Override
    public ListeFiltreEtatDTO findByIdEtat(Long idEtat) {
        EtatImprimable etat = etatImprimableRepository.findById(idEtat)
                .orElseThrow(() -> new ResourceNotFoundException("EtatImprimable", "Id", idEtat));
        final List<FiltreEtatImprimable> etatsFiltres = filtreEtatRepository.findAllByEtatImprimableId(idEtat);
        return ListeFiltreEtatDTO.buildFromEntities(etat, etatsFiltres);
    }

    /**
     * @param filtresEtat objet contenant l'Id de letat et les filtres a lui ajouter
     * @return com.nfl.smartschool.dto.systeme.etat.AddFiltresEtatDTO liste des filtres a ajouter a cet etat
     * @implSpec Ajoute des filtres a un etat
     */
    @Override
    @Transactional
    public ListeFiltreEtatDTO ajouterFiltresEtat(AddFiltresEtatDTO filtresEtat) {
        final List<FiltreEtatImprimable> filtreEtatSaved = new ArrayList<>();
        final EtatImprimable etat = etatImprimableRepository.findById(filtresEtat.getIdEtat())
                .orElseThrow(() -> new ResourceNotFoundException("EtatImprimable", "Id", filtresEtat.getIdEtat()));
        filtresEtat.getFiltres().forEach(filtreDTO -> {
            if (filtreRepository.existsById(filtreDTO.getId())) {
                FiltreEtatImprimable fet = FiltreEtatImprimable.builder()
                        .etatImprimable(etat)
                        .filtre(Filtre.builder().id(filtreDTO.getId()).build())
                        .build();
                filtreEtatSaved.add(filtreEtatRepository.save(fet));
            }
        });
        return ListeFiltreEtatDTO.buildFromEntities(etat, filtreEtatSaved);
    }

    /**
     * @param updateFiltreEtat objet contenant les valeurs a modifier
     * @return FiltreEtatImprimable Objet mis a jour
     * @implSpec Mets a jour un FiltreEtatImprimable
     */
    @Override
    @Transactional
    public FiltreEtatImprimable update(FiltreEtatImprimable updateFiltreEtat) {
        FiltreEtatImprimable fet = filtreEtatRepository.findById(updateFiltreEtat.getId())
                .orElseThrow(() -> new ResourceNotFoundException("FiltreEtatImprimable", "Id", updateFiltreEtat.getId()));
        fet.setFiltre(updateFiltreEtat.getFiltre());
        fet.setEtatImprimable(updateFiltreEtat.getEtatImprimable());
        return filtreEtatRepository.save(fet);
    }
}

