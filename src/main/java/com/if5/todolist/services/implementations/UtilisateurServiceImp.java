package com.if5.todolist.services.implementations;

import static com.if5.todolist.config.SecurityConstants.SECRET;
import static com.if5.todolist.config.SecurityConstants.SUJET;
import static com.if5.todolist.config.SecurityConstants.COMPANY;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.if5.todolist.controllers.helpers.ExcelHelper;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import com.if5.todolist.models.dtos.tache.OrderResponseDto;
import com.if5.todolist.models.dtos.utilisateur.UtilisateurRequestDto;
import com.if5.todolist.models.dtos.utilisateur.UtilisateurResponseDto;
import com.if5.todolist.models.entities.Attribution;
import com.if5.todolist.models.entities.Role;
import com.if5.todolist.models.entities.Utilisateur;
import com.if5.todolist.models.enumerations.ResponseStatus;
import com.if5.todolist.repositories.AttributionRepository;
import com.if5.todolist.repositories.RoleRepository;
import com.if5.todolist.repositories.UtilisateurRepository;
import com.if5.todolist.services.interfaces.UtilisateurServiceInter;

import net.bytebuddy.utility.RandomString;

@Service
@Transactional
public class UtilisateurServiceImp implements UtilisateurServiceInter{

	           @Autowired
	           private JavaMailSender javaMailSender;
	           
	           @Autowired
               private UtilisateurRepository utilisateurRepository;
	           
	           @Autowired
               private RoleRepository roleRepository;
	           
	           @Autowired
	           private AttributionRepository attributionRepository;
	           
	           @Autowired
               private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UtilisateurResponseDto saveUser(UtilisateurRequestDto utilisateurRequestDto, String siteURL)
    		   throws EntityNotFoundException, MessagingException, UnsupportedEncodingException {
    	
    	//String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
       // utilisateurRequestDto.setPhotos(fileName);
    	
    	Utilisateur utilisateurTosave;
    	
    	if(utilisateurRequestDto.getId() != null && utilisateurRequestDto.getId() > 0 ) {
    		utilisateurTosave = utilisateurRepository.findById(utilisateurRequestDto.getId()).orElseThrow(
					()->new EntityNotFoundException("Projet à modifier est introuvable"));
			return UtilisateurResponseDto.buildUserDtoFromUser(utilisateurRepository.save(UtilisateurRequestDto.buildUpdateUser(utilisateurRequestDto, utilisateurTosave)));
			
		}
    	
        List<Role> listRole = new ArrayList<>();
        
        for(Long l: utilisateurRequestDto.getRoles()){
        	
        Role r=	roleRepository.findById(l).orElseThrow(()-> new EntityNotFoundException("Ce role n'existe pas dans la BD"));
            listRole.add(r);   
        }
        List<Attribution> listTacheUtilisateur = new ArrayList<>();
        
        System.out.println("list :"+ utilisateurRequestDto.getListTacheUtilisateur());
        for(Long t: utilisateurRequestDto.getListTacheUtilisateur()){
        	listTacheUtilisateur.add(attributionRepository.findById(t).orElseThrow(() -> new EntityNotFoundException("Cette tache n'existe pas dans la BD")));
        }
        utilisateurTosave = UtilisateurRequestDto.buildUserFromDto(utilisateurRequestDto , listRole, listTacheUtilisateur  );
		String hashPWD = bCryptPasswordEncoder.encode(utilisateurTosave.getPassword());
		utilisateurTosave.setRoles(listRole);
		//utilisateur.setListTache(listTache);
		utilisateurTosave.setPassword(hashPWD);   
		 String randomCode = RandomString.make(64);
		 utilisateurTosave.setVerificationCode(randomCode);
		 utilisateurTosave.setEnabled(false);
	     sendVerificationEmail(utilisateurTosave, siteURL);
        Utilisateur u= utilisateurRepository.save(utilisateurTosave);
      //  String uploadDir = "user-photos/" + u.getId();
       // FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);            
      return UtilisateurResponseDto.buildUserDtoFromUser(u);
    }
 
    @Override
    public List<UtilisateurResponseDto> getAllUsers() {
    	
    	Pageable peageable = PageRequest.of(0,10);
    	
    	Page<Utilisateur> pageOfUser =  utilisateurRepository.findAll(peageable);
    	// List<Utilisateur> listOfUser = pageOfUser.getContent();
    	 List<Utilisateur> listOfUser = utilisateurRepository.findAll();

    	       // List<String>  l=  listOfUser.stream().map(u ->u.getNom().toUpperCase()).collect(Collectors.toList());
    	       
        
    	 return  listOfUser.stream()
        		.map(UtilisateurResponseDto::buildUserDtoFromUser)
        		.collect(Collectors.toList());
       // return UtilisateurResponseDto.buildListDtoFromListUtilisateur(listOfUser);
    }

    @Override
    public Utilisateur getUser(Long id) throws InvalidEntityException {
       
        return utilisateurRepository.getById(id);
    }

    @Override
    public UtilisateurResponseDto updateUser(UtilisateurRequestDto utilisateurRequestDto)
            throws EntityNotFoundException {
      
        return null;
    }

    @Override
    public UtilisateurResponseDto showUserDetails(Long userId) throws EntityNotFoundException {

       Utilisateur userDetails =  utilisateurRepository.findById(userId).orElse(null);

       if(userDetails == null){

            throw new EntityNotFoundException("Aucun utilisateur avec l'ID "+userId+" n'existe dans la base de donnée");

       }

        return UtilisateurResponseDto.buildUserDtoFromUser(userDetails);
    }

    @Override
    public String addRoleToUser(String userName, String roleName) throws EntityNotFoundException {
         
            Utilisateur user = utilisateurRepository.findByUserName(userName);
        
            if(user == null) {
                throw new EntityNotFoundException("Un utilisateur avec le username "+ "'"+ userName+ "'"+" n'existe pas dans la base de donnée");
            }
            
           Role role = roleRepository.findByNom(roleName).orElse(null);
            if(role == null) {
                throw new EntityNotFoundException("Le Role: "+roleName+" n'existe pas dans la base de donnée");
            }
            
            
            List<Role> roles = user.getRoles();
            
            if(roles.contains(role)) {
                throw new EntityNotFoundException("Cet utilisateur a déja le role "+ "'"+role.getNom()+ "'");
            }
            
            user.getRoles().add(role);
            
           utilisateurRepository.save(user);
      return "Bravo vous avez ajouter le role à cet utilisateur avec succès";
        }

    @Override
	public void sendVerificationEmail(Utilisateur utilisateur, String siteURL)    	
		throws MessagingException, UnsupportedEncodingException {
	    	    String toAddress = utilisateur.getEmail();
	    	    String fromAddress = SECRET;
	    	    String senderName = COMPANY;
	    	    String subject = SUJET;
	    	    String content = "Cher(e) "+ utilisateur.getUserName() +",<br>"
	    	            + "Cliquez sur le lien ci-après afin d'activer votre compte :<br>"
	    	            + "<h3> <a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
	    	            + "Mreci,<br>"
	    	            + "ADVENCE-IT GROUP.";
	    	     
	    	    MimeMessage message = javaMailSender.createMimeMessage();
	    	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    	     
	    	    helper.setFrom(fromAddress, senderName);
	    	    helper.setTo(toAddress);
	    	    helper.setSubject(subject);
	    	     
	    	    content = content.replace("[[name]]", utilisateur.getUserName());
	    	    String verifyURL = siteURL + "/toDoList/v1/verify?code=" + utilisateur.getVerificationCode();
	    	     
	    	    content = content.replace("[[URL]]", verifyURL);
	    	     
	    	    helper.setText(content, true);
	    	     
	    	    javaMailSender.send(message);
	     
	    }
    
    public boolean verify(String verificationCode) {
        Utilisateur utilisateur = utilisateurRepository.findByVerificationCode(verificationCode);
         
        if (utilisateur == null || utilisateur.isEnabled()) {
        	
            return false;
        } else {
        	utilisateur.setVerificationCode(null);
        	utilisateur.setEnabled(true);
            utilisateurRepository.save(utilisateur);
            
            return true;
        }
         
    }
    
    public void save(MultipartFile file) {
        try {
          List<Role> roles = ExcelHelper.excelToTutorials(file.getInputStream());
          roleRepository.saveAll(roles);
        } catch (IOException e) {
          throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
      }

	@Override
	public OrderResponseDto deleteUser(Long id) throws EntityNotFoundException {
		
		System.out.println("ID ====> "+id);
		Utilisateur user = utilisateurRepository.findById(id).orElseThrow(()
				-> new EntityNotFoundException("Utilisateur non trouver."));
		user.setRoles(new ArrayList<>());
		user.setListTacheUtilisateur(new ArrayList<>());
	 // utilisateurRepository.delete(user);
		utilisateurRepository.deleteById(id);
		return new OrderResponseDto("Operation reussi", ResponseStatus.SUCCES, "L'utilisateur à été supprimer avec succès"); 
	}

	@Override
	public OrderResponseDto deleteRole(Long id) throws InvalidEntityException {
		roleRepository.delete(roleRepository.findById(id).orElseThrow(()
				->new InvalidEntityException(" Role inexistant ")
				));
		return new OrderResponseDto() ;
	}

	public OrderResponseDto deleteRoleFromUtilisateur(Long utilisateurId, Long roleId) throws EntityNotFoundException {
		Utilisateur utilisateur =  utilisateurRepository.findById(utilisateurId).orElseThrow(()
				-> new EntityNotFoundException("Utilisateur non existant"));
		utilisateur.removeRole(roleId);
		utilisateurRepository.save(utilisateur);
		return new OrderResponseDto("Opération reussi",ResponseStatus.SUCCES,"Role retirer à l'utilisateur.");	
	}
    
    }
