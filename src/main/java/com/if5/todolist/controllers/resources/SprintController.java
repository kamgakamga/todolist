package com.if5.todolist.controllers.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.models.dtos.sprint.SprintRequestDto;
import com.if5.todolist.models.dtos.sprint.SprintResponseDto;
import com.if5.todolist.models.dtos.sprint.SprintStatistiqueDto;
import com.if5.todolist.services.interfaces.SprintServiceInter;

@RestController
@RequestMapping("/todo-list/v1")
public class SprintController {
	
	private final SprintServiceInter sprintServiceInter;

	public SprintController(SprintServiceInter sprintServiceInter) {
		this.sprintServiceInter = sprintServiceInter;
	}


	@PostMapping("/save/sprint")
	public ResponseEntity<SprintResponseDto> saveSprint(@RequestBody SprintRequestDto sprintRequestDto) throws EntityNotFoundException, InvalidEntityException{
		return new ResponseEntity<SprintResponseDto>(sprintServiceInter.saveSprint(sprintRequestDto), HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/delete/sprint/{id}")
	public ResponseEntity<String> deleteSprint(@PathVariable("id") Long id) throws EntityNotFoundException {
		return ResponseEntity.ok( sprintServiceInter.deleteSprint(id));
	}
	@GetMapping("/all/sprint")
	public ResponseEntity<List<SprintResponseDto>> getAllSprint(){
	
		return ResponseEntity.ok(sprintServiceInter.getAllSprint());
	}
	
	@GetMapping("/sprint/etat")
	public List<SprintResponseDto> sprintParEtat(@RequestParam("libelle") String libelle){
		return null;
		
	} 
	
	@GetMapping("/statistique/sprint")
	public List<SprintStatistiqueDto> statistique(@RequestParam("libelle") String libelle){
		return null;
		
	}
	
	@PutMapping("/add/sprint-to/project")
	public ResponseEntity<String> addSprintToProjet(@RequestParam String libelle, @RequestParam String nomprojet){
		return ResponseEntity.ok(sprintServiceInter.addSprintToProjet(libelle, nomprojet));
		
		
	}
	
	@GetMapping("/all/sprint_for/projet/{id}")
	public ResponseEntity<List<SprintResponseDto>> getAllSprintForProjet(@PathVariable Long id){
		
		return ResponseEntity.ok(sprintServiceInter.sprintForProjet(id));
	}

	

}
