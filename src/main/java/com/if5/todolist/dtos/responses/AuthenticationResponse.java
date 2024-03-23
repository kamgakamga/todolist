package com.if5.todolist.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

	private String accessToken;
}
