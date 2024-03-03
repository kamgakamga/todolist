package com.if5.todolist.controllers.resources;

import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.models.dtos.etatTache.EtatTacheRequestDto;
import com.if5.todolist.models.dtos.etatTache.EtatTacheResponseDto;
import com.if5.todolist.services.interfaces.EtatTacheServiceInter;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/toDoList/v1")
@CrossOrigin("*")
@Api("Api pour la gestion des Etats")
public class EtatController {
	
	 private final EtatTacheServiceInter etatTacheServiceInter;

	public EtatController(EtatTacheServiceInter etatTacheServiceInter) {
		this.etatTacheServiceInter = etatTacheServiceInter;
	}


	@PostMapping("/save/etat")
	public ResponseEntity<EtatTacheResponseDto> saveEtat(@RequestBody EtatTacheRequestDto etatRequestDto) throws DuplicationEntityException{
		return new  ResponseEntity<EtatTacheResponseDto>(etatTacheServiceInter.saveEtat(etatRequestDto),HttpStatus.CREATED);
	}

	@GetMapping("/all/etat-tache")
	public ResponseEntity<List<EtatTacheResponseDto>> getAllEtatTache() {
		List<EtatTacheResponseDto> etatsTaches = etatTacheServiceInter.getAllEtatTaches();
		return new  ResponseEntity<List<EtatTacheResponseDto>>(etatsTaches,HttpStatus.OK);
	}

	
}
