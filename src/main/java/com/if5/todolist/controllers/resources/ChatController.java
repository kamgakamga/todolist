package com.if5.todolist.controllers.resources;

import com.if5.todolist.dtos.requets.MessageRequestDto;
import com.if5.todolist.dtos.requets.TopicRequestDto;
import com.if5.todolist.dtos.responses.ApiResponse;
import com.if5.todolist.dtos.responses.MessageResponseDto;
import com.if5.todolist.dtos.responses.TopicResponseDto;
import com.if5.todolist.models.entities.Message;
import com.if5.todolist.services.interfaces.ChatService;
import com.if5.todolist.utils.KafkaConstants;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.if5.todolist.utils.StringsUtils.SUCESS_MESSAGE;

@RestController
@RequestMapping("/api/")
public class ChatController {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final ChatService chatService;

    public ChatController(KafkaTemplate<String, Message> kafkaTemplate,
                          ChatService chatService) {
        this.kafkaTemplate = kafkaTemplate;
        this.chatService = chatService;
    }

    @PostMapping(value = "send", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse<MessageResponseDto>> sendMessage(@RequestBody MessageRequestDto message) {
        System.out.println(message.toString());
        try {
            Message messageToSend = Message.MessageBuilder.aMessage()
                    .sender(message.getSender())
                    .content(message.getContent())
                    .timestamp(LocalDateTime.now().toString())
                    .build();
            //Sending the message to kafka topic queue
        	System.out.println(message.toString());
            kafkaTemplate.send(KafkaConstants.TODOLISTCHAT, messageToSend).get();
            return ResponseEntity.ok(new ApiResponse<>(true, SUCESS_MESSAGE, MessageResponseDto.buildDtoFromEntity(messageToSend), new Date()));
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @MessageMapping("sendMessage")
    @SendTo("/topic/group")
    public Message broadcastGroupMessage(@Payload Message message) {
        //Sending this message to all the subscribers
        return message;
    }


    @PostMapping(value = "topic", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse<TopicResponseDto>> createTopic(@RequestBody TopicRequestDto sujet) {
        System.out.println(sujet.toString());
        try {
            return ResponseEntity.ok(new ApiResponse<>(true, SUCESS_MESSAGE, chatService.createTopic(sujet), new Date()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
