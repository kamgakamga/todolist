package com.if5.todolist.controllers.resources;

import com.if5.todolist.dtos.requets.RoleRequestDto;
import com.if5.todolist.dtos.requets.UtilisateurRequestDto;
import com.if5.todolist.dtos.responses.ApiResponse;
import com.if5.todolist.dtos.responses.ApiResponseMessage;
import com.if5.todolist.dtos.responses.RoleResponseDto;
import com.if5.todolist.dtos.responses.UtilisateurResponseDto;
import com.if5.todolist.dtos.responses.application.tache.OrderResponseDto;
import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.helpers.ExcelHelper;
import com.if5.todolist.models.entities.FileUploadResponse;
import com.if5.todolist.models.entities.Role;
import com.if5.todolist.models.entities.Utilisateur;
import com.if5.todolist.repositories.RoleRepository;
import com.if5.todolist.repositories.UtilisateurRepository;
import com.if5.todolist.services.interfaces.RoleServiceInter;
import com.if5.todolist.services.interfaces.UtilisateurServiceInter;
import com.if5.todolist.utils.FileUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.if5.todolist.utils.StringsUtils.SUCESS_MESSAGE;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/")
@Api("API pour le CRUD des utilisateurs")
@Slf4j
public class UtilisateurController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilisateurController.class);



    private final UtilisateurServiceInter utilisateurServiceInter;
    private final RoleServiceInter roleServiceInter;
    private final UtilisateurRepository utilisateurRepository;
    private final RoleRepository roleRepository;

    public UtilisateurController(UtilisateurServiceInter utilisateurServiceInter,
                                 RoleServiceInter roleServiceInter,
                                 UtilisateurRepository utilisateurRepository,
                                 RoleRepository roleRepository) {
        this.utilisateurServiceInter = utilisateurServiceInter;
        this.roleServiceInter = roleServiceInter;
        this.utilisateurRepository = utilisateurRepository;
        this.roleRepository = roleRepository;
    }


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
    @ApiOperation(value = "Api qui liste tous les utilisateurs de la bd")
    public ResponseEntity<ApiResponse<UtilisateurResponseDto>> getAllUser(@ApiParam(value = "clé de la recherche") @RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                                          @ApiParam(value = "Page de la recherche") @RequestParam(name = "page", defaultValue = "0") int page,
                                                                          @ApiParam(value = "taille de l'élément à afficher ")  @RequestParam(name = "size", defaultValue = "20") int size,
                                                                          @RequestParam(name = "sort", defaultValue = "ASC") String sort,
                                                                          @RequestParam(name = "orderBy", defaultValue = "id") String orderBy){
        try{
            log.info("Liste des utilisateurs dans le système");
            Page<UtilisateurResponseDto> data = utilisateurServiceInter.getAllUsers(keyword, PageRequest.of(page, size, Sort.Direction.fromString(sort), orderBy));
            return ResponseEntity.ok(new ApiResponse(true,SUCESS_MESSAGE ,data, new Date()));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, e.getMessage(), e.getCause(),new Date()));
        }
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "Affiche le detail d'un utilisateur")
    public ResponseEntity<UtilisateurResponseDto> showDetailsUser(@PathVariable("id") Long id) throws EntityNotFoundException{

        return ResponseEntity.ok(utilisateurServiceInter.showUserDetails(id));
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
        log.info("*********Je suis appeler en cas d'activation de compte");
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
