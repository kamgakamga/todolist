package com.if5.todolist.exceptions.handlers;

import com.if5.todolist.dtos.responses.ApiResponseMessage;
import com.if5.todolist.exceptions.DuplicationEntityException;
import com.if5.todolist.exceptions.EntityNotFoundException;
import com.if5.todolist.exceptions.InvalidEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ToDoListHandler{

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String,String> handlerInvalidArgument(MethodArgumentNotValidException ex){
		 
		Map<String,String> errorsMap= new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error->{
			        errorsMap.put(error.getField(),error.getDefaultMessage());
		} );
		return errorsMap;
		
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String,String> handlerUserException(EntityNotFoundException ex){
		 
		Map<String,String> errorUserMap= new HashMap<>();
		errorUserMap.put("error", ex.getMessage());
		return errorUserMap;	
	}
	
	@ExceptionHandler(InvalidEntityException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String,String> handlerInvalidException(InvalidEntityException ex){
		 
		Map<String,String> errorUserMap= new HashMap<>();
		errorUserMap.put("error", ex.getMessage());
		return errorUserMap;	
	}

	
	@ExceptionHandler(DuplicationEntityException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
	public Map<String,String> handlerDuplicateException(DuplicationEntityException ex){
		 
		Map<String,String> errorUserMap= new HashMap<>();
		errorUserMap.put("error", ex.getMessage());
		return errorUserMap;	
	}
	
	 @ExceptionHandler(MaxUploadSizeExceededException.class)
	  public ResponseEntity<ApiResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exc) {
	    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponseMessage("taille du fichier trop grand!"));
	  }
	

    
}
