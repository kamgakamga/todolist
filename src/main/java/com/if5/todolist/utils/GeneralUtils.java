package com.if5.todolist.utils;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 2:19 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.utils
 **/
@Slf4j
public class GeneralUtils {

    public static final String VARIABLE_BLOQUER_NOTES_INSOLVABLES = "BLOQUER_NOTES_INSOLVABLES";
    public static final String VARIABLE_AFFICHER_SERVICES_OPTIONNELS_SUR_RECU_ELEVE = "AFFICHER_SERVICES_OPTIONNELS_SUR_RECU_ELEVE";
    public static final String VARIABLE_CODE_GROUPE_ETAB = "CODE_GRP_ETAB";
    public static final String VARIABLE_SUPPRIMER_REGLEMENT = "SUPPRIMER_REGLEMENT";
    public static final String VARIABLE_APP_MODE = "SYS_OPE_MODE"; // 0=PROD, 1=MAINTENANCE, 2=TEST
    public static final String VARIABLE_APP_VERSION = "SYS_UPD_NUM";
    public static final String VARIABLE_COTATION = "COTATION";
    public static final String VARIABLE_COTE_SUR_20 = "COTE_SUR_20";
    public static final String DEBT_CLAIM = "DEBT_CLAIM";

    public static final String USER_DIR = "user.dir";
    public static final String REPORTS_DIR_PATH = System.getProperty(USER_DIR) + "/../reports/def/";
    public static final String STUDENT_QR_CODE_DIR_PATH = System.getProperty(USER_DIR) + "/../qrcode/eleves/";
    public static final String LOGO_PAR_DEFAUT = System.getProperty(USER_DIR) + "/../images/scolarite/logo_defaut.png";
    public static final String LOGO_DRAPEAU = System.getProperty(USER_DIR) + "/../images/scolarite/flag.png";
    public static final String LOGO_ELEVE_FILLE = System.getProperty(USER_DIR) + "/../images/scolarite/student-female.png";
    public static final String LOGO_ELEVE_GARCON = System.getProperty(USER_DIR) + "/../images/scolarite/student-male.png";
    public static final String FILLIGRANE_PAR_DEFAUT = System.getProperty(USER_DIR) + "/../images/scolarite/filigrane.png";
    public static final String IMAGES_ELEVE_DIR_PATH = System.getProperty(USER_DIR) + "/../images/eleves/";
    public static final String IMAGES_ENSEIGNANT_DIR_PATH = System.getProperty(USER_DIR) + "/../images/enseignants/";
    public static final String IMAGES_SCOLARITE_DIR_PATH = System.getProperty(USER_DIR) + "/../images/scolarite/";

    /**
     * @param phone1 premier numero de telephone
     * @param phone2 second numero de telephone
     * @return true si le numero est disponible et au bon format et false le cas echeant
     * @implSpec Verifie si l'un des numero passe en parametre est correct
     */
    public static boolean phoneNumberIsAvailable(String phone1, String phone2) {
        String telephone = (Objects.nonNull(phone1) && phone1.trim().length() > 7) ? phone1 : phone2;
        if (Objects.nonNull(telephone) && telephone.trim().length() > 7) {
            telephone = telephone.replaceAll("[^0-9]", "").trim();
            return telephone.length() > 7;
        }
        return false;
    }

    /**
     * @param phone1 premier numero de telephone
     * @param phone2 second numero de telephone
     * @return le numero de telephone correctement formatte
     * @implSpec Verifie si l'un des numero passe en parametre est correct et renvoie le 1er numero au format de 9 chiffres
     */
    public static @NotEmpty String getPhoneNumber(String phone1, String phone2) {
        String telephone = (Objects.nonNull(phone1) && phone1.trim().length() > 7) ? phone1 : phone2;
        telephone = telephone.replaceAll("[^0-9]", "").trim();
        return telephone.length() == 8 ? "6" + telephone : telephone;
    }
    public static @NotEmpty String getPhoneNumberValide(String phone1, String phone2) {
        String telephone;
        if (Objects.nonNull(phone1) && phone1.trim().length() > 7) telephone = phone1;
        else telephone = (Objects.nonNull(phone2) && phone2.trim().length() > 7) ? phone2 : null;

        if (Objects.isNull(telephone)) return null;
        telephone = telephone.replaceAll("[^0-9]", "").trim();
        return telephone.length() == 8 ? "6" + telephone : telephone;
    }
    public static boolean phoneNumberiSValide(String phone1, String phone2) {
        String telephone = (Objects.nonNull(phone1) && phone1.trim().length() > 7) ? phone1 : phone2;
        telephone = telephone.replaceAll("[^0-9]", "").trim();
        return true;
    }

    /**
     * @param phone premier numero de telfephone
     * @return le numero de telephone correctement formatte
     * @implSpec Verifie si l'un des numero passe en parametre est correct et renvoie le 1er numero au format de 9 chiffres
     */
    public static String getPhoneNumber(String phone) {
        if (Objects.nonNull(phone) && phone.trim().length() > 7) {
            String telephone = phone.replaceAll("[^0-9]", "").trim();
            return telephone.length() == 8 ? "6" + telephone : telephone;
        }
        return null;
    }

    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnLabel(x))) {
                return true;
            }
        }
        return false;
    }


    /**
     * Retourne les roles
     *
     * @param roles
     * @return
     */
//    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles) {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return authorities;
//    }

    /**
     * Retourne le type de provider en fonction de son id
     *
     * @param providerId
     * @return
     */
//    public static SocialProvider toSocialProvider(String providerId) {
//        for (SocialProvider socialProvider : SocialProvider.values()) {
//            if (socialProvider.getProviderType().equals(providerId)) {
//                return socialProvider;
//            }
//        }
//        return SocialProvider.LOCAL;
//    }


    public static Double getNoteMaximaleExtreme(String extreme) {
        try {
            return Double.parseDouble(extreme.substring(extreme.indexOf("-") + 1, extreme.indexOf("]")).replace(',', '.'));
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static Double getNoteMinimaleExtreme(String extreme) {
        try {
            return Double.parseDouble(extreme.substring(1, extreme.indexOf("-")).replace(',', '.'));
        } catch (Exception e) {
            return 0.0;
        }
    }

    /**
     * Methode permettant de recuperer l'utilisateur courant
     *
     * @param localUser utilisateur connecté
     * @return UserInfo
     */
//    public static UserInfo buildUserInfo(LocalUser localUser, String codeAnnee) {
//        Utilisateur user = localUser.getUser();
//        List<RoleDto> roles = new ArrayList<>();
//        user.getRoles().forEach(item -> roles.add(RoleDto.builder().id(item.getId()).name(item.getName()).build()));
//        return new UserInfo(user.getId().toString(), user.getDisplayName(), user.getEmail(), roles, codeAnnee);
//    }

    /**
     * Methode utilitaire permettant de formatter les objets @value de type numerique
     * respectant le format @pattern ceci sera generalement appele lorsqu'on aura
     * besoin d'imprimer les valeurs numerique sur les format du genre 0001, 00,00.1 etc....
     *
     * @param pattern regex de formattage
     * @param value valeur formattée
     * @return String
     */
    public static String numericFormatter(String pattern, Object value) {
        DecimalFormat formatter = new DecimalFormat(pattern);
        return formatter.format(value);
    }

    public static String genererPasswordUser() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String password = RandomString.make(8);
        log.info("Le mot de passe generer est {}", password);
        return bcrypt.encode(password);
    }

    public static String genererPasswordUser(String password) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        log.info("La chaine generer est {}", password);
        return bcrypt.encode(password);
    }

    public static boolean comparePassword(String clear, String crypt) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        return bcrypt.matches(clear, crypt);
    }

    // Methode permettant de remplacer les espaces par le caractère undescore dans une chaine de caractère
    public static String replaceSpaceByUndescore(String chaine) {
        return chaine.replaceAll(" ", "_");
    }

//  //  public static LocalUser currentUser(@CurrentUser LocalUser localUser) {
//        return localUser;
//    }


    public static void initHeaderDocumentPDF(HttpServletResponse response, String contentDisposition) {
        response.setContentType("application/x-pdf");
        response.setHeader("Content-disposition", contentDisposition);
    }

    /**
     * @implSpec construit une Map dans laquelle se trouve les elements d'en tete pour les etats imprimables
     */
    public static Map<String, Object> buildEtatParams() {
       return new HashMap<>();
    }

    public static String replaceSpacesWithUnderscores(String input) {
        // Vérifier si l'entrée est nulle ou vide
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Remplacer les espaces par des underscores
        return input.replaceAll(" ", "_");
    }

    public static void initHeaderDocumentXLS(HttpServletResponse response, String contentDisposition) {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", contentDisposition);
    }


    public static String buildKeyword(String key){
        return "%"+key+"%";
    }
}
