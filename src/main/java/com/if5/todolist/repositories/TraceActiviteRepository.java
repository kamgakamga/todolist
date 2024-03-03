package com.if5.todolist.repositories;

import com.if5.todolist.models.entities.TraceActivite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraceActiviteRepository extends JpaRepository<TraceActivite,Long> {
    
}
