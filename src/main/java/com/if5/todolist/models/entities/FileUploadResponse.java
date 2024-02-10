package com.if5.todolist.models.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FileUploadResponse {

	    private String fileName;
	    private String downloadUri;
	    private long size;
	    
	    
}
