package com.if5.todolist.services.interfaces;

import com.if5.todolist.dtos.requets.application.projet.ProjetRequestDto;
import com.if5.todolist.dtos.responses.application.projet.ProjetResponseDto;
import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public interface ProjetServiceInter {
	
	public ProjetResponseDto saveProjet(ProjetRequestDto projetRequestDto) throws DuplicationEntityException, EntityNotFoundException;
	public String deleteProjet(Long id) throws EntityNotFoundException;
	public Page<ProjetResponseDto> getAllProjet(String keyword, Pageable pageable);
	public ProjetResponseDto getProjet(Long id) throws EntityNotFoundException;

}
