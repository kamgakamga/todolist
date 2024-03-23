package com.if5.todolist.services.interfaces.systeme;

import com.if5.todolist.dtos.requets.systeme.AddEtatImprimableParamDTO;
import com.if5.todolist.dtos.requets.systeme.ParamImpressionDTO;
import com.if5.todolist.dtos.requets.systeme.UpdateEtatImprimableParamDTO;
import com.if5.todolist.dtos.responses.systeme.EtatActionDTO;
import com.if5.todolist.models.entities.systeme.EtatImprimable;
import com.if5.todolist.services.interfaces.generics.CommonGenericCrudService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 1:55 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.interfaces.systeme
 **/
public interface EtatImprimableService extends CommonGenericCrudService<EtatImprimable> {
    /**
     * @param pageable parametres de pagination
     * @return La liste des {@link EtatActionDTO}
     * @implSpec retourne la liste de tous les etats avec pour chacun l'action a laquelle il est lie
     */
    Page<EtatActionDTO> findByAllEtatAction(Pageable pageable);

    /**
     * @param etatDTO liste des etats a enregistrer
     * @return La liste des etats enregistres
     * @apiNote Energistre plusieurs etats pour une fenetre
     */
    List<EtatImprimable> saveManyEtats(AddEtatImprimableParamDTO etatDTO);


    /**
     * @param etatDTO etat a modifier
     * @return L'etat modifie
     * @apiNote Effectue la mise a jour d'un etat enregistre
     */
    EtatImprimable update(UpdateEtatImprimableParamDTO etatDTO);

    /**
     * @param paramImpression donnees a inserer dans l'etat
     * @implSpec declenche l'impression d'un etat sur la base de parametres
     */
    void imprimerEtat(ParamImpressionDTO paramImpression, Connection connection, HttpServletResponse response) throws JRException, IOException;


    void deleteEtat(Long id);
}
