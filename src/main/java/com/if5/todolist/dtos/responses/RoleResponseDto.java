package com.if5.todolist.dtos.responses;

import com.if5.todolist.models.entities.Role;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponseDto {
    
    private Long id;
    
    private String nom;


    public static RoleResponseDto buildDtoFromRole(Role role){
        return RoleResponseDto.builder()
                      .id(role.getId())
                      .nom(role.getNom())
                      .build();
    }

    public static Role buildRoleFromDto(RoleResponseDto dto){
        return Role.RoleBuilder.aRole()
                      .id(dto.getId())
                      .nom(dto.getNom())
                      .build();
    }


    public static List<RoleResponseDto> buildListDtoFromListRole(Set<Role> listRole){
        return listRole.stream().map(RoleResponseDto::buildDtoFromRole).collect(Collectors.toList());
                     
    }

    public static Set<Role> buildListRoleFromListDto(List<RoleResponseDto> listRoleDto){
        return listRoleDto.stream().map(RoleResponseDto::buildRoleFromDto).collect(Collectors.toSet());
                     
    }
}
