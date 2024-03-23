package com.if5.todolist.dtos.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TopicResponseDto {
    private String libelle;
    private String description;
}