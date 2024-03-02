package com.if5.todolist.repositories.parametre;

import com.if5.todolist.models.entities.parametrages.VariableGlobale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 1:50 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.repositories.parametre
 **/
public interface VariableGlobaleRepository  extends JpaRepository<VariableGlobale, Long> {

    @Query("SELECT V FROM VariableGlobale V WHERE V.cle LIKE 'SMS_GATEWAY_%' ")
    List<VariableGlobale> listeVariableGlobaleSms();
    @Query("SELECT V FROM VariableGlobale V WHERE V.visible=1")
    List<VariableGlobale> listeVariablesAffichable();
    Optional<VariableGlobale> findTopByCle(String key);
}
