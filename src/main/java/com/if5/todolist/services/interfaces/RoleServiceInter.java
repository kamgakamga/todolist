package com.if5.todolist.services.interfaces;

import com.if5.todolist.dtos.requets.RoleRequestDto;
import com.if5.todolist.dtos.responses.RoleResponseDto;
import com.if5.todolist.exceptions.DuplicationEntityException;

import java.util.List;

public interface RoleServiceInter {

    public RoleResponseDto sauveRole(RoleRequestDto roleDto) throws DuplicationEntityException; 
	public List<RoleResponseDto> getAllRole();
    
}
