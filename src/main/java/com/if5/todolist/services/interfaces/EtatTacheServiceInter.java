package com.if5.todolist.services.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;

import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.models.dtos.etatTache.EtatTacheRequestDto;
import com.if5.todolist.models.dtos.etatTache.EtatTacheResponseDto;
import com.if5.todolist.models.entities.EtatTache;

@Service
public interface EtatTacheServiceInter {
	
	public EtatTacheResponseDto saveEtat(EtatTacheRequestDto etatTacheRequestDto) throws DuplicationEntityException;
	public EtatTache getByLibelle(String e);
	public List<EtatTache> getAllByDefaultValue(Boolean v);

    List<EtatTacheResponseDto> getAllEtatTaches();
}
