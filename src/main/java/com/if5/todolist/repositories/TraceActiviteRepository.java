package com.if5.todolist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.if5.todolist.models.entities.TraceActivite;

public interface TraceActiviteRepository extends JpaRepository<TraceActivite,Long> {
    
}
