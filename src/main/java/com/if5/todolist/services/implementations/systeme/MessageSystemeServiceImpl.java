package com.if5.todolist.services.implementations.systeme;

import com.if5.todolist.exceptions.ResourceNotFoundException;
import com.if5.todolist.models.entities.parametrages.VariableGlobale;
import com.if5.todolist.models.entities.systeme.MessageSysteme;
import com.if5.todolist.repositories.parametre.VariableGlobaleRepository;
import com.if5.todolist.repositories.systeme.MessageSystemeRepository;
import com.if5.todolist.services.implementations.generics.CommonGenericCrudServiceImpl;
import com.if5.todolist.services.interfaces.systeme.MessageSystemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Copyright (c) 2024, Iforce5, All Right Reserved.
 * https://iforce5.com
 * <p>
 * When: @created 02/March/2024 -- 4:37 PM
 *
 * @author name :  Serges KAMGA on 3/2/2024
 * @author email : kamgakamga93@gmail.com /  serge.kamga@iforce5.com
 * Project : @project todolist
 * Package : @package com.if5.todolist.services.implementations.systeme
 **/
@Service
public class MessageSystemeServiceImpl extends CommonGenericCrudServiceImpl<MessageSystemeRepository, MessageSysteme> implements MessageSystemeService {
    public MessageSystemeServiceImpl(MessageSystemeRepository messageSysRepository) {
        super(messageSysRepository);
    }

    @Autowired
    private VariableGlobaleRepository variableGlobaleRepository;

    /**
     * @param codeMessage le code du message
     * @return Le message ou null le cas echeant
     * @implSpec verifie si un code message est deja enregistre et le renvoie
     */
    @Override
    public MessageSysteme findByCodeMessage(int codeMessage) {
        String key = "SYSTEME_LANGUE";
        //On recupere la variable globale qui stocke la langue de l'utilisateur connecté

        VariableGlobale variableGlobale = variableGlobaleRepository.findTopByCle(key)
                .orElseThrow(() -> new ResourceNotFoundException("VariableGlobale", "id", key));

        MessageSysteme messageSysteme =	repository.findByCodeMessage(codeMessage)
                .orElseThrow(() -> new ResourceNotFoundException("MessageSysteme", "id", codeMessage));

        MessageSysteme newMessage = new MessageSysteme();

        if(variableGlobale.getValeur().equalsIgnoreCase("en")) {
            newMessage.setValeurFr(messageSysteme.getValeurEn());
            return newMessage;
        }else {
            return messageSysteme;
        }
    }
    /**
     * @param codeMessage le code du message
     * @return true if exists and false otherwise
     * @implSpec verifie si un code message est deja enregistre
     */
    @Override
    public boolean existsByCodeMessage(int codeMessage) {
        return repository.existsByCodeMessage(codeMessage);
    }

    /**
     * @param valeurEn valeur anglaise du message
     * @param valeurFr valeur francaise du message
     * @return true if exists and false otherwise
     * @implSpec verifie si un message francais ou anglais est deja enregistre
     */
    @Override
    public boolean existsByValeurEnOrValeurFr(String valeurEn, String valeurFr) {
        return repository.existsByValeurEnOrValeurFr(valeurEn, valeurFr);
    }

    /**
     * @param message le message a modifier
     * @return la valeur mise a jour
     * @implSpec mets a jour un message systeme en BD
     */
    @Override
    @Transactional
    public MessageSysteme update(MessageSysteme message) {
        MessageSysteme stored = repository.findById(message.getId())
                .orElseThrow(() -> new ResourceNotFoundException("MessageSysteme", "id", message.getId()));
        stored.setCodeMessage(message.getCodeMessage());
        stored.setTypeMessage(message.getTypeMessage());
        stored.setValeurEn(message.getValeurEn());
        stored.setValeurFr(message.getValeurFr());
        return repository.save(stored);
    }

}
