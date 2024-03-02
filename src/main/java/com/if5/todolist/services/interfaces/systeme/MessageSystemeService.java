package com.if5.todolist.services.interfaces.systeme;

import com.if5.todolist.models.entities.systeme.MessageSysteme;
import com.if5.todolist.services.interfaces.generics.CommonGenericCrudService;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:27 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.interfaces.systeme
 **/
public interface MessageSystemeService extends CommonGenericCrudService<MessageSysteme> {
    /**
     * @param codeMessage le code du message
     * @return Le message ou null le cas echeant
     * @implSpec verifie si un code message est deja enregistre et le renvoie
     */
    MessageSysteme findByCodeMessage(int codeMessage);

    /**
     * @param codeMessage le code du message
     * @return true if exists and false otherwise
     * @implSpec verifie si un code message est deja enregistre
     */
    boolean existsByCodeMessage(int codeMessage);

    /**
     * @param valeurEn valeur anglaise du message
     * @param valeurFr valeur francaise du message
     * @implSpec verifie si un message francais ou anglais est deja enregistre
     * @return true if exists and false otherwise
     * */
    boolean existsByValeurEnOrValeurFr(String valeurEn, String valeurFr);

    /**
     * @implSpec mets a jour un message systeme en BD
     * @param message le message a modifier
     * @return la valeur mise a jour
     * */
    MessageSysteme update(MessageSysteme message);
}
