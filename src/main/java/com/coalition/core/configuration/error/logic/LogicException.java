package com.coalition.core.configuration.error.logic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Logic processing error while trying to execute operation.")
public class LogicException extends Exception{
    public LogicException (String message, Throwable exception){
        super(message, exception);
    }

    public LogicException (String message){
        super(message);
    }
}
