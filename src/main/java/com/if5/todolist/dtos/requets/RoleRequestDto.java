package com.if5.todolist.dtos.requets;

import com.if5.todolist.models.entities.Role;
import lombok.*;

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
