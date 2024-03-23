package com.if5.todolist.services.implementations.systeme;

import com.if5.todolist.dtos.responses.systeme.FenetreDTO;
import com.if5.todolist.dtos.responses.systeme.ModuleFenetresDTO;
import com.if5.todolist.exceptions.ResourceNotFoundException;
import com.if5.todolist.models.entities.systeme.Fenetre;
import com.if5.todolist.models.entities.systeme.ModuleSysteme;
import com.if5.todolist.repositories.systeme.FenetreRepository;
import com.if5.todolist.repositories.systeme.ModuleSystemeRepository;
import com.if5.todolist.services.implementations.generics.CommonGenericCrudServiceImpl;
import com.if5.todolist.services.interfaces.systeme.FenetreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:32 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.implementations.systeme
 **/
@Service
public class FenetreServiceimpl extends CommonGenericCrudServiceImpl<FenetreRepository, Fenetre> implements FenetreService {

    private final ModuleSystemeRepository moduleRepository;

    public FenetreServiceimpl(FenetreRepository repository, ModuleSystemeRepository moduleRepository) {
        super(repository);
        this.moduleRepository = moduleRepository;
    }

    /**
     * @param fenetre object to update
     * @return la fenetre mise a jour
     * @implSpec mise a jour d'une fenetre enregistree
     */
    @Override
    @Transactional
    public FenetreDTO update(FenetreDTO fenetre) {
        Fenetre stored = repository.findById(fenetre.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Fenetre", "id", fenetre.getId()));
        stored.setFenetreFiltre(fenetre.getFenetreFiltre());
        stored.setFenetreEditable(fenetre.getFenetreEditable());
        stored.setFenetreImpression(fenetre.getFenetreImpression());
        stored.setDescription(fenetre.getDescription());
        stored.setLibelleFenetre(fenetre.getLibelleFenetre());
        stored.setIdObjet(fenetre.getIdObjet());
        stored.setUrl(fenetre.getUrl());
        stored.setModule(ModuleSysteme.builder().id(fenetre.getIdModule()).build());
        stored = repository.save(stored);
        return FenetreDTO.buildFromEntity(stored);
    }

    /**
     * @param libelle libelle de la fenetre
     * @return returns true if exists and false otherwise
     * @implSpec verifie si cette valeur existe deja en BD
     */
    @Override
    public boolean existsByLibelle(String libelle) {
        return repository.existsByLibelleFenetre(libelle);
    }

    /**
     * @param idModule identifiant du module
     * @return la liste des fenetres d'un module
     * @implSpec Liste des fenetres appartenant a un module
     */
    @Override
    public ModuleFenetresDTO findAllByModuleId(Long idModule) {
        ModuleSysteme module = moduleRepository.findById(idModule)
                .orElseThrow(() -> new ResourceNotFoundException("ModuleSysteme", "id", idModule));
        final List<Fenetre> fenetres = repository.findAllByModuleIdOrderByLibelleFenetre(idModule);
        return ModuleFenetresDTO.buildFromEntities(module, fenetres);
    }

    /**
     * @return liste des fenêtres autorisant les états
     * @implSpec liste des fenêtres autorisant les états
     */
    @Override
    public List<FenetreDTO> listeFenetresAutorisantEtat() {
        return FenetreDTO.buildFromEntityList(repository.listeFenetresAutorisantEtat());
    }
}
