package com.if5.todolist.services.interfaces;

import com.if5.todolist.dtos.requets.SprintRequestDto;
import com.if5.todolist.dtos.requets.SprintStatistiqueRequestDto;
import com.if5.todolist.dtos.responses.SprintResponseDto;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface SprintServiceInter {
	
	 SprintResponseDto saveSprint(SprintRequestDto sprintRequestDto) throws EntityNotFoundException, InvalidEntityException;
	 String deleteSprint(Long id) throws EntityNotFoundException;
	 Page<SprintResponseDto> getAllSprint(String keyword, Pageable pageable);
	 List<SprintResponseDto> sprintParEtat(String libelle);
	 List<SprintStatistiqueRequestDto> statistique(String libelle);
	 String addSprintToProjet(Long idSprint, Long idProjet);
	 List<SprintResponseDto> sprintForProjet(Long id);
     SprintResponseDto getOneSprint(Long id);
}
