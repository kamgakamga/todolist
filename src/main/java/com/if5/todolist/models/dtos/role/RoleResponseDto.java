package com.if5.todolist.models.dtos.role;

import java.util.List;
import java.util.stream.Collectors;

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
        return Role.builder()
                      .id(dto.getId())
                      .nom(dto.getNom())
                      .build();
    }


    public static List<RoleResponseDto> buildListDtoFromListRole(List<Role> listRole){
        return listRole.stream().map(RoleResponseDto::buildDtoFromRole).collect(Collectors.toList());
                     
    }

    public static List<Role> buildListRoleFromListDto(List<RoleResponseDto> listRoleDto){
        return listRoleDto.stream().map(RoleResponseDto::buildRoleFromDto).collect(Collectors.toList());
                     
    }
}
