package com.if5.todolist.services.interfaces;

import com.if5.todolist.dtos.requets.UtilisateurRequestDto;
import com.if5.todolist.dtos.responses.UtilisateurResponseDto;
import com.if5.todolist.dtos.responses.application.tache.OrderResponseDto;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.models.entities.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface UtilisateurServiceInter {

    public UtilisateurResponseDto saveUser(UtilisateurRequestDto utilisateurRequestDto, String siteURL)
    		        throws EntityNotFoundException, UnsupportedEncodingException, MessagingException;
	public Page<UtilisateurResponseDto> getAllUsers(String keyword, Pageable pageable);
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
