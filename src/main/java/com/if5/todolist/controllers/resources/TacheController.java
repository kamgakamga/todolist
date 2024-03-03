package com.if5.todolist.controllers.resources;

import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.models.dtos.tache.*;
import com.if5.todolist.models.entities.Tache;
import com.if5.todolist.repositories.TacheRepository;
import com.if5.todolist.services.implementations.TacheExcelExporter;
import com.if5.todolist.services.implementations.TachePDFExporter;
import com.if5.todolist.services.interfaces.TacheServiceInter;
import com.lowagie.text.DocumentException;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/toDoList/v1")
@Api("API pour la gestion des taches")
public class TacheController {


	@Autowired private TacheServiceInter tacheServiceInter;
	@Autowired private TacheRepository tacheRepository;

	@ApiOperation(value = "get all task", notes = "récupere toutes les tâches dans la base de donnée", response = Tache.class)
	@ApiResponses(value= {
			@ApiResponse(code = 201, message ="les tâches  ont été récupéré avec succès") })
	@GetMapping("/taches")
	public ResponseEntity<List<TacheResponseDto>> getAllTache(){

		return ResponseEntity.ok(tacheServiceInter.getAllTask());
	}

	@ApiOperation(value = "create new task in database", notes = "elle permet la creation d'une nouvelle tâche  dans la base de donnée", response = Tache.class)
	@ApiResponses(value= {

			@ApiResponse(code = 201, message = "La tâche est creer avec succès"),
			@ApiResponse(code = 404, message = "La tâche fournie n'est pas  valide")
	})
	@PostMapping("/save-tache")
	public ResponseEntity<TacheResponseDto> saveTache(@RequestBody TacheRequestDto  tacheRequestDto) throws EntityNotFoundException{

		return new  ResponseEntity<TacheResponseDto>( tacheServiceInter.saveTask(tacheRequestDto), HttpStatus.CREATED);
	}

	@GetMapping("/tache-details/{id}")
	public ResponseEntity<TacheResponseDto> showDetailsOfAtask(@PathVariable long id) throws EntityNotFoundException{

		System.out.println(id);
		return ResponseEntity.ok(tacheServiceInter.showDetailsOfAtask(id));
	}

	@GetMapping("/tache-to-status")
	public ResponseEntity<List<TacheResponseDto>> taskAccordingToStatus(@RequestParam String status){
		System.out.println("********ok");
		return ResponseEntity.ok(tacheServiceInter.taskAccordingToStatus(status));
	}
	
		 @ApiResponses(value= {
		      @ApiResponse(code = 201, message = "Le nombre de tâche à été calculer avec succès"),
		      @ApiResponse(code = 404, message = "Les informations fournies ne sont pas correcte")
				 })
		  @PostMapping("/priode/statut") 
		  public ResponseEntity<List<TacheResponseDto>> tacheFonctionDePriodeEtStatut(@RequestBody PeriodRequestDto periodRequestDto){
		    	  List<TacheResponseDto> allOfTask = tacheServiceInter.tacheFonctionDePeriodEtStatut(periodRequestDto);
		   return ResponseEntity.ok(allOfTask);
		 }


		 @PutMapping("/ajout-coodonnees")
		 public ResponseEntity<Boolean> ajoutCoorGeoloc(
					@RequestParam(value = "libelle", required = true) String libelle, 
					@RequestParam(value ="ip", required = true) String ip) 
			                   throws EntityNotFoundException, GeoIp2Exception, IOException{
		  return new ResponseEntity<>(tacheServiceInter.ajoutCoordoneeGoe(libelle, ip ), HttpStatus.OK);
		 }
		 
		@PutMapping("/change-status/{id}")
		 public ResponseEntity<String>  changeStatutTache(@PathVariable("id") Long id, @RequestParam String statut ) throws EntityNotFoundException{
		   
		    return ResponseEntity.ok( tacheServiceInter.changeStatutTache(id, statut));

		 }
    

	@DeleteMapping("/tache/delete/{id}")
	public ResponseEntity<OrderResponseDto> deleteTask(@PathVariable Long id) throws EntityNotFoundException {
		
		return new ResponseEntity<>(tacheServiceInter.deleteTask(id),HttpStatus.OK);
	}
	

	@GetMapping("/tache/utilisateur")
	public ResponseEntity<List<TacheResponseDto>> tachePourTravailleur(@RequestBody  NombreRequestDto nombreRequestDto){
		List<TacheResponseDto> nh = tacheServiceInter.tachePourTravailleur(nombreRequestDto) ;
		return ResponseEntity.ok(nh);

	}
	
	@GetMapping("/tache")
	public ResponseEntity<List<TacheResponseDto>> getAlltacheUser(@RequestBody  NombreRequestDto nombreRequestDto){
		
		return ResponseEntity.ok( tacheServiceInter.getAllTacheUtilisateurByStatut(nombreRequestDto));
	}

	@GetMapping("/tache/sprint/user")
	public ResponseEntity<List<TacheResponseDto>> getAlltacheSprintForUser(@RequestBody  NombreRequestDto nombreRequestDto){
		return ResponseEntity.ok( tacheServiceInter.getAllTacheUserForSprint(nombreRequestDto));
	}
	
	@GetMapping("/heure/utilisateur")
	public ResponseEntity<CalculNombreHeureResponseDto> nombreHeure(@RequestBody  NombreRequestDto nombreRequestDto) throws InvalidEntityException{
		
	return ResponseEntity.ok(tacheServiceInter.nombreHeure(nombreRequestDto));	
	}

	@GetMapping("/taches/sprint/{id}")
	public ResponseEntity<List<StatistiqueResponseDto>> statistiqueDesTacheParSprint(@PathVariable Long id) throws EntityNotFoundException{
		
		return ResponseEntity.ok(tacheServiceInter.statistiqueOfTaskForSprint(id));
		
	}
	
	@PostMapping("/add-task/for-user")
	public ResponseEntity<OrderResponseDto> addTaskForUser(@RequestBody AddTaskRequestDTO addTaskRequestDTO) throws EntityNotFoundException{
		
		return ResponseEntity.ok(tacheServiceInter.addTacheForUser(addTaskRequestDTO));
	}
	
	@PutMapping("/update-task/for-user")
	public ResponseEntity<OrderResponseDto> changeTacheForUser(@RequestBody AddTaskRequestDTO addTaskRequestDTO) throws EntityNotFoundException{
		
		return ResponseEntity.ok(tacheServiceInter.changeTacheForUser(addTaskRequestDTO));
	}

	@GetMapping("/taches-sprint/{id}")
	public ResponseEntity<List<TacheResponseDto>> tachePourSprint(@PathVariable Long id) throws EntityNotFoundException{
		return ResponseEntity.ok(tacheServiceInter.tachePourSprint(id));
		
	}

	@GetMapping("/sub-taches/{id}")
	public ResponseEntity<List<TacheResponseDto>> soustachePourTache(@PathVariable Long id) throws EntityNotFoundException{
		return ResponseEntity.ok(tacheServiceInter.sousTachesDuneTache(id));
		
	}
	
	@GetMapping("/taches/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
      
		response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=LISTE_DES_TACHES_DU_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
         
        List<Tache> listTaches = tacheRepository.findAll(Sort.by("libelle").ascending());
         
        TachePDFExporter exporter = new TachePDFExporter(listTaches);
        exporter.export(response);
         
    }
	
	@GetMapping("/taches/{utilisateurId}/export/pdf")
    public void exportToPDF(@PathVariable("utilisateurId") Long utilisateurId, HttpServletResponse response) throws DocumentException, IOException {
      
		response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=LISTE_DES_TACHES_DU_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
         
        //List<Tache> listTaches = tacheRepository.findAll(Sort.by("libelle").ascending());
        List<Tache> listTaches = tacheServiceInter.getAllTacheUser(utilisateurId);
       
        TachePDFExporter exporter = new TachePDFExporter(listTaches);
        exporter.export(response);
         
    }
	
	 @GetMapping("/taches/export/excel")
	    public void exportToExcel(HttpServletResponse response) throws IOException {
	        response.setContentType("application/octet-stream");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());
	         
	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=Taches_" + currentDateTime + ".xlsx";
	        response.setHeader(headerKey, headerValue);
	         
	        List<Tache> listTaches = tacheRepository.findAll(Sort.by("libelle").ascending());
	         
	        TacheExcelExporter excelExporter = new TacheExcelExporter(listTaches);
	         
	        excelExporter.export(response);    
	    }  

}



