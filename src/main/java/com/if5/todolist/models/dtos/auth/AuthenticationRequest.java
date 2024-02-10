package com.if5.todolist.models.dtos.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {

	private String username;
	private String password;
}
