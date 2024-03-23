package com.if5.todolist.config.kafka;

import com.if5.todolist.models.entities.Message;
import com.if5.todolist.utils.KafkaConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Component
public class  MessageListener {
     private final SimpMessagingTemplate template;

	public MessageListener(@Qualifier("messagingTemplate") SimpMessagingTemplate template) {
		this.template = template;
	}

	@KafkaListener(
	            topics = KafkaConstants.TODOLISTCHAT,
	            groupId = KafkaConstants.GROUP_ID
	    )
	    
	    public void listen(Message message) {
	    	System.out.println("Envoyer via kafka listener..");   
	        System.out.println("sending via kafka listener..");
	        template.convertAndSend("/topic/group", message);
	    }
}
