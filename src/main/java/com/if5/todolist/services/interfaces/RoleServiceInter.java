package com.if5.todolist.services.interfaces;

import java.util.List;

import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.models.dtos.role.RoleRequestDto;
import com.if5.todolist.models.dtos.role.RoleResponseDto;

public interface RoleServiceInter {

    public RoleResponseDto sauveRole(RoleRequestDto roleDto) throws DuplicationEntityException; 
	public List<RoleResponseDto> getAllRole();
    
}
