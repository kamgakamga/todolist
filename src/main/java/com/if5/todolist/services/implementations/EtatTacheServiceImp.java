package com.if5.todolist.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.models.dtos.etatTache.EtatTacheRequestDto;
import com.if5.todolist.models.dtos.etatTache.EtatTacheResponseDto;
import com.if5.todolist.models.entities.EtatTache;
import com.if5.todolist.repositories.EtatTacheRepository;
import com.if5.todolist.services.interfaces.EtatTacheServiceInter;

@Service
public class EtatTacheServiceImp implements EtatTacheServiceInter {
	
	@Autowired private EtatTacheRepository etatTacheRepository;

	@Override
	public EtatTacheResponseDto saveEtat(EtatTacheRequestDto etatTacheRequestDto) throws DuplicationEntityException {	
		
		 // EtatTache etatTache = etatTacheRepository.findByLibelleIgnoreCase(etatTacheRequestDto.getLibelle());
		  String liblle =  etatTacheRequestDto.getLibelle();
		        String lib = liblle.replace(" ", "_");
		        etatTacheRequestDto.setLibelle(lib);
		  
		  if(getByLibelle(etatTacheRequestDto.getLibelle()) != null) {
			  
			  throw new DuplicationEntityException("L'etat de la tache que vous souhaiter ajouter existe deja en BD");
			  
		  }
		  etatTacheRequestDto.setLibelle(etatTacheRequestDto.getLibelle().toUpperCase());
		return EtatTacheResponseDto.buildDtoFromEntity(etatTacheRepository.save(EtatTacheRequestDto.buildEntityFromDto(etatTacheRequestDto)));
	}

	@Override
	public EtatTache getByLibelle(String e) {
    return etatTacheRepository.findByLibelleIgnoreCase(e) ;
	}

	@Override
	public List<EtatTache> getAllByDefaultValue(Boolean v) {
		
		List<EtatTache> e = etatTacheRepository.findAllByDefaultValue(true);
		System.out.println(e);
		return e ;
	}

	@Override
	public List<EtatTacheResponseDto> getAllEtatTaches() {
		return EtatTacheResponseDto.buildListDtoFromListEtatTache(etatTacheRepository.findAll());
	}


}