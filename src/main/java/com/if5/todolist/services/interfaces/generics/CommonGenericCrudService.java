package com.if5.todolist.services.interfaces.generics;

import com.if5.todolist.models.entities.AuditModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 3:11 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.interfaces.generics
 **/
public interface CommonGenericCrudService<T extends AuditModel> {
    /**
     * Checks if a records having a given Id exists in database .
     *
     * @param id the entity's Id.
     * @return True if exists and false otherwise.
     */
    boolean existsById(Long id);

    /**
     * @param listeIds liste des identifiant de l'objet
     * @return true s'il en existe et false le cas echeant
     * @apiNote verifie s'il existe en BD des enregistrement avec ces id
     */
    boolean existsAllById(List<Long> listeIds);

    /**
     * Save a obj of a given Type .
     *
     * @param obj the entity to save.
     * @return the persisted entity.
     */
    T save(T obj);

    /**
     * Get all the records of this entity.
     *
     * @return the list of entities.
     */
    List<T> findAll();

    /**
     * Get all the records of this entity.
     *
     * @return the list of entities paged.
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Get the a record from DB using it's ID
     *
     * @param id the id (Long) of the entity.
     * @return the entity.
     */
    Optional<T> findById(Long id);

    /**
     * Get the a record from DB using it's ID
     *
     * @param id the id (Long) of the entity.
     * @return the entity.
     */
    T getOne(Long id);

    /**
     * Delete the a record from DB using its ID.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
