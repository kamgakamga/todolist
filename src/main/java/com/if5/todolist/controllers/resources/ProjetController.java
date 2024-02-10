package com.if5.todolist.controllers.resources;

import java.util.List;

import com.if5.todolist.models.dtos.ApiResponse;
import com.if5.todolist.models.entities.Projet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.models.dtos.projet.ProjetRequestDto;
import com.if5.todolist.models.dtos.projet.ProjetResponseDto;
import com.if5.todolist.services.interfaces.ProjetServiceInter;

import io.swagger.annotations.Api;

@CrossOrigin("*")
@RestController
@RequestMapping("/toDoList/v1")
@Api("Api pour le gestion des projets")
public class ProjetController{

	  @Autowired private ProjetServiceInter projetServiceInter;

	@PostMapping("/save/project")
	public ResponseEntity<ProjetResponseDto> saveProjet(@RequestBody ProjetRequestDto projetRequestDto) throws DuplicationEntityException, EntityNotFoundException {
		System.out.println("projetRequestDto.toString()");
		return new ResponseEntity<>(projetServiceInter.saveProjet(projetRequestDto), HttpStatus.CREATED);
	}

	@DeleteMapping("/delete/project/{id}")
	public ResponseEntity<String> deleteProjet(@PathVariable Long id) throws EntityNotFoundException {
		return ResponseEntity.ok(projetServiceInter.deleteProjet(id));
	}
	  
	  @GetMapping("/all/projet")
	 public ResponseEntity<ApiResponse> getAllProjet(){
		try{
		  List<ProjetResponseDto> data = projetServiceInter.getAllProjet();
		 return ResponseEntity.ok(new ApiResponse(true,"Listte de tous les projets",data));
		 }catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(false, e.getMessage(), e.getCause()));
		}
	 } 
	  
	/*  @GetMapping("/detail/projet/{id}")
	  public ResponseEntity<ProjetResponseDto> getProjet(@PathVariable Long id){



		  try{
			  List<ProjetResponseDto> data = projetServiceInter.getAllProjet();
			  return ResponseEntity.ok(new ApiResponse(true,"Listte de tous les projets",data));
		  }catch (Exception e){
			  return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					  .body(new ApiResponse(false, e.getMessage(), e.getCause()));
		  }
	  }


	 */

}
