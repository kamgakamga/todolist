package com.if5.todolist.services.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.models.dtos.projet.ProjetRequestDto;
import com.if5.todolist.models.dtos.projet.ProjetResponseDto;

@Transactional
@Service
public interface ProjetServiceInter {
	
	public ProjetResponseDto saveProjet(ProjetRequestDto projetRequestDto) throws DuplicationEntityException, EntityNotFoundException;
	public String deleteProjet(Long id) throws EntityNotFoundException;
	public List<ProjetResponseDto> getAllProjet();
	public ProjetResponseDto getProjet(Long id) throws InvalidEntityException;

}