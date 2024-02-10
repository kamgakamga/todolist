package com.if5.todolist.exceptions;

public class DuplicationEntityException extends Exception {

    public DuplicationEntityException() {
    }

    public DuplicationEntityException(String message) {
        super(message);
    }

    public DuplicationEntityException(Throwable cause) {
        super(cause);
    }

    public DuplicationEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicationEntityException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
    
}
