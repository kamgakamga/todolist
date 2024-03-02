package com.if5.todolist.models.dtos.role;

import com.if5.todolist.models.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleRequestDto {
    
    private Long id;
    private String nom;


    public static Role buildRoleFromDto(RoleRequestDto dto){
        return Role.RoleBuilder.aRole()
                      .nom(dto.getNom())
                      .build();
    }

    public static RoleRequestDto buildDtoFromRole(Role role){
        return RoleRequestDto.builder()
                      .nom(role.getNom())
                      .build();
    }
    
}
