package com.if5.todolist.services.interfaces;

import com.if5.todolist.dtos.requets.TopicRequestDto;
import com.if5.todolist.dtos.responses.TopicResponseDto;

public interface ChatService {
	
	 TopicResponseDto createTopic(TopicRequestDto topicRequestDto);

}
