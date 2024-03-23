package com.if5.todolist.dtos.responses.systeme;

import com.if5.todolist.models.entities.systeme.Fenetre;
import com.if5.todolist.models.entities.systeme.ModuleSysteme;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:15 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.models.dtos.responses.systeme
 **/
@Data
@Builder
@ToString
public class ModuleFenetresDTO {
    private ModuleSystemeDTO module;
    List<FenetreDTO> fenetres;

    public static ModuleFenetresDTO buildFromEntities(ModuleSysteme module, List<Fenetre> fenetres) {
        return ModuleFenetresDTO.builder()
                .module(ModuleSystemeDTO.buildFromEntity(module))
                .fenetres(FenetreDTO.buildFromEntityList(fenetres))
                .build();
    }
}
