package com.if5.todolist.services.interfaces;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.models.dtos.sprint.SprintRequestDto;
import com.if5.todolist.models.dtos.sprint.SprintResponseDto;
import com.if5.todolist.models.dtos.sprint.SprintStatistiqueDto;

@Service
@Transactional
public interface SprintServiceInter {
	
	public SprintResponseDto saveSprint(SprintRequestDto sprintRequestDto) throws EntityNotFoundException, InvalidEntityException;
	public String deleteSprint(Long id) throws EntityNotFoundException;
	public List<SprintResponseDto> getAllSprint();
	public List<SprintResponseDto> sprintParEtat(String libelle);
	public List<SprintStatistiqueDto> statistique(String libelle);
	public String addSprintToProjet(String libelle, String nomProjet);
	public List<SprintResponseDto> sprintForProjet(Long id);

}
