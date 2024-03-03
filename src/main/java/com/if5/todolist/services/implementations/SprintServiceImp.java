package com.if5.todolist.services.implementations;

import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.models.dtos.sprint.SprintRequestDto;
import com.if5.todolist.models.dtos.sprint.SprintResponseDto;
import com.if5.todolist.models.dtos.sprint.SprintStatistiqueDto;
import com.if5.todolist.models.entities.EtatTache;
import com.if5.todolist.models.entities.Projet;
import com.if5.todolist.models.entities.Sprint;
import com.if5.todolist.models.entities.Tache;
import com.if5.todolist.repositories.EtatTacheRepository;
import com.if5.todolist.repositories.ProjetRepository;
import com.if5.todolist.repositories.SprintRepository;
import com.if5.todolist.repositories.TacheRepository;
import com.if5.todolist.services.interfaces.SprintServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SprintServiceImp implements SprintServiceInter{
	
	@Autowired private SprintRepository sprintRepository;
	@Autowired private ProjetRepository projetRepository;
	@Autowired private TacheRepository tacheRepository;
	@Autowired private EtatTacheRepository etatTacheRepository;
	
	@Override
	public SprintResponseDto saveSprint(SprintRequestDto sprintRequestDto) throws EntityNotFoundException, InvalidEntityException {
		
		 List<EtatTache> etatTaches = new ArrayList<>(); 
		 List<Tache> taches = new ArrayList<>(); 
		 List<Long> tachesId = sprintRequestDto.getTachesId();
		 Long projetId = sprintRequestDto.getProjetId();
		 if(projetId == null) {
			 throw new EntityNotFoundException("Vous devez selectionner un projet pour ce sprint"); 
		 }
		 
		 Projet projet = projetRepository.findById(projetId).orElse(null);
		if(projet == null) {
			throw new InvalidEntityException("projet non trouver dans la BD");	
		}
		                                  
	     if(sprintRequestDto.getEtatId().isEmpty()) {
	    	 etatTaches.addAll(projet.getEtatTaches());
	     }else {
			for(Long etatTacheId :sprintRequestDto.getEtatId()) {
				EtatTache etatTacheForSprint= etatTacheRepository.findById(etatTacheId).orElse(null);
				if(etatTacheForSprint != null) {
					etatTaches.add(etatTacheForSprint);
				}
			}
	     }
		
	   if(tachesId != null) {	   
		   for(Long tacheId :tachesId) {
				Tache t= tacheRepository.findById(tacheId).orElse(null);
				if(t != null) {
					taches.add(t);
				}
			}
	   }  
		
	Sprint sprint = SprintRequestDto.buildEntityFromDto(sprintRequestDto,projet, taches , etatTaches);
		
	
	sprintRepository.save(sprint);
	return   SprintResponseDto.buildResponseDtoFromEntity(sprint);
		
	}

	@Override
	public String deleteSprint(Long id) throws EntityNotFoundException {
		
		Sprint p = sprintRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Sprint avec l'ID "+id +" non Trouver"));
		sprintRepository.delete(p);
		return "Ce Sprint est definitivement suprimer en BD";
	}

	@Override
	public List<SprintResponseDto> getAllSprint() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<Sprint> pages = sprintRepository.findAll(pageable);
		List<Sprint> listSprint = pages.getContent();
		
		return SprintResponseDto.buildListDtoFromListSprint(listSprint);
	}

	@Override
	public List<SprintResponseDto> sprintParEtat(String libelle) {
	//	List<Sprint> sprintParEtat = sprintRepository.findAllByEtat(libelle);
		return null;
	}

	@Override
	public List<SprintStatistiqueDto> statistique(String etatTache) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addSprintToProjet(String libelle, String nomProjet) {
		Sprint sprint = sprintRepository.findByLibelleIgnoreCase(libelle);
		Projet projet = projetRepository.findByNomProjetIgnoreCase(nomProjet);
		
		if( sprint != null && projet != null) {
			List<Sprint> ls = projet.getSprints();
			ls.add(sprint);
			projet.setSprints(ls);
			sprint.setProjet(projet);
		    projetRepository.save(projet);
		    sprintRepository.save(sprint);
			return "Ajout Effectuer avec succes";	
		}
		
		return "Le Projet ou Le Sprint n'existe pas dans la BD ";
	}

	public Projet getProjet(Long id){
		return projetRepository.getById(id);
	}

	@Override
	public List<SprintResponseDto> sprintForProjet(Long id) {
		
		
		return SprintResponseDto.buildListDtoFromListSprint(sprintRepository.findAllByProjetId(id));
	}
}
