package com.if5.todolist.controllers.resources;

import com.if5.todolist.dtos.requets.SprintRequestDto;
import com.if5.todolist.dtos.requets.SprintStatistiqueRequestDto;
import com.if5.todolist.dtos.responses.ApiResponse;
import com.if5.todolist.dtos.responses.SprintResponseDto;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.services.interfaces.SprintServiceInter;
import com.if5.todolist.utils.GeneralUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.if5.todolist.utils.StringsUtils.SUCESS_MESSAGE;

@RestController
@Api("Api pour le gestion des sprints")
@RequestMapping("/api/")
public class SprintController {
	
	private final SprintServiceInter sprintServiceInter;

	public SprintController(SprintServiceInter sprintServiceInter) {
		this.sprintServiceInter = sprintServiceInter;
	}


	@PostMapping("sprint")
	@ApiOperation("Api permettant d'ajouter un nouveau sprint dans la base de donnée.")
	public ResponseEntity<ApiResponse<SprintResponseDto>> saveSprint(@RequestBody SprintRequestDto sprintRequestDto) throws EntityNotFoundException, InvalidEntityException{

		try{
		return 	ResponseEntity.ok(new ApiResponse(true,SUCESS_MESSAGE,sprintServiceInter.saveSprint(sprintRequestDto),new Date()));

		}catch (Exception e){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		              .body(new ApiResponse(false,e.getMessage(),null,new Date()));
		}
	}
	
	@DeleteMapping("sprint/{id}")
	@ApiOperation("Api permettant de supprimer un sprint dans la base de donnée.")
	public ResponseEntity<String> deleteSprint(@PathVariable("id") Long id) throws EntityNotFoundException {
		return ResponseEntity.ok( sprintServiceInter.deleteSprint(id));
	}
	@GetMapping("sprint")
	@ApiOperation("Api permettant de lister tous les sprints de la la base de donnée.")
	public ResponseEntity<ApiResponse<List<SprintResponseDto>>> getAllSprint(@ApiParam(value = "clé de la recherche") @RequestParam(name = "keyword", defaultValue = "") String keyword,
																			 @ApiParam(value = "Page de la recherche") @RequestParam(name = "page", defaultValue = "0") int page,
																			 @ApiParam(value = "taille de l'élément à afficher ")  @RequestParam(name = "size", defaultValue = "20") int size,
																			 @RequestParam(name = "sort", defaultValue = "ASC") String sort,
																			 @RequestParam(name = "orderBy", defaultValue = "id") String orderBy){
		try{
			return 	ResponseEntity.ok(new ApiResponse(true,SUCESS_MESSAGE,sprintServiceInter.getAllSprint(GeneralUtils.buildKeyword(keyword), PageRequest.of(page, size, Sort.Direction.fromString(sort), orderBy)),new Date()));

		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(false,e.getMessage(),null,new Date()));
		}
	}

	@GetMapping("sprint/{id}")
	@ApiOperation("Api permettant d'avoir le détail d'un sprint.")
	public ResponseEntity<ApiResponse<SprintResponseDto>> getSprintDetail(@PathVariable Long id){
		try{
			SprintResponseDto data = sprintServiceInter.getOneSprint(id);
			return ResponseEntity.ok(new ApiResponse(true,SUCESS_MESSAGE,data,new Date()));
		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(false, e.getMessage(), e.getCause(),new Date()));
		}
	}

	@GetMapping("/sprint/etat")
	public List<SprintResponseDto> sprintParEtat(@RequestParam("libelle") String libelle){
		return null;
		
	} 
	
	@GetMapping("/statistique/sprint")
	public List<SprintStatistiqueRequestDto> statistique(@RequestParam("libelle") String libelle){
		return null;
		
	}
	
	@PutMapping("/add/sprint-to/project")
	public ResponseEntity<String> addSprintToProjet(@RequestParam("idSprint") Long idSprint, @RequestParam("idProjet") Long idProjet){
		return ResponseEntity.ok(sprintServiceInter.addSprintToProjet(idSprint, idProjet));
		
		
	}
	
	@GetMapping("/all/sprint_for/projet/{id}")
	public ResponseEntity<List<SprintResponseDto>> getAllSprintForProjet(@PathVariable Long id){
		
		return ResponseEntity.ok(sprintServiceInter.sprintForProjet(id));
	}

	

}
