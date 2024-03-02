package com.if5.todolist.services.implementations.systeme;

import com.if5.todolist.exceptions.ResourceNotFoundException;
import com.if5.todolist.helpers.PrintEtatHelper;
import com.if5.todolist.models.dtos.requests.systeme.AddEtatImprimableParamDTO;
import com.if5.todolist.models.dtos.requests.systeme.EtatImprimableParamDTO;
import com.if5.todolist.models.dtos.requests.systeme.ParamImpressionDTO;
import com.if5.todolist.models.dtos.requests.systeme.UpdateEtatImprimableParamDTO;
import com.if5.todolist.models.dtos.responses.systeme.EtatActionDTO;
import com.if5.todolist.models.entities.Role;
import com.if5.todolist.models.entities.systeme.ActionSysteme;
import com.if5.todolist.models.entities.systeme.EtatImprimable;
import com.if5.todolist.models.entities.systeme.Fenetre;
import com.if5.todolist.models.entities.systeme.RoleAction;
import com.if5.todolist.repositories.RoleRepository;
import com.if5.todolist.repositories.systeme.*;
import com.if5.todolist.services.implementations.generics.CommonGenericCrudServiceImpl;
import com.if5.todolist.services.interfaces.systeme.EtatImprimableService;
import com.if5.todolist.utils.GeneralUtils;
import net.sf.jasperreports.engine.JRException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static com.if5.todolist.utils.StringsUtils.*;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 1:57 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.implementations.systeme
 **/
@Service
public class EtatImprimableServiceImpl  extends CommonGenericCrudServiceImpl<EtatImprimableRepository, EtatImprimable> implements EtatImprimableService {

    private final FenetreRepository fenetreRepository;
    private final FiltreEtatImprimableRepository filtreEtatRepository;
    private final RoleRepository roleRepository;
    private final ActionSystemeRepository actionSystemeRepository;
    private final RoleActionRepository roleActionRepository;

    public EtatImprimableServiceImpl(FenetreRepository fenetreRepository,
                                     EtatImprimableRepository repository,
                                     FiltreEtatImprimableRepository filtreEtatRepository,
                                     RoleRepository roleRepository,
                                     ActionSystemeRepository actionSystemeRepository,
                                     RoleActionRepository roleActionRepository) {
        super(repository);
        this.fenetreRepository = fenetreRepository;
        this.filtreEtatRepository = filtreEtatRepository;
        this.roleRepository = roleRepository;
        this.actionSystemeRepository = actionSystemeRepository;
        this.roleActionRepository = roleActionRepository;
    }

    /**
     * @param pageable parametres de pagination
     * @return La liste des {@link EtatActionDTO}
     * @implSpec retourne la liste de tous les etats avec pour chacun l'action a laquelle il est lie
     */
    @Override
    public Page<EtatActionDTO> findByAllEtatAction(Pageable pageable) {

        Page<EtatActionDTO> etatAction = repository.findByAllEtatAction(pageable);
        etatAction.getContent();

        return etatAction;
    }

    @Override
    public Page<EtatImprimable> findAll(Pageable pageable) {
        return repository.listeEtats(pageable);
    }

    /**
     * @param etatDtoToSave liste des etats a enregistrer
     * @return La liste des etats enregistres
     * @apiNote Energistre plusieurs etats pour une fenetre
     */
    @Override
    @Transactional
    public List<EtatImprimable> saveManyEtats(AddEtatImprimableParamDTO etatDtoToSave) {
        Fenetre fenetre = fenetreRepository.findById(etatDtoToSave.getIdFenetre())
                .orElseThrow(() -> new ResourceNotFoundException("Fenetre", "id", etatDtoToSave.getIdFenetre()));
        List<EtatImprimable> saved = new ArrayList<>();
        List<Role> roleList = roleRepository.findAll();
        etatDtoToSave.getEtatImprimables().forEach(etatDTO -> {
            if (!repository.existsByLibelle(etatDTO.getLibelle())) {
                EtatImprimable etatImprimable = EtatImprimableParamDTO.buildFromDTO(fenetre.getId(), etatDTO);
                etatImprimable = repository.save(etatImprimable);
                ActionSysteme actionSysteme = ActionSysteme.builder()
                        .description(etatImprimable.getDescription())
                        .fenetre(fenetre)
                        .libelleAction("bouton imprimer " + etatImprimable.getLibelle())
                        .idObjet(etatImprimable.getLibelle().toLowerCase().replace(" ", "-"))
                        .typeObjet(5)
                        .build();
                final ActionSysteme actionSaved = actionSystemeRepository.save(actionSysteme);
                roleList.forEach(role -> {
                    RoleAction roleAction = RoleAction.builder()
                            .action(actionSaved)
                            .role(role)
                            .conditionActif(FALSE)
                            .conditionEditable(FALSE)
                            .conditionVisible(FALSE)
                            .build();
                    roleActionRepository.save(roleAction);
                });
                saved.add(etatImprimable);
            }
        });
        return saved;
    }

    /**
     * @param etatDTO etat a modifier
     * @return L'etat modifie
     * @apiNote Effectue la mise a jour d'un etat enregistre
     */
    @Override
    @Transactional
    public EtatImprimable update(UpdateEtatImprimableParamDTO etatDTO) {
        Fenetre fenetre = fenetreRepository.findById(etatDTO.getIdFenetre())
                .orElseThrow(() -> new ResourceNotFoundException(FENETRE, ID, etatDTO.getIdFenetre()));
        EtatImprimable stored = repository.findById(etatDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException(ETAT_IMPRIMABLE, ID, etatDTO.getId()));
        stored.setType(etatDTO.getType());
        stored.setLibelle(etatDTO.getLibelle());
        stored.setChemin(etatDTO.getChemin());
        stored.setDescription(etatDTO.getDescription());
        stored.setExportable(etatDTO.getExportable());
        stored.setFenetre(fenetre);
        stored.setGroupe(etatDTO.getGroupe());
        return repository.save(stored);
    }

    /**
     * @param paramImpression donnees a inserer dans l'etat
     * @implSpec declenche l'impression d'un etat sur la base de parametres
     */
    @Override
    @Transactional
    public void imprimerEtat(ParamImpressionDTO paramImpression, Connection connection, HttpServletResponse response) throws JRException, IOException {
        EtatImprimable stored = repository.findById(paramImpression.getIdEtat())
                .orElseThrow(() -> new ResourceNotFoundException(ETAT_IMPRIMABLE, ID, paramImpression.getIdEtat()));
        PrintEtatHelper.imprimerEtatImprimable(stored, paramImpression, GeneralUtils.getInstituteHeader(), connection, response);
    }

    /**
     *
     * @param id Identifiant de l'etat imprimable
     */
    @Override
    @Transactional
    public void deleteEtat(Long id) {

        // Supprimer les FiltreEtatImprimable
        filtreEtatRepository.deleteAllByEtatImprimableId(id);

        // Supprimer l'etat
        repository.deleteById(id);
    }
}
