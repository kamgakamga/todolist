package com.if5.todolist.services.implementations.generics;

import com.if5.todolist.models.entities.AuditModel;
import com.if5.todolist.services.interfaces.generics.CommonGenericCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 3:14 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.implementations.generics
 **/
@RequiredArgsConstructor
public class CommonGenericCrudServiceImpl<R extends JpaRepository<M, Long>, M extends AuditModel>  implements CommonGenericCrudService<M> {

    protected final R repository;

    /**
     * Checks if a records having a given Id exists in database .
     *
     * @param id the entity's Id.
     * @return True if exists and false otherwise.
     */
    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    /**
     * @param listeIds liste des identifiant de l'objet
     * @return true s'il en existe et false le cas echeant
     * @apiNote verifie s'il existe en BD des enregistrement avec ces id
     */
    @Override
    public boolean existsAllById(List<Long> listeIds) {
        boolean existAll = true;
        int i = 0;
        while (existAll && i < listeIds.size()) {
            existAll = repository.existsById(listeIds.get(i));
            i++;
        }
        return existAll;
    }

    /**
     * Save a obj of a given Type .
     *
     * @param obj the entity to save.
     * @return the persisted entity.
     */
    @Override
    @Transactional
    public M save(M obj) {
        return repository.save(obj);
    }

    /**
     * Get all the records of this entity.
     *
     * @return the list of entities.
     */
    @Override
    public List<M> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<M> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Get the a record from DB using it's ID
     *
     * @param id the id (Long) of the entity.
     * @return the entity.
     */
    @Override
    public Optional<M> findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Get the a record from DB using it's ID
     *
     * @param id the id (Long) of the entity.
     * @return the entity.
     */
    @Override
    public M getOne(Long id) {
        Optional<M> m = repository.findById(id);
        if( m.isPresent()) {
            return m.get();
        }
        return null;
    }

    /**
     * Delete the a record from DB using its ID.
     *
     * @param id the id of the entity.
     */
    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);

    }
}
