package com.if5.todolist.dtos.requets;

import com.if5.todolist.models.entities.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class MessageRequestDto {

    private String sender;
    private String content;

    public static Message buildEntityFromDto(MessageRequestDto messageRequestDto) {
        return Message.MessageBuilder.aMessage()
                .sender(messageRequestDto.getSender())
                .content(messageRequestDto.getContent())
                .build();
    }

}
