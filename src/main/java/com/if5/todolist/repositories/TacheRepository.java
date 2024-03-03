package com.if5.todolist.repositories;

import com.if5.todolist.models.entities.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TacheRepository  extends JpaRepository<Tache, Long>{

    public Optional<Tache> findByLibelle(String libelle);
    
    //Premiere méthode
  // @Query(value="SELECT T FROM Tache T WHERE T.dateDebut BETWEEN :dateDebut AND :dateFin AND s.statut=com.if5.todolist.models.enumerations.StatutTache.REALISEE")
  //deuxieme méthode 
 // @Query(value="SELECT s FROM Tache s WHERE s.dateDebut<=:dateDebut AND s.dateFin<=:dateFin AND s.statut=com.if5.todolist.models.enumerations.StatutTache.REALISEE")  
    @Query(value="SELECT T FROM Tache T WHERE  T.dateDebut BETWEEN :dateDebut AND :dateFin AND T.dateFin BETWEEN :dateDebut AND :dateFin AND T.statut=:statut")
    public List<Tache> findAllByStatutTache(@Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);
    
    public Tache findByStatut(String status);

    public List<Tache> findAllByStatut(String status);
    
    @Query(value="SELECT T FROM Tache T WHERE  T.dateDebut BETWEEN ?1 AND ?2 AND T.dateFin BETWEEN ?1 AND ?2 AND T.statut=?3")
    public List<Tache> findAllInPeriod(Date startDate, Date endDate,String statut);
   
    //@Query(value="SELECT A FROM Tache A WHERE  A.dateDebut BETWEEN :dateDebut AND :dateFin AND A.dateFin BETWEEN :dateDebut AND :dateFin AND A.statut=:statut AND A.utilisateur.id=:utilisateur")
   // @Query(value="SELECT T FROM Tache T WHERE  T.dateDebut<=:dateDebut AND T.dateFin<=:dateFin AND T.statut=:statut AND T.utilisateur.id=:utilisateur")
    @Query("SELECT T FROM Tache T JOIN T.responsableTache AT JOIN AT.utilisateur U WHERE T.dateDebut<=:dateDebut AND T.dateFin<=:dateFin AND T.statut=:statut AND U.id=:utilisateur")
	public List<Tache> findAllTacheUser(@Param("dateDebut")Date dateDebut, @Param("dateFin")Date dateFin, @Param("utilisateur") Long utilisateur,@Param("statut") String statut);
   
   @Query("SELECT T FROM Tache T JOIN T.responsableTache AT JOIN AT.utilisateur U WHERE T.statut=?1 AND U.id=?2")
   public List<Tache> findAllUtilisateurAndStatut(String statut, Long utilisateur);
    
   // @Query(value="SELECT T FROM Tache T WHERE T.sprint.id=:sprint AND T.utilisateur.id=:utilisateur")
    @Query("SELECT T FROM Tache T JOIN T.responsableTache AT JOIN "
            +"AT.utilisateur U WHERE T.sprint.id=?1 AND U.id=?2")
    public List<Tache> getAllSpritForUser(Long sprint,Long utilisateur);

    @Query(value="SELECT T FROM Tache T WHERE T.sprint.id=?1")
	public List<Tache> findAllBySprint(Long sprintId);
    
    @Query(value="SELECT T FROM Tache T WHERE T.tacheParente.id=?1")
	public List<Tache> findAllByTacheParentId(Long sprintId);

   
    
    
}
