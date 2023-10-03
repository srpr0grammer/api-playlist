package com.apiplaylist.service.exception;

public class ForbiddenException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ForbiddenException(String msg){
        super(msg);
    }

    public ForbiddenException(String msg, Throwable cause){
        super(msg, cause);
    }
}
