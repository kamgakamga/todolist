package com.if5.todolist.services.implementations;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.models.dtos.tache.AddTaskRequestDTO;
import com.if5.todolist.models.dtos.tache.CalculNombreHeureResponseDto;
import com.if5.todolist.models.dtos.tache.NombreRequestDto;
import com.if5.todolist.models.dtos.tache.OrderResponseDto;
import com.if5.todolist.models.dtos.tache.PeriodRequestDto;
import com.if5.todolist.models.dtos.tache.StatistiqueResponseDto;
import com.if5.todolist.models.dtos.tache.TacheRequestDto;
import com.if5.todolist.models.dtos.tache.TacheResponseDto;
import com.if5.todolist.models.dtos.utilisateur.UtilisateurRequestDto;
import com.if5.todolist.models.dtos.utilisateur.UtilisateurResponseDto;
import com.if5.todolist.models.entities.Attribution;
import com.if5.todolist.models.entities.EtatTache;
import com.if5.todolist.models.entities.Sprint;
import com.if5.todolist.models.entities.Statistique;
import com.if5.todolist.models.entities.Tache;
import com.if5.todolist.models.entities.Utilisateur;
import com.if5.todolist.models.enumerations.ResponseStatus;
import com.if5.todolist.repositories.AttributionRepository;
import com.if5.todolist.repositories.EtatTacheRepository;
import com.if5.todolist.repositories.SprintRepository;
import com.if5.todolist.repositories.TacheRepository;
import com.if5.todolist.repositories.UtilisateurRepository;
import com.if5.todolist.services.interfaces.TacheServiceInter;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

@Service
@Transactional
public class TacheServiceImp implements TacheServiceInter {

              private DatabaseReader dbReader;
   @Autowired private TacheRepository tacheRepository;
   @Autowired private UtilisateurRepository utilisateurRepository;
   @Autowired private SprintRepository sprintRepository;
   @Autowired private EtatTacheRepository etatTacheRepository;
   @Autowired private AttributionRepository attributionRepository; 
  
public TacheServiceImp() throws IOException {
       File database = new File(System.getProperty("user.home") +"/GeoLite2/GeoLite2-City.mmdb" );
      
      dbReader = new DatabaseReader.Builder(database).build();
   }
   
@Override
public TacheResponseDto saveTask(TacheRequestDto tacheRequestDto) throws EntityNotFoundException {

    //Tache tache = tacheRepository.findByLibelle(tacheRequestDto.getLibelle()).orElse(null);
   Tache tacheToSave;
    if(tacheRequestDto.getId() != null && tacheRequestDto.getId() > 0 ) {
    	tacheToSave = tacheRepository.findById(tacheRequestDto.getId()).orElseThrow(
				()->new EntityNotFoundException("Projet à modifier est introuvable"));
		return TacheResponseDto.buildTacheFromDto(tacheRepository.save(TacheRequestDto.builUpdateTache(tacheRequestDto, tacheToSave)));
		
	}
	
    List<EtatTache> listEtatTache = new ArrayList<>();
     
   /* if( tache != null){
        throw new EntityNotFoundException("Cette tache existe deja dans la base de donnée");
    }*/
    
    Long tacheParentId = tacheRequestDto.getTacheParente() == null ? 0 :  tacheRequestDto.getTacheParente() ;
    Long userId = tacheRequestDto.getUtilisateur() == null ? 0 : tacheRequestDto.getUtilisateur();
   
    Tache tacheParente = tacheRepository.findById(tacheParentId).orElse(null);

    Utilisateur utilisateur = utilisateurRepository.findById(userId).orElse(null);
    
    if(tacheRequestDto.getDateDebut().after(tacheRequestDto.getDateFin())){

        throw new EntityNotFoundException("La date de debut de la tache ne peux pas etre situé apres la date de fin de la tache");
    }
    
    Long sprintId= tacheRequestDto.getSprintId() == null ? 0 : tacheRequestDto.getSprintId();
    
    Sprint sprint = sprintRepository.findById(sprintId).orElse(null);
    
    if( sprintId > 0 && sprint == null) {
    	throw new EntityNotFoundException("Aucun sprint trouver dans la BD");
    }
    if(sprint != null) {
    	   listEtatTache = sprint.getEtatTaches();
    	}
   
   String i = tacheRequestDto.getStatut();
   
 /*   if(!(listEtatTache.contains(etatTacheRepository.findByLibelleIgnoreCase(tacheRequestDto.getStatut())))) {
    	 throw new EntityNotFoundException("L'etat de la tache que vous avez renseiger n'est pas correcte");   	
    	 }*/
   
    tacheToSave=TacheRequestDto.builTacheFromDto(tacheRequestDto, utilisateur, tacheParente, sprint);   
	 tacheRepository.save(tacheToSave);     
    return TacheResponseDto.buildTacheFromDto(tacheToSave);   
}

@Override
public List<TacheResponseDto> getAllTask() {
List<Tache> allTache =tacheRepository.findAll();
    return TacheResponseDto.buildListDtoFromListTache(allTache);
}


@Override
public TacheResponseDto showDetailsOfAtask(Long id) throws EntityNotFoundException {
    System.out.println(id);
		 Tache optionalTask = tacheRepository.findById(id).orElse(null);
	  System.out.println(optionalTask);
	  if(optionalTask == null) { 
		  throw new EntityNotFoundException("La tache avec l'ID " +id + " n'existe pas en BDD"); 
	  }
	  return TacheResponseDto.buildTacheFromDto(optionalTask); 
	  }
	  

@Override
public List<TacheResponseDto> taskAccordingToStatus(String status) {
     List<Tache> listOfTask = tacheRepository.findAllByStatut(status);
   
  return  TacheResponseDto.buildListDtoFromListTache(listOfTask);
}

@Override
public OrderResponseDto deleteTask(Long id) throws EntityNotFoundException {
    Tache tache = tacheRepository.findById(id).orElseThrow(()
    		-> new EntityNotFoundException("Tache non trouver dans la BD"));
		  
    if(tache.getTacheParente() != null) {
        throw new EntityNotFoundException("Cette tache ne peut etre supprimer car");
    }
  return new OrderResponseDto("Operation reussie",ResponseStatus.SUCCES, "La tache est supprimer avec succès");
}


@Override
public List<TacheResponseDto> tachePourTravailleur(NombreRequestDto nombreRequestDto)  {
    
    List<Tache> tacheFromDB = tacheRepository.findAllTacheUser(nombreRequestDto.getDateDebut(), nombreRequestDto.getDateFin(),nombreRequestDto.getReponsableTacheId(),nombreRequestDto.getStatut() );

return TacheResponseDto.buildListDtoFromListTache(tacheFromDB);  
}


@Override
public List<TacheResponseDto> tacheFonctionDePeriodEtStatut(PeriodRequestDto periodRequestDto ) {

     Date dateDebut = periodRequestDto.getDateDebut();
     Date dateFin = periodRequestDto.getDateFin();
     String statut =periodRequestDto.getStatut();
      List<Tache> tacheRealisee = tacheRepository.findAllInPeriod(dateDebut,dateFin, statut);
    
    return TacheResponseDto.buildListDtoFromListTache(tacheRealisee);
}

@Override
public String changeStatutTache(Long id, String statut) throws EntityNotFoundException {

    Tache tache = tacheRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Cette tache n'existe pas dans la BD"));
	 
	  if (tache.getSprint().getEtatTaches().contains(statut)) {
	      tache.setStatut(statut);
          tacheRepository.save(tache);
          return "Le status actuel de cette tache est: "+statut;
	  }
	  
	  return "Le status actuel de cette tache est: "+statut;
}

@Override
public boolean ajoutCoordoneeGoe(String titre, String ip) throws EntityNotFoundException, GeoIp2Exception, IOException {
     
    Tache tache = tacheRepository.findByLibelle(titre).orElse(null);

    if(tache == null){
        throw new EntityNotFoundException("Cette tache n'existe pas!");
    }
    
      InetAddress ipAddress = InetAddress.getByName(ip);
      CityResponse response = dbReader.city(ipAddress);
      
      String lieuDeRappel = response.getCity().getName();

      String latitude = response.getLocation().getLatitude().toString();
      Double lat =Double.parseDouble(latitude);

      String longitude = response.getLocation().getLongitude().toString();
      Double log =Double.parseDouble(longitude);

      tache.setLatitude(lat);
      tache.setLongitue(log);
      tache.setLieuDeRappel(lieuDeRappel);


      tacheRepository.save(tache);

      System.out.println(lieuDeRappel);


      return true;
  }

@Override
public List<TacheResponseDto> getAllTacheUtilisateurByStatut(NombreRequestDto nombreRequestDto) {
	return TacheResponseDto.buildListDtoFromListTache(attributionRepository.findAlltaskUser( nombreRequestDto.getReponsableTacheId()));
}

@Override
public List<TacheResponseDto> getAllTacheUserForSprint(NombreRequestDto nombreRequestDto) {
	
	System.out.println("sprint=> "+nombreRequestDto.getSprint());	
	System.out.println("user=> "+nombreRequestDto.getReponsableTacheId());
	return TacheResponseDto.buildListDtoFromListTache(tacheRepository.getAllSpritForUser(nombreRequestDto.getSprint(), nombreRequestDto.getReponsableTacheId()));
}

@Override
public List<Tache> getAllTacheUser(Long utilisateurId) {
	return attributionRepository.getAllTacheForUser(utilisateurId);
}

@Override
public CalculNombreHeureResponseDto nombreHeure(NombreRequestDto nombreRequestDto) throws InvalidEntityException {
	List<Tache> taches = tacheRepository.findAllTacheUser(nombreRequestDto.getDateDebut(), nombreRequestDto.getDateFin(), nombreRequestDto.getReponsableTacheId(), nombreRequestDto.getStatut());
	
	if(taches == null) {
		throw new InvalidEntityException("Pas de tache pour cet utilisateur");
	}
	
	long nombreHeure= 0;
	for(Tache t: taches) {
			long diff =(t.getDateFin().getTime() - t.getDateDebut().getTime());
		
		    TimeUnit time = TimeUnit.HOURS;
		 long difference  = time.convert(diff, TimeUnit.MILLISECONDS);
		 nombreHeure += difference;   	
	}
	String responsableTache =utilisateurRepository.findById(nombreRequestDto.getReponsableTacheId()).get().getUserName();
	String etatTache = nombreRequestDto.getStatut();
	List<String> libelleTaches = taches.stream()
			                           .map(e->e.getLibelle())
			                           .collect(Collectors.toList());
	
	
	
	String reponse = "Mr/Mme "+responsableTache+ " pour le statut "+etatTache+
			         " doit effectuer "+nombreHeure+" Heure(s) pour les taches "+libelleTaches+".";
	
	
	return new CalculNombreHeureResponseDto("Opération reussi", nombreHeure, reponse);
}

@Override
public List<StatistiqueResponseDto> statistiqueOfTaskForSprint(Long sprintId) throws EntityNotFoundException {
	
	Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(()->
	new EntityNotFoundException("Ce sprint n'existe pas dans la BD"));
	
	List<Tache> listTacheForSprint = tacheRepository.findAllBySprint( sprintId);
	
	List<EtatTache> listEtatTacheSprint = sprint.getEtatTaches();
	List<String>  listLibelleEtatTacheSprint = listEtatTacheSprint.stream()
	                                           .map(e->e.getLibelle())
	                                           .collect(Collectors.toList());
	
	List<List<Tache>> listFinal= new ArrayList<>();

	 for(String statutSprint :listLibelleEtatTacheSprint) {
		 
		 List<Tache> nouvelList = new ArrayList<>(); 
		 for(Tache tacheSprint:listTacheForSprint ) {
			 if(tacheSprint.getStatut().equals(statutSprint)) {
				 nouvelList.add(tacheSprint);
			 }
		 }
		 listFinal.add(nouvelList);
	 }
	 Statistique statistique = new Statistique();
	 List<StatistiqueResponseDto> statistiquesResponse = new ArrayList<>();

	 for(List<Tache> lf:listFinal) {
		 Double d = (Double.valueOf(lf.size())/listTacheForSprint.size())*100;
		 double pouc = d;
		 double poucentage = Math.round(pouc);
		   statistique.setNombreTache(lf.size());
		   statistique.setPourcentage(poucentage);
		   statistique.setListTache(lf); 
		   int nombreDeTache =statistique.getNombreTache();
		   double pourcentage =statistique.getPourcentage();
		   List<Tache> listeTache =statistique.getListTache();
		   Statistique st = new Statistique(nombreDeTache, pourcentage, listeTache);
		   
		   statistiquesResponse.add( StatistiqueResponseDto.buildDtoFromEntity(st));
		
	 }	
    return statistiquesResponse;    	 	
}

@Override
public OrderResponseDto addTacheForUser(AddTaskRequestDTO addTaskRequestDTO) throws EntityNotFoundException {
	
	Utilisateur user = utilisateurRepository.findById(addTaskRequestDTO.getNewUserId()).orElseThrow(()
			-> new EntityNotFoundException("Utilisateur non existant dans la BD")); 

	Tache tache =tacheRepository.findById(addTaskRequestDTO.getTacheId()).orElseThrow(()
			-> new EntityNotFoundException("Tache non existante dans la BD"));
	
	Attribution  newUserTache = new Attribution();
	
	newUserTache.setTache(tache);
	newUserTache.setUtilisateur(user);
	newUserTache.setDateAttribution(LocalDateTime.now());
	
	attributionRepository.save(newUserTache);
	
	
	//return new OrderResponseDto("Opération réussie",ResponseStatus.SUCCES,user.getListTacheUtilisateur().stream().map(e->e.getTache().getLibelle()));
	
	return  new OrderResponseDto("Opération réussie",ResponseStatus.SUCCES,"La tache: " 
            +tache.getLibelle()+ " à été affecter à : "
            +user.getPrenom()+" "+ user.getNom().toUpperCase()+" avec succès.");
	
}

@Override
public OrderResponseDto changeTacheForUser(AddTaskRequestDTO addTaskRequestDTO) throws EntityNotFoundException {
	
	Utilisateur newUser = utilisateurRepository.findById(addTaskRequestDTO.getNewUserId()).orElseThrow(()
			-> new EntityNotFoundException("Utilisateur non existant dans la BD"));
	Utilisateur oldUser = utilisateurRepository.findById(addTaskRequestDTO.getOldUserId()).orElseThrow(()
			-> new EntityNotFoundException("Utilisateur non existant dans la BD"));
	Attribution updateAttribution = attributionRepository.findById(addTaskRequestDTO.getAttributionId()).orElseThrow(
			()-> new EntityNotFoundException("Attribution non existante dans la BD"));
	Tache tache =tacheRepository.findById(addTaskRequestDTO.getTacheId()).orElseThrow(()
			-> new EntityNotFoundException("Tache non existante dans la BD"));
	
	updateAttribution.setTache(updateAttribution.getTache());
	updateAttribution.setUtilisateur(newUser);
	updateAttribution.setDateAttribution(LocalDateTime.now());
	
	attributionRepository.save(updateAttribution);
	
	
	URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(updateAttribution.getId())
            .toUri();
	
	System.out.println("----------------------------");
	System.out.println(location);
	System.out.println("----------------------------");
	
	
	return  new OrderResponseDto("Opération réussie",ResponseStatus.SUCCES,"La tache: " 
	                            +tache.getLibelle()+ " à été affecter à : "
			                    +newUser.getPrenom()+" "+newUser.getNom().toUpperCase()+" en remplacement de "
	                            +oldUser.getPrenom()+" "+oldUser.getNom().toUpperCase()+".");
}

@Override
public List<TacheResponseDto> tachePourSprint(Long sprintId) {
	List<Tache> listeTachePourSprint = tacheRepository.findAllBySprint(sprintId);
	return TacheResponseDto.buildListDtoFromListTache(listeTachePourSprint);
}

@Override
public List<TacheResponseDto> sousTachesDuneTache(Long tacheParentId) {
	List<Tache> sousTachesDuneTache=tacheRepository.findAllByTacheParentId(tacheParentId);
	return TacheResponseDto.buildListDtoFromListTache(sousTachesDuneTache);
}

}
    

