package com.if5.todolist.controllers.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toDoList/v1")
public class ChatController {
	
	/*@Autowired
    private KafkaTemplate<String, Message> kafkaTemplate;

    @PostMapping(value = "/send", consumes = "application/json", produces = "application/json")
    public void sendMessage(@RequestBody Message message) {
        message.setTimestamp(LocalDateTime.now().toString());
        System.out.println(message.toString());
        try {
            //Sending the message to kafka topic queue
        	System.out.println(message.toString());
            kafkaTemplate.send(KafkaConstants.TODOLISTCHAT, message).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message) {
        //Sending this message to all the subscribers
        return message;
    }*/
}
