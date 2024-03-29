package com.if5.todolist.services.implementations;

import com.if5.todolist.dtos.requets.application.projet.ProjetRequestDto;
import com.if5.todolist.dtos.responses.application.projet.ProjetResponseDto;
import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.models.entities.EtatTache;
import com.if5.todolist.models.entities.Projet;
import com.if5.todolist.models.entities.Utilisateur;
import com.if5.todolist.repositories.EtatTacheRepository;
import com.if5.todolist.repositories.ProjetRepository;
import com.if5.todolist.repositories.UtilisateurRepository;
import com.if5.todolist.services.interfaces.ProjetServiceInter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ProjetServiceImp implements ProjetServiceInter{
	
	 private final ProjetRepository projetRepository;
	 private final UtilisateurRepository utilisateurRepository;
	 private final EtatTacheRepository etatTacheRepository;

	public ProjetServiceImp(ProjetRepository projetRepository,
							UtilisateurRepository utilisateurRepository,
							EtatTacheRepository etatTacheRepository) {
		this.projetRepository = projetRepository;
		this.utilisateurRepository = utilisateurRepository;
		this.etatTacheRepository = etatTacheRepository;
	}

	@Override
	public ProjetResponseDto saveProjet(ProjetRequestDto projetRequestDto) throws DuplicationEntityException, EntityNotFoundException {
		
		Projet projet;
		System.out.println(projetRequestDto.toString());
		if(projetRequestDto.getId() != null && projetRequestDto.getId() > 0 ) {
			projet = projetRepository.findById(projetRequestDto.getId()).orElseThrow(
					()->new EntityNotFoundException("Projet à modifier est introuvable"));
			return ProjetResponseDto.buildDtoFromEntity(projetRepository.save(ProjetRequestDto.buildUpdate(projetRequestDto, projet)));
		}
		Projet p = projetRepository.findByNomProjetIgnoreCase(projetRequestDto.getNomProjet());
		if(!Objects.equals(p, null) && Objects.isNull(projetRequestDto.getId())){
			throw new DuplicationEntityException("un projet existe déja avec ce nom");
		}
		 List<EtatTache> etatTaches = new ArrayList<>();
		 List<Long> etatId = projetRequestDto.getEtatId().isEmpty() ? new ArrayList<>() : projetRequestDto.getEtatId();
		 
	     if(projetRequestDto.getEtatId().isEmpty()) {
	    	 etatTaches = etatTacheRepository.findAllByDefaultValue(true);
	     }else {
			for(Long etatTacheId :etatId) {
				
				EtatTache et= etatTacheRepository.findById(etatTacheId).orElse(null);
				if(et != null) {
					etatTaches.add(et);
				}
			}
	     }
		
		List<Long> utilisateursId = projetRequestDto.getUtilisateurs();
		List<Utilisateur> utilisateurs = new ArrayList<>(); 
		for(Long utilisateurId :utilisateursId) {
			
			Utilisateur u = utilisateurRepository.findById(utilisateurId).orElse(null);
			
			if(u != null) {
				utilisateurs.add(u);
			}
		}
		Utilisateur responsableProjet =utilisateurRepository.findById(projetRequestDto.getResponsableId()).orElse(null);
		if(responsableProjet == null) {
			throw new EntityNotFoundException("Le responsable choisi n'est pas un utilisateur valide veillez le changez");
		}
		
		 projet= ProjetRequestDto.buildEntityFromDto(projetRequestDto, utilisateurs, etatTaches, responsableProjet );
		
		return ProjetResponseDto.buildDtoFromEntity(projetRepository.save(projet))  ;
	}

	@Override
	public String deleteProjet(Long id) throws EntityNotFoundException {
	
		Projet p = projetRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Projet introuvable avec l'ID "+id +" non Trouver"));
		projetRepository.delete(p);
		return "Ce projet est definitivement suprimer en BD";
	}

	@Override
	public Page<ProjetResponseDto> getAllProjet(String keyword, Pageable pageRequest) {
		return ProjetResponseDto.buildPageDtoFromPageEntity(projetRepository.findAllByKeyWord(keyword, pageRequest));
	}

	@Override
	public ProjetResponseDto getProjet(Long id) throws EntityNotFoundException {
		Projet projet = projetRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Projet introuvable avec l'Id "+id));
		return ProjetResponseDto.buildDtoFromEntity(projet);
	}
	
	


	
}