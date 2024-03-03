package com.if5.todolist.controllers.resources;

import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.models.dtos.ApiResponse;
import com.if5.todolist.models.dtos.projet.ProjetRequestDto;
import com.if5.todolist.models.dtos.projet.ProjetResponseDto;
import com.if5.todolist.services.interfaces.ProjetServiceInter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.if5.todolist.utils.StringsUtils.SUCESS_MESSAGE;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/")
@Api("Api pour le gestion des projets")
@Slf4j
public class ProjetController{

    private final ProjetServiceInter projetService;

    public ProjetController(ProjetServiceInter projetService) {

        this.projetService = projetService;
    }

    @PostMapping("projets")
	public ResponseEntity<ApiResponse<ProjetResponseDto>> saveProjet(@RequestBody ProjetRequestDto projetRequestDto) throws DuplicationEntityException, EntityNotFoundException {
		try{
			log.info("Ajout d'un projets dans le système en cours"+ projetRequestDto.toString());
			ProjetResponseDto data =  projetService.saveProjet(projetRequestDto);
			return ResponseEntity.ok(new ApiResponse(true,SUCESS_MESSAGE ,data, new Date()));
		}catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(false, e.getMessage(), e.getCause(),new Date()));
		}
	}

	@DeleteMapping("projets/{id}")
	public ResponseEntity<ApiResponse<String>> deleteProjet(@PathVariable Long id) throws EntityNotFoundException {
        try{
            log.info("Suppresion d'un projets dans le système en cours "+id);

            return ResponseEntity.ok(new ApiResponse(true,SUCESS_MESSAGE ,projetService.deleteProjet(id), new Date()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause(),new Date()));
        }
	}
	  
	@GetMapping("projets")
	 public ResponseEntity<ApiResponse<ProjetResponseDto>> getAllProjet(@ApiParam(value = "clé de la recherche") @RequestParam(name = "keyword", defaultValue = "") String keyword,
																		@ApiParam(value = "Page de la recherche") @RequestParam(name = "page", defaultValue = "0") int page,
																		@ApiParam(value = "taille de l'élément à afficher ")  @RequestParam(name = "size", defaultValue = "20") int size,
																		@RequestParam(name = "sort", defaultValue = "ASC") String sort,
																		@RequestParam(name = "orderBy", defaultValue = "id") String orderBy){
		try{
			log.info("Liste des projets dans le système");
		  Page<ProjetResponseDto> data =  projetService.getAllProjet("%"+keyword+"%", PageRequest.of(page, size, Sort.Direction.fromString(sort), orderBy));
		 return ResponseEntity.ok(new ApiResponse(true,SUCESS_MESSAGE ,data, new Date()));
		 }catch (Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ApiResponse(false, e.getMessage(), e.getCause(),new Date()));
		}
	 } 
	  
	  @GetMapping("projets/{id}")
	  public ResponseEntity<ApiResponse<ProjetResponseDto>> getProjetDetail(@PathVariable Long id){
		  try{
			  ProjetResponseDto data = projetService.getProjet(id);
			  return ResponseEntity.ok(new ApiResponse(true,SUCESS_MESSAGE,data,new Date()));
		  }catch (Exception e){
			  return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					  .body(new ApiResponse(false, e.getMessage(), e.getCause(),new Date()));
		  }
	  }


}
