package com.if5.todolist.models.dtos.responses.systeme;

import com.if5.todolist.models.entities.systeme.ModuleSysteme;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

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
public class ModuleSystemeDTO {
    private Long id;
    @NotNull
    private String nomModule;
    private String description;

    public static ModuleSysteme buildFromDTO(ModuleSystemeDTO dto) {
        return ModuleSysteme.builder()
                .nomModule(dto.getNomModule())
                .description(dto.getDescription())
                .id(dto.getId())
                .build();
    }

    public static ModuleSystemeDTO buildFromEntity(ModuleSysteme entity) {
        return ModuleSystemeDTO.builder()
                .nomModule(entity.getNomModule())
                .description(entity.getDescription())
                .id(entity.getId())
                .build();
    }
}
