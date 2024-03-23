package com.if5.todolist.services.implementations;

import com.if5.todolist.dtos.requets.SprintRequestDto;
import com.if5.todolist.dtos.requets.SprintStatistiqueRequestDto;
import com.if5.todolist.dtos.responses.SprintResponseDto;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.exceptions.ResourceNotFoundException;
import com.if5.todolist.models.entities.EtatTache;
import com.if5.todolist.models.entities.Projet;
import com.if5.todolist.models.entities.Sprint;
import com.if5.todolist.models.entities.Tache;
import com.if5.todolist.repositories.EtatTacheRepository;
import com.if5.todolist.repositories.ProjetRepository;
import com.if5.todolist.repositories.SprintRepository;
import com.if5.todolist.repositories.TacheRepository;
import com.if5.todolist.services.interfaces.SprintServiceInter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SprintServiceImp implements SprintServiceInter{

	private final SprintRepository sprintRepository;
	private final ProjetRepository projetRepository;
	private final TacheRepository tacheRepository;
	private final EtatTacheRepository etatTacheRepository;

	public SprintServiceImp(SprintRepository sprintRepository,
							ProjetRepository projetRepository,
							TacheRepository tacheRepository,
							EtatTacheRepository etatTacheRepository) {
		this.sprintRepository = sprintRepository;
		this.projetRepository = projetRepository;
		this.tacheRepository = tacheRepository;
		this.etatTacheRepository = etatTacheRepository;
	}

	@Override
	public SprintResponseDto saveSprint(SprintRequestDto sprintRequestDto) throws EntityNotFoundException, InvalidEntityException {

		List<EtatTache> etatTaches = new ArrayList<>();
		List<Tache> taches = new ArrayList<>();
		List<Long> tachesId = sprintRequestDto.getTachesId();
		Projet projet = projetRepository.findById(sprintRequestDto.getProjetId()).orElse(null);

		if((sprintRequestDto.getEtatId().isEmpty() || Objects.isNull(sprintRequestDto.getEtatId()))
				&& Objects.nonNull(projet)  && !projet.getEtatTaches().isEmpty()) {
			etatTaches.addAll(projet.getEtatTaches());


		}else if(!sprintRequestDto.getEtatId().isEmpty()) {
			for(Long etatTacheId :sprintRequestDto.getEtatId()) {
				EtatTache etatTacheForSprint= etatTacheRepository.findById(etatTacheId).orElse(null);
				if(etatTacheForSprint != null) {
					etatTaches.add(etatTacheForSprint);
				}
			}
		} else {
			etatTaches = etatTacheRepository.findAllByDefaultValue(true);
		}

		if(!tachesId.isEmpty()) {
			for(Long tacheId :tachesId) {
				Tache t= tacheRepository.findById(tacheId).orElse(null);
				if(Objects.nonNull(t)) {
					taches.add(t);
				}
			}
		}

		Sprint sprint = SprintRequestDto.buildEntityFromDto(sprintRequestDto,projet, taches , etatTaches);


		sprintRepository.save(sprint);
		return   SprintResponseDto.buildDtoFromEntity(sprint);

	}

	@Override
	public String deleteSprint(Long id) throws EntityNotFoundException {

		Sprint p = sprintRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Sprint avec l'ID "+id +" non Trouver"));
		sprintRepository.delete(p);
		return "Ce Sprint est definitivement suprimer en BD";
	}

	@Override
	public Page<SprintResponseDto> getAllSprint(String keyword, Pageable pageable) {
		return SprintResponseDto.buildPageDtoFromPageEntity(sprintRepository.findAllByKeyWord(keyword,pageable));
	}

	@Override
	public List<SprintResponseDto> sprintParEtat(String libelle) {
		//	List<Sprint> sprintParEtat = sprintRepository.findAllByEtat(libelle);
		return null;
	}

	@Override
	public List<SprintStatistiqueRequestDto> statistique(String etatTache) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addSprintToProjet(Long idSprint, Long idProjet) {
		Sprint sprint = sprintRepository.findById(idSprint).orElseThrow(() -> new ResourceNotFoundException("Sprint","ID",idSprint));
		Projet projet = projetRepository.findById(idProjet).orElseThrow(() -> new ResourceNotFoundException("Projet","ID",idProjet));;
		List<Sprint> ls = projet.getSprints();
		ls.add(sprint);
		projet.setSprints(ls);
		sprint.setProjet(projet);
		projetRepository.save(projet);
		sprintRepository.save(sprint);
		return "ok";
	}

	public Projet getProjet(Long id){
		return projetRepository.getById(id);
	}

	@Override
	public List<SprintResponseDto> sprintForProjet(Long id) {


		return SprintResponseDto.buildListDtoFromListSprint(sprintRepository.findAllByProjetId(id));
	}

	@Override
	public SprintResponseDto getOneSprint(Long id) {
		Sprint response = sprintRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sprint","ID",id));
		return SprintResponseDto.buildDtoFromEntity(response);
	}
}
