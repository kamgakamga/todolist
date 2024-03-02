package com.if5.todolist.services.implementations.systeme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.if5.todolist.exceptions.ResourceNotFoundException;
import com.if5.todolist.models.entities.parametrages.VariableGlobale;
import com.if5.todolist.repositories.parametre.VariableGlobaleRepository;
import com.if5.todolist.services.implementations.generics.CommonGenericCrudServiceImpl;
import com.if5.todolist.services.interfaces.systeme.VariableGlobaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.if5.todolist.utils.GeneralUtils.*;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:44 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.implementations.systeme
 **/
@Service
@Slf4j
public class VariableGlobaleServiceImpl extends CommonGenericCrudServiceImpl<VariableGlobaleRepository,
        VariableGlobale> implements VariableGlobaleService {

    @Value("12236544888")
    private String appVersion;

    public VariableGlobaleServiceImpl(VariableGlobaleRepository repository) {
        super(repository);
    }

    /**
     * verifie si la BD est en mode PROD (0)
     *
     * @return true si la est bien en mode PRODUCTION et false le cas echeant
     */
    @Override
    public boolean isAppInProductionMode() {
        return getVariableByKey(VARIABLE_APP_MODE).getValeur().equalsIgnoreCase("0");
    }

    @Override
    public boolean ifTheBillIsPaid() {
        boolean test = true;
        VariableGlobale debtClaim = repository.findTopByCle(DEBT_CLAIM).orElse(null);
        if (Objects.nonNull(debtClaim)) {
            LocalDate localDate = LocalDate.parse(debtClaim.getValeur().trim());
            if (LocalDate.now().isAfter(localDate)) {
                test = false;
            }
        }
        return test;
    }

    /**
     * verifie si le version du backend deployee correspond bien au numero de mise a jour de la BD
     * et s'assure aussi de ce que la BD est en mode PROD (0)
     *
     * @return true si la mise a jour a ete correctement effectuee et false le cas echeant
     */
    @Override
    public boolean isAppUgraded() {
        return getVariableByKey(VARIABLE_APP_VERSION).getValeur().equalsIgnoreCase(appVersion);
    }


    /**
     * obtention de la variables globale possedant la cle demandee
     *
     * @param key cle a retrouver
     */
    @Override
    public VariableGlobale getVariableByKey(String key) {
        return repository.findTopByCle(key).orElseThrow(() -> new ResourceNotFoundException("Variable Globale", "cle", key));
    }

    /**
     * obtention de la liste de toutes les variables globales affichables
     */
    @Override
    public List<VariableGlobale> listeVariablesAffichable() {
        return repository.listeVariablesAffichable();
    }

/*
    @Override
    @Transactional
    public VariableGlobale modifierVariableGlobale(VariableGlobaleDTO variableGlobale, UserInfo info) {
        VariableGlobale stored = repository.findById(variableGlobale.getId())
                .orElseThrow(() -> new ResourceNotFoundException("VariableGlobale", "id", variableGlobale.getId()));
        stored.setValeur(variableGlobale.getValeur());
        stored.setDescription(variableGlobale.getDescription());
        stored.setDate_modif(LocalDateTime.now());
        stored.setUtil_modif(info.getDisplayName());
        return null;
    }
*/
    @Override
    public void modifierVariableGlobalPourChagerLangue(String langue) {
        String key = "SYSTEME_LANGUE";
        Optional<VariableGlobale> variableGlobale = repository.findTopByCle(key);
        VariableGlobale vg;

        if(variableGlobale.isPresent()) {
            vg = variableGlobale.get();
            if(!langue.equals(vg.getValeur())) {
                vg.setValeur(langue);
                repository.save(vg);
            }
        }
    }
}
