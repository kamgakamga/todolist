package com.if5.todolist.services.interfaces;

import java.io.IOException;
import java.util.List;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.models.dtos.tache.TacheRequestDto;
import com.if5.todolist.models.dtos.tache.TacheResponseDto;
import com.if5.todolist.models.entities.Tache;
import com.if5.todolist.models.dtos.tache.AddTaskRequestDTO;
import com.if5.todolist.models.dtos.tache.CalculNombreHeureResponseDto;
import com.if5.todolist.models.dtos.tache.NombreRequestDto;
import com.if5.todolist.models.dtos.tache.OrderResponseDto;
import com.if5.todolist.models.dtos.tache.PeriodRequestDto;
import com.if5.todolist.models.dtos.tache.StatistiqueResponseDto;
import com.maxmind.geoip2.exception.GeoIp2Exception;

public interface TacheServiceInter {
    
    public TacheResponseDto saveTask(TacheRequestDto tacheRequestDto) throws EntityNotFoundException ;
	public List<TacheResponseDto> getAllTask();
    public TacheResponseDto showDetailsOfAtask (Long id) throws EntityNotFoundException;
    public List<TacheResponseDto> taskAccordingToStatus( String status);
    public OrderResponseDto deleteTask(Long id) throws EntityNotFoundException ;
    public List<TacheResponseDto> tachePourTravailleur(NombreRequestDto nombreRequestDto);
    //public Integer nombreTache(TacheRequestDto tacheRequestDto );
    public String changeStatutTache(Long id, String statut) throws EntityNotFoundException;
    public List<TacheResponseDto> tacheFonctionDePeriodEtStatut(PeriodRequestDto periodRequestDto );
    public boolean ajoutCoordoneeGoe(String titre, String ip) throws EntityNotFoundException, GeoIp2Exception, IOException;
    public List<TacheResponseDto> getAllTacheUtilisateurByStatut(NombreRequestDto nombreRequestDto);
    public List<TacheResponseDto> getAllTacheUserForSprint(NombreRequestDto nombreRequestDto);
    public CalculNombreHeureResponseDto nombreHeure(NombreRequestDto nombreRequestDto) throws InvalidEntityException;
    
    public List<StatistiqueResponseDto> statistiqueOfTaskForSprint(Long sprintId) throws EntityNotFoundException;
    public List<TacheResponseDto> tachePourSprint(Long sprintId);
    public List<TacheResponseDto> sousTachesDuneTache(Long tacheParentId);
    
    public OrderResponseDto addTacheForUser(AddTaskRequestDTO addTaskRequestDTO) throws EntityNotFoundException;
    public OrderResponseDto changeTacheForUser(AddTaskRequestDTO addTaskRequestDTO) throws EntityNotFoundException;
    public List<Tache> getAllTacheUser(Long utilisateurId);
}
