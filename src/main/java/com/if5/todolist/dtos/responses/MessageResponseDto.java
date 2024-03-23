package com.if5.todolist.dtos.responses;

import com.if5.todolist.models.entities.Message;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class MessageResponseDto {

    private String sender;
    private String content;
    private String timestamp;

    public static MessageResponseDto buildDtoFromEntity(Message m) {
        return MessageResponseDto.builder()
                .sender(m.getSender())
                .content(m.getContent())
                .timestamp(m.getTimestamp())
                      .build();
    }

    public static List<MessageResponseDto> buildListDtoFromList(List<Message> listEntity){
        return listEntity.stream().map(MessageResponseDto::buildDtoFromEntity).collect(Collectors.toList());

    }

    public static Page<MessageResponseDto> buildPageDtoFromPageEntity(Page<Message> pageEntityList) {
        return Objects.isNull(pageEntityList) ? Page.empty() : pageEntityList.map(MessageResponseDto::buildDtoFromEntity);
    }
}
