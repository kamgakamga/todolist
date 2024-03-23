package com.if5.todolist.dtos.responses;

public class ApiResponseMessage {
	
	private String message;
	  public ApiResponseMessage(String message) {
	    this.message = message;
	  }
	  public String getMessage() {
	    return message;
	  }
	  public void setMessage(String message) {
	    this.message = message;
	  }

}
