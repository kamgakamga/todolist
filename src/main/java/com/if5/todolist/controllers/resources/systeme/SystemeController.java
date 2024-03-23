package com.if5.todolist.controllers.resources.systeme;

import com.if5.todolist.dtos.requets.systeme.ParamImpressionDTO;
import com.if5.todolist.dtos.responses.ApiResponse;
import com.if5.todolist.dtos.responses.systeme.EtatimprimableResponseDTO;
import com.if5.todolist.dtos.responses.systeme.ListeFiltreEtatDTO;
import com.if5.todolist.models.entities.parametrages.VariableGlobale;
import com.if5.todolist.models.entities.systeme.Filtre;
import com.if5.todolist.models.entities.systeme.MessageSysteme;
import com.if5.todolist.services.interfaces.systeme.*;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Connection;
import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 1:32 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.controllers.resources.systeme
 **/

@CrossOrigin("*")
@RestController
@RequestMapping("/api/systeme")
@Transactional
@Slf4j
public class SystemeController {

    @Autowired
    private DataSource dataSource;

    private final EtatImprimableService etatImprimableService;
    private final FiltreEtatImprimableService filtreEtatImprimableService;
    private final FiltreService filtreService;
    private final VariableGlobaleService variableGlobaleService;
    private final MessageSystemeService messageService;

    public SystemeController(DataSource dataSource,
                               EtatImprimableService etatImprimableService,
                               FenetreService fenetreService,
                               FiltreService filtreService,
                               FiltreEtatImprimableService filtreEtatImprimableService,
                               ModeleImpressionService modeleImpressionService,
                               VariableGlobaleService variableGlobaleService,
                               MessageSystemeService messageService) {

        this.dataSource = dataSource;

        this.etatImprimableService = etatImprimableService;
        this.filtreService = filtreService;
        this.filtreEtatImprimableService = filtreEtatImprimableService;
        this.variableGlobaleService = variableGlobaleService;
        this.messageService = messageService;
    }

    @GetMapping(value = "/variable-globale/{key}/consulter")
    @ApiOperation("API d'obtention des details d'une variable globale")
    public ResponseEntity<ApiResponse> consulterVariablesGlobales(@NotBlank @PathVariable String key) {

        VariableGlobale data = variableGlobaleService.getVariableByKey(key);
        MessageSysteme message = messageService.findByCodeMessage(1001);
        return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data, new Date()));
    }

    @GetMapping(value = "/variable-globale/liste")
    @ApiOperation("API d'obtention de la liste de toutes les variables globales affichables")
    public ResponseEntity<ApiResponse> listeVariablesGlobales() {

        List<VariableGlobale> data = variableGlobaleService.listeVariablesAffichable();
        MessageSysteme message = messageService.findByCodeMessage(1001);
        return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data, new Date()));
    }

   /* @PutMapping(value = "/variable-globale/modifier")
    @ApiOperation("API de modification de la valeur et de la description d'une variable globale affichable")
    public ResponseEntity<ApiResponse> modifierVariableGlobale(@Valid @RequestBody VariableGlobaleDTO variableGlobale) {
        // Enregistrement des parametres de la trace d'activite
        String details = "modification de la valeur et de la description d'une variable globale affichable";
        String param = JsonUtil.getJson(variableGlobale);
        String source = "GET /api/parametre/variable-globale/modifier" + ENTITY_NAME + ".listeVariablesGlobales()";

        if (!variableGlobaleService.existsById(variableGlobale.getId())) {
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    String.format(messages, "variableGlobale", "id", variableGlobale.getId()));
            throw new ResourceNotFoundException("variableGlobale", "id", variableGlobale.getId());
        }
        UserInfo info = GeneralUtils.buildUserInfo(userConnected, null);
        VariableGlobale data = variableGlobaleService.modifierVariableGlobale(variableGlobale, info);
        LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param, null);
        MessageSysteme message = messageService.findByCodeMessage(1001);
        return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data));

    }*/

   /* @GetMapping(value = "/numerotation/{id_souche}/generer")
    @ApiOperation("API d'obtention du prochain numéro disponible")
    @ApiImplicitParam(name = "id_souche", value = "type de numero attendu: 0 = Matricule, 1 = Règlement élève, 2 = Règlement divers", allowableValues = "0, 1, 2", format = "Long", dataTypeClass = Long.class, example = "1")
    public ResponseEntity<ApiResponse> genererProchainNumeroSouche(@ApiIgnore @CurrentUser LocalUser userConnected,
                                                                   @PathVariable Long id_souche) {

        // Enregistrement des parametres de la trace d'activite
        String details = "Obtention du prochain numéro disponible type souche= " + id_souche;
        String param = JsonUtil.getJson(id_souche);
        String source = "GET /api/parametre/numerotation/{id_souche}/generer" + ENTITY_NAME
                + ".genererProchainNumeroSouche()";

        if (id_souche != 0 && id_souche != 1 && id_souche != 2) {
            MessageSysteme message = messageService.findByCodeMessage(2094);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param, message.getValeurFr() + id_souche);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false, message.getValeurFr(), id_souche));
        }

        String data = numerotationService.genererNumeroDisponible(id_souche);
        LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param, null);
        MessageSysteme message = messageService.findByCodeMessage(1001);
        return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data));

    }*/

/*
    @PostMapping(value = "/numerotation/ajouter")
    @ApiOperation("API pour définir les parametres de numérotation")
    @ApiImplicitParam(name = "numerotation", value = "paramatres definissant le masque de generation des numeros", dataTypeClass = NumerotationDTO.class)
    public ResponseEntity<ApiResponse> definirNumerotation(@ApiIgnore @CurrentUser LocalUser userConnected,
                                                           @Valid @RequestBody NumerotationDTO numerotation) {

        Numerotation data = numerotationService.save(numerotation);
        LogManagerHelper.saveActivity(activiteService, userConnected,
                "POST /api/parametre/numerotation/ajouter" + ENTITY_NAME + ".definirNumerotation()",
                "définir les parametres de numérotation " + numerotation, JsonUtil.getJson(numerotation), null);
        MessageSysteme message = messageService.findByCodeMessage(1001);
        return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data));
    }
*/

  /*  @GetMapping(value = "/numerotation/consulter")
    @ApiOperation("API pour recuperer les details des Paramètre de numérotation")
    public ResponseEntity<ApiResponse> consulterNumerotation(@ApiIgnore @CurrentUser LocalUser userConnected) {

        Optional<Numerotation> data = numerotationService.consulterNumerotation();
        LogManagerHelper.saveActivity(activiteService, userConnected,
                "GET /api/parametre/numerotation/consulter" + ENTITY_NAME + ".consulterNumerotation()",
                "recuperer les details des Paramètre de numérotation", null, null);
        MessageSysteme message = messageService.findByCodeMessage(1001);
        MessageSysteme messages = messageService.findByCodeMessage(2095);

        return data.isPresent() ? ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data))
                : ResponseEntity.badRequest().body(new ApiResponse(false, messages.getValeurFr(), null));

    }
*/
    @PostMapping(value = "/etat/imprimer")
    @Transactional
    @ApiOperation("Api qui declenche l'impression d'un etat imprimable")
    public void imprimerEtat(@Valid @RequestBody ParamImpressionDTO paramImpression,
                             HttpServletResponse response) {

        if (!etatImprimableService.existsById(paramImpression.getIdEtat())) {
            MessageSysteme message = messageService.findByCodeMessage(2018);
        }
        try {
            Connection connection = dataSource.getConnection();
            etatImprimableService.imprimerEtat(paramImpression, connection, response);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

  /*  @GetMapping(value = "/etat/liste/paged")
    @ApiOperation("Api qui produit la liste de tous les etats imprimables de la BD")
    public ResponseEntity<ApiResponse> listeEtatsPaged(@ApiIgnore @CurrentUser LocalUser userConnected,
                                                       Pageable pageable) {

        paramsMap.clear();
        paramsMap.put("page", pageable.getPageNumber());
        paramsMap.put("size", pageable.getPageSize());

        // Enregistrement des parametres de la trace d'activite
        String details = String.format("liste de tous les etats imprimables paginés de la BD");
        String param = JsonUtil.getJson(paramsMap);
        String source = "GET /api/parametre/etat/liste/paged" + ENTITY_NAME + ".listeEtatsPaged()";

        try {
            Page<EtatActionDTO> pageQueried = etatImprimableService.findByAllEtatAction(pageable);

            List<EtatActionResponse> pageContent = EtatActionResponse
                    .buildFromEntity(pageQueried.getContent());
            PageDataResponse data = PageDataResponse.buildFromEntities(pageQueried, pageContent);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param, null);
            MessageSysteme message = messageService.findByCodeMessage(1001);
            return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data));
        } catch (Exception e) {
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    "Exception " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause()));
        }
    }*/

    @GetMapping(value = "/etat/liste")
    @ApiOperation("Api qui produit la liste de tous les etats imprimables de la BD")
    public ResponseEntity<ApiResponse> listeEtats() {
        try {
            List<EtatimprimableResponseDTO> data = EtatimprimableResponseDTO
                    .buildFromEntityList(etatImprimableService.findAll());
            MessageSysteme message = messageService.findByCodeMessage(1001);
            return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data, new Date()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause(), new Date()));
        }
    }

/*    @GetMapping(value = "/etat/{idEtat}/modele/liste")
    @ApiOperation("Api qui produit la liste des modèles d'impression sur la base d'un Id de l'état imprimable de la BD")
    public ResponseEntity<ApiResponse> listeEtatModelesImpression(@ApiIgnore @CurrentUser LocalUser userConnected,
                                                                  @NotNull @PathVariable Long idEtat) {
        // Enregistrement des parametres de la trace d'activite
        String details = "liste des modèles d'impression sur la base d'un Id de l'état imprimable de la BD";
        String param = JsonUtil.getJson(idEtat);
        String source = "GET /api/parametre/etat/{idEtat}/modele/liste" + ENTITY_NAME + ".listeEtatModelesImpression()";
        if (!etatImprimableService.existsById(idEtat)) {
            MessageSysteme message = messageService.findByCodeMessage(2018);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    message.getValeurFr());
            throw new ResourceNotFoundException("Etat", "id", idEtat);
        }
        try {
            final List<ShortModeleImpressionDTO> data = modeleImpressionService.findAllByEtatImprimableId(idEtat);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param, null);
            MessageSysteme message = messageService.findByCodeMessage(1001);
            return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data));
        } catch (Exception e) {
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    "Exception " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause()));
        }
    }*/
/*
    @PostMapping(value = "/etat/{id}/filtre/ajouter")
    @ApiOperation("Api qui ajoute des filtres a un etat imprimable de la BD sur la base de l'ID de l'etat")
    public ResponseEntity<ApiResponse> ajouterFiltresEtat(@Valid @RequestBody AddFiltresEtatDTO filtresEtat) {

        if (!etatImprimableService.existsById(filtresEtat.getIdEtat())) {
            MessageSysteme message = messageService.findByCodeMessage(2018);
            throw new ResourceNotFoundException("Etat", "id", filtresEtat.getIdEtat());
        }
        try {
            ListeFiltreEtatDTO data = filtreEtatImprimableService.ajouterFiltresEtat(filtresEtat);
            MessageSysteme message = messageService.findByCodeMessage(1001);
            return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data, new Date()));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause(),new Date()));
        }
    }*/

    @DeleteMapping(value = "/filtre-etat/{idFiltreEtat}/supprimer")
    @ApiOperation("Api qui supprime un filtre qui avait ete assigne a un etat imprimable de la BD sur la base de l'ID de FiltreEtatImprimable")
    public ResponseEntity<ApiResponse> supprimerFiltresEtat(@NotNull @PathVariable Long idFiltreEtat) {

        if (!filtreEtatImprimableService.existsById(idFiltreEtat)) {
            MessageSysteme message = messageService.findByCodeMessage(2019);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, message.getValeurFr(), idFiltreEtat, new Date()));
        }
        try {
            filtreEtatImprimableService.delete(idFiltreEtat);
            MessageSysteme message = messageService.findByCodeMessage(1001);
            return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), null, new Date()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause(), new Date()));
        }
    }

/*    @PutMapping(value = "/filtre-etat/{idFiltreEtat}/modifier")
    @ApiOperation("Api qui modifie un filtre qui avait ete assigne a un etat imprimable de la BD sur la base de l'ID de FiltreEtatImprimable")
    public ResponseEntity<ApiResponse> supprimerFiltresEtat(@Valid @RequestBody UpdateFiltreEtatDTO updateFiltreEtat) {

        if (!filtreEtatImprimableService.existsById(updateFiltreEtat.getIdFiltreEtat())) {
            MessageSysteme message = messageService.findByCodeMessage(2019);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    message.getValeurFr());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, message.getValeurFr(), updateFiltreEtat.getIdFiltreEtat()));
        }
        if (!etatImprimableService.existsById(updateFiltreEtat.getIdEtat())) {
            MessageSysteme message = messageService.findByCodeMessage(2018);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    message.getValeurFr());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, message.getValeurFr(), updateFiltreEtat.getIdEtat()));
        }
        if (!filtreService.existsById(updateFiltreEtat.getIdFiltre())) {
            MessageSysteme message = messageService.findByCodeMessage(2020);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    message.getValeurFr());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, message.getValeurFr(), updateFiltreEtat.getIdFiltre()));
        }
        try {
            FiltreEtatImprimable data = filtreEtatImprimableService
                    .update(UpdateFiltreEtatDTO.buildFromDTO(updateFiltreEtat));
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param, null);
            MessageSysteme message = messageService.findByCodeMessage(1001);
            return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data));
        } catch (Exception e) {
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    "Exception " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause()));
        }
    }*/

    @Deprecated
    @GetMapping(value = "/etat/{id}/filtre/liste")
    @ApiOperation("Api qui produit la liste de tous les filtres affectes a un etat imprimables de la BD sur la base de l'ID de l'etat")
    public ResponseEntity<ApiResponse> listeFiltresEtat(@NotNull @PathVariable Long id) {

        if (!etatImprimableService.existsById(id)) {
            MessageSysteme message = messageService.findByCodeMessage(2018);

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, message.getValeurFr(), id, new Date()));
        }
        try {
            ListeFiltreEtatDTO data = filtreEtatImprimableService.findByIdEtat(id);
            MessageSysteme message = messageService.findByCodeMessage(1001);
            return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data, new Date()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause(), new Date()));
        }
    }

/*
    @GetMapping(value = "/etat/{id_etat}/traiter/{traiter}/filtre/liste")
    @ApiOperation("Api qui produit la liste de tous les filtres affectes a un etat imprimables de la BD sur la base de l'ID de l'etat")
    @ApiImplicitParam(name = "traiter", value = "Entier définissant si la source de donnees doit etre traitee ou pas: 0=NON, 1=OUI", paramType = "path", format = "Long", dataTypeClass = Long.class, example = "20")
    public ResponseEntity<ApiResponse> listeFiltresTraiterEtat(@NotNull @PathVariable("id_etat") Long id, @NotNull @PathVariable Long traiter) {

        if (!etatImprimableService.existsById(id)) {
            MessageSysteme message = messageService.findByCodeMessage(2018);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, message.getValeurFr(), id, new Date()));
        }
        if (traiter != 0 && traiter != 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, "La valeur du champs traiter n'est pas accepté", traiter,new Date()));
        }
        try {
            int traiterSource = traiter.intValue();
            ListeFiltreEtatDTO data = traiterSource > 0 ? filtreRechercheService.findByIdEtat(id, traiterSource)
                    : filtreEtatImprimableService.findByIdEtat(id);
            MessageSysteme message = messageService.findByCodeMessage(1001);
            return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data, new Date()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause(),new Date()));
        }
    }
*/

    @GetMapping(value = "/etat/{id}/filtre/disponible")
    @ApiOperation("Api qui produit la liste de tous les filtres disponibles pour un etat imprimable de la BD sur la base de l'ID de l'etat")
    public ResponseEntity<ApiResponse> listeFiltresDispoPourEtat(@NotNull @PathVariable Long id) {


        if (!etatImprimableService.existsById(id)) {
            MessageSysteme message = messageService.findByCodeMessage(2018);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, message.getValeurFr(), id, new Date()));
        }
        List<Filtre> data = filtreService.listeFiltresDispoPourEtat(id);
        MessageSysteme message = messageService.findByCodeMessage(1001);
        return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data, new Date()));
    }

    @GetMapping(value = "/etat/filtre/disponible")
    @ApiOperation("Api qui produit la liste de tous les filtres de la BD sur la base de l'ID de l'etat")
    public ResponseEntity<ApiResponse> listeFiltres() {

        List<Filtre> data = filtreService.listeFiltres();
        MessageSysteme message = messageService.findByCodeMessage(1001);
        return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data, new Date()));

    }

   /* @PostMapping(value = "/etat/ajouter")
    @ApiOperation("Api qui enregistre de nouveaux etats imprimables dans la BD")
    public ResponseEntity<ApiResponse> ajouterEtats(@ApiIgnore @CurrentUser LocalUser userConnected,
                                                    @Valid @RequestBody AddEtatImprimableParamDTO etatDTO) {

        // Enregistrement des parametres de la trace d'activite
        String details = String.format("Api qui enregistre de nouveaux etats imprimables %s", etatDTO);
        String param = JsonUtil.getJson(etatDTO);
        String source = "POST /api/parametre/etat/ajouter" + ENTITY_NAME + ".ajouterEtats()";

        if (Objects.isNull(etatDTO.getIdFenetre()) || !fenetreService.existsById(etatDTO.getIdFenetre())) {
            MessageSysteme message = messageService.findByCodeMessage(2021);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    message.getValeurFr());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, message.getValeurFr(),
                            etatDTO.getIdFenetre()));
        }
        try {
            List<EtatimprimableResponseDTO> data = EtatimprimableResponseDTO
                    .buildFromEntityList(etatImprimableService.saveManyEtats(etatDTO));
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param, null);
            MessageSysteme message = messageService.findByCodeMessage(1001);
            return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data));
        } catch (Exception e) {
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    "Exception " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause()));
        }
    }
*/
   /* @PutMapping(value = "/etat/modifier")
    @ApiOperation("Api qui modifie un etat imprimable de la BD")
    public ResponseEntity<ApiResponse> modifierEtat(@ApiIgnore @CurrentUser LocalUser userConnected,
                                                    @Valid @RequestBody UpdateEtatImprimableParamDTO etatDTO) {

        // Enregistrement des parametres de la trace d'activite
        String details = String.format("Api qui modifie un etat imprimable %s", etatDTO);
        String param = JsonUtil.getJson(etatDTO);
        String source = "PUT /api/parametre/etat/modifier" + ENTITY_NAME + ".modifierEtat()";

        if (Objects.isNull(etatDTO.getId()) || !etatImprimableService.existsById(etatDTO.getId())) {
            MessageSysteme message = messageService.findByCodeMessage(2018);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    message.getValeurFr());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, message.getValeurFr(),
                            etatDTO.getIdFenetre()));
        }
        if (Objects.isNull(etatDTO.getIdFenetre()) || !fenetreService.existsById(etatDTO.getIdFenetre())) {
            MessageSysteme message = messageService.findByCodeMessage(2021);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    message.getValeurFr());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, message.getValeurFr(),
                            etatDTO.getIdFenetre()));
        }
        try {
            EtatImprimable data = etatImprimableService.update(etatDTO);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param, null);
            MessageSysteme message = messageService.findByCodeMessage(1001);
            return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), data));
        } catch (Exception e) {
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    "Exception " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause()));
        }
    }
*/
    /*@DeleteMapping(value = "/etat/{id}/supprimer")
    @ApiOperation("Api qui supprime un etat imprimable de la BD")
    public ResponseEntity<ApiResponse> supprimerEtat(@ApiIgnore @CurrentUser LocalUser userConnected,
                                                     @NotNull @PathVariable Long id) {
        // Enregistrement des parametres de la trace d'activite
        String details = String.format("Api qui supprime un etat imprimable %s", id);
        String param = JsonUtil.getJson(id);
        String source = "DELETE /api/parametre/etat/{id}/supprimer" + ENTITY_NAME + ".supprimerEtat()";

        if (!etatImprimableService.existsById(id)) {
            MessageSysteme message = messageService.findByCodeMessage(2018);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    message.getValeurFr());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, message.getValeurFr(), id));
        }
        try {
            etatImprimableService.deleteEtat(id);
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param, null);
            MessageSysteme message = messageService.findByCodeMessage(1001);
            return ResponseEntity.ok(new ApiResponse(true, message.getValeurFr(), null));
        } catch (Exception e) {
            LogManagerHelper.saveActivity(activiteService, userConnected, source, details, param,
                    "Exception " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause()));
        }
    }
    */

}
