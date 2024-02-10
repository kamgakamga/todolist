package com.if5.todolist.models.dtos.tache;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddTaskRequestDTO {
	
	private Long attributionId;
	private Long tacheId;
	private Long newUserId;
	private Long oldUserId;
	
}
