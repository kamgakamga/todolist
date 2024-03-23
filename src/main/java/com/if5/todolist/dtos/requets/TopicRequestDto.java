package com.if5.todolist.dtos.requets;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class TopicRequestDto {
    private String libelle;
    private String description;
}
