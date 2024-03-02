package com.if5.todolist.services.interfaces.systeme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.if5.todolist.models.entities.parametrages.VariableGlobale;
import com.if5.todolist.services.interfaces.generics.CommonGenericCrudService;

import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:25 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.interfaces.systeme
 **/
public interface VariableGlobaleService extends CommonGenericCrudService<VariableGlobale> {

    /**
     * verifie si le version du backend deployee correspond bien au numero de mise a jour de la BD
     *
     * @return true si la mise a jour a ete correctement effectuee et false le cas echeant
     */
    boolean isAppUgraded();

    /**
     * verifie si la BD est en mode PROD (0)
     *
     * @return true si la est bien en mode PRODUCTION et false le cas echeant
     */
    boolean isAppInProductionMode();

    boolean ifTheBillIsPaid();

    /**
     * obtention de la liste de toutes les variables globales affichables
     */
    List<VariableGlobale> listeVariablesAffichable();

    /**
     * obtention de la variables globale possedant la cle demandee
     *
     * @param key cle a retrouver
     */
    VariableGlobale getVariableByKey(String key);

    /**
     * modifier la valeur variableGlobale en fonction du choix de l'utilisateur
     *
     * @param langue element a modifier
     * @param info            utilisateur ayant effectue la modif
     *
     * return true si la modification a ete faite et false sinon
     */
    void modifierVariableGlobalPourChagerLangue(String langue);
}
