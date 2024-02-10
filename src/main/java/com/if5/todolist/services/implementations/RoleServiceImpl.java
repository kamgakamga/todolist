package com.if5.todolist.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.models.dtos.role.RoleRequestDto;
import com.if5.todolist.models.dtos.role.RoleResponseDto;
import com.if5.todolist.models.entities.Role;
import com.if5.todolist.repositories.RoleRepository;
import com.if5.todolist.services.interfaces.RoleServiceInter;

@Service
public class RoleServiceImpl implements RoleServiceInter {

    @Autowired private RoleRepository roleRepository;

    @Override
    public RoleResponseDto sauveRole(RoleRequestDto roleRequestDto) throws DuplicationEntityException {

      Role role = roleRepository.findByNom(roleRequestDto.getNom()).orElse(null); 
        if(role != null){
            throw new DuplicationEntityException("Un role avec le nom "+roleRequestDto.getNom()+" existe deja dans la base de donnée");
          } 
    
       Role r = RoleRequestDto.buildRoleFromDto(roleRequestDto);
       System.out.println(r.getNom());
       roleRepository.save(r);
       
        return RoleResponseDto.buildDtoFromRole(r);
    }

    

    @Override
    public List<RoleResponseDto> getAllRole() {
        
        return null;
    }
    
    
    
}
