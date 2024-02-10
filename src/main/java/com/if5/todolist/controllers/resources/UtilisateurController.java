package com.if5.todolist.controllers.resources;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.if5.todolist.controllers.helpers.ExcelHelper;
import com.if5.todolist.controllers.utils.FileUploadUtil;
import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.models.dtos.role.ApiResponseMessage;
import com.if5.todolist.models.dtos.role.RoleRequestDto;
import com.if5.todolist.models.dtos.role.RoleResponseDto;
import com.if5.todolist.models.dtos.tache.OrderResponseDto;
import com.if5.todolist.models.dtos.utilisateur.UtilisateurRequestDto;
import com.if5.todolist.models.dtos.utilisateur.UtilisateurResponseDto;
import com.if5.todolist.models.entities.FileUploadResponse;
import com.if5.todolist.models.entities.Role;
import com.if5.todolist.models.entities.Utilisateur;
import com.if5.todolist.repositories.RoleRepository;
import com.if5.todolist.repositories.UtilisateurRepository;
import com.if5.todolist.services.interfaces.RoleServiceInter;
import com.if5.todolist.services.interfaces.UtilisateurServiceInter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/toDoList/v1")
@Api("API pour le CRUD des utilisateurs")
public class UtilisateurController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurController.class);
	
	

	@Autowired private UtilisateurServiceInter utilisateurServiceInter;
	@Autowired private RoleServiceInter roleServiceInter;
    @Autowired private UtilisateurRepository utilisateurRepository;
    @Autowired private RoleRepository roleRepository;



    @PostMapping("/save-user")
    @ApiOperation(value = "Enregistrer un utilisateur",  response = Utilisateur.class)
    public ResponseEntity<UtilisateurResponseDto> saveUser(
    		                                @RequestBody UtilisateurRequestDto utilisateurRequestDto,
    		                                HttpServletRequest request
    		                               
    		                                ) throws EntityNotFoundException, DuplicationEntityException,
                                                         MessagingException, IOException{
    	
      
        if(utilisateurRequestDto.getId() == null){
        	
            if(!utilisateurRequestDto.getPassword().equals(utilisateurRequestDto.getConfirmationPassword()) ||
            utilisateurRequestDto.getUserName() == null) { 
                         throw new EntityNotFoundException("mot de passe incorrect");
            }
                         Utilisateur user = utilisateurRepository.findByUserName(utilisateurRequestDto.getUserName());

           if(user!=null) {
                           throw new DuplicationEntityException("Ce nom d'utilisateur existe deja en base de donnée bien vouloir le changer");
               }

        }
        return new ResponseEntity<UtilisateurResponseDto>( utilisateurServiceInter.saveUser(utilisateurRequestDto,getSiteURL(request)),HttpStatus.CREATED);

    }

    @PostMapping("/save-role")
    @ApiOperation(value = "Enregistrer un role",  response = Role.class)
    public ResponseEntity<RoleResponseDto> saveRole(@RequestBody RoleRequestDto roleRequestDto) throws DuplicationEntityException{
    
        return ResponseEntity.ok(roleServiceInter.sauveRole(roleRequestDto));
    }
    

    @GetMapping("/users")
    @ApiOperation(value = "Lister tous les utilisateur")
    public ResponseEntity<List<UtilisateurResponseDto>> getAllUser(){
        return ResponseEntity.ok(utilisateurServiceInter.getAllUsers());
    }
 
    @GetMapping("/show-details/{userId}")
    @ApiOperation(value = "Affiche le detail d'un utilisateur")
    public ResponseEntity<UtilisateurResponseDto> showDetailsUser(@PathVariable Long userId) throws EntityNotFoundException{

        return ResponseEntity.ok(utilisateurServiceInter.showUserDetails(userId));
    }
    
    @PutMapping("/add-role")
    @ApiOperation(value = "Ajoute un role à un utilisateur")
    public ResponseEntity<String> addRoleToUser(@RequestParam String userName, @RequestParam String roleName) throws EntityNotFoundException{
       
    return new ResponseEntity<>(utilisateurServiceInter.addRoleToUser(userName, roleName), HttpStatus.CREATED);

    }
    
    @GetMapping("/roles")
    @ApiOperation(value = "Lister Tous les roles")
    public ResponseEntity<List<Role>> getRoles() {
        return ResponseEntity.ok(roleRepository.findAll());
    }
    
    
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }  
    
    @GetMapping("/verify")
    public String verifyUser(@RequestParam("code") String code) {
    	System.out.println("*********Je suis appeler en cas d'activation de compte");
        if (utilisateurServiceInter.verify(code)) {
            return "Félicitation, votre compte est activé avec succes.";
        } else {
            return "Echec d'activation de compte.";
        }
    }
    
    @PostMapping("/uploadFile")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile)
                    throws IOException {
         
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        long size = multipartFile.getSize();
         
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);
         
        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setDownloadUri("/downloadFile/" + filecode);
         
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/downloadFile/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
    	FileUploadUtil downloadUtil = new FileUploadUtil();
         
        Resource resource = null;
        try {
            resource = downloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
         
        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
         
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
         
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);       
    }
    
    @PostMapping("/upload/excel-file")
    public ResponseEntity<ApiResponseMessage> uploadFiles(@RequestParam("file") MultipartFile file) {
      String message = "";
      if (ExcelHelper.hasExcelFormat(file)) {
        try {
         utilisateurServiceInter.save(file);
          message = "Uploaded the file successfully: " + file.getOriginalFilename();
          return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseMessage(message));
        } catch (Exception e) {
          message = "Could not upload the file: " + file.getOriginalFilename() + "!";
          return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponseMessage(message));
        }
      }
      message = "Please upload an excel file!";
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseMessage(message));
    }
    
    
    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<OrderResponseDto> deleteUser(@PathVariable("id") Long id) throws EntityNotFoundException{
    	return new ResponseEntity<>(utilisateurServiceInter.deleteUser(id), HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/role/{id}")
    public ResponseEntity<OrderResponseDto> deleteRole(@PathVariable("id") Long id) throws InvalidEntityException{
    	
    	return new ResponseEntity<>(utilisateurServiceInter.deleteRole(id), HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping("/utilisateurs/{utilisateurId}/roles/{roleId}")
    public ResponseEntity<OrderResponseDto> deleteRoleFromUtilisateur(@PathVariable("utilisateurId") Long utilisateurId, @PathVariable("roleId") Long roleId ) throws EntityNotFoundException{
    	
    	return new ResponseEntity<>(utilisateurServiceInter.deleteRoleFromUtilisateur(utilisateurId,roleId), HttpStatus.OK);
    }
    
}
