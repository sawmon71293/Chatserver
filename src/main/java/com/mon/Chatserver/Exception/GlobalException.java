package com.mon.Chatserver.Exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException e,WebRequest req){
        ErrorDetail err=new ErrorDetail(e.getMessage(),req.getDescription(false),LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessageException.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(MessageException e,WebRequest req){
        ErrorDetail err=new ErrorDetail(e.getMessage(),req.getDescription(false),LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ChatException.class)
    public ResponseEntity<ErrorDetail> chatExceptionHandler(ChatException e,WebRequest req){
        ErrorDetail err=new ErrorDetail(e.getMessage(),req.getDescription(false),LocalDateTime.now());

        return new ResponseEntity<ErrorDetail>(err,HttpStatus.BAD_REQUEST);
    }


    
}
