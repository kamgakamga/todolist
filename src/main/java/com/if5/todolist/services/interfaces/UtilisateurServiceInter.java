package com.if5.todolist.services.interfaces;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.models.dtos.tache.OrderResponseDto;
import com.if5.todolist.models.dtos.utilisateur.UtilisateurRequestDto;
import com.if5.todolist.models.dtos.utilisateur.UtilisateurResponseDto;
import com.if5.todolist.models.entities.Utilisateur;

public interface UtilisateurServiceInter {

    public UtilisateurResponseDto saveUser(UtilisateurRequestDto utilisateurRequestDto, String siteURL)
    		        throws EntityNotFoundException, UnsupportedEncodingException, MessagingException;
	public List<UtilisateurResponseDto>  getAllUsers();
	public Utilisateur getUser(Long id) throws InvalidEntityException;
	public UtilisateurResponseDto updateUser(UtilisateurRequestDto utilisateurRequestDto) throws EntityNotFoundException;
	public UtilisateurResponseDto showUserDetails(Long userId) throws EntityNotFoundException;
	public String addRoleToUser(String userName,String roleName) throws EntityNotFoundException;
	public void sendVerificationEmail(Utilisateur Utilisateur, String siteURL)    	
			throws MessagingException, UnsupportedEncodingException ;
    public boolean verify(String verificationCode);
    public void save(MultipartFile file);
    public OrderResponseDto deleteUser(Long id) throws EntityNotFoundException;
    public OrderResponseDto deleteRole(Long id) throws InvalidEntityException;
    public OrderResponseDto deleteRoleFromUtilisateur(Long utilisateurId, Long roleId) throws EntityNotFoundException;
    
}
