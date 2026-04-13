package com.fresh.freshmart.expceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = RecordNotFoundException.class)
    public ResponseEntity handlerInvalidRecord(RecordNotFoundException exception){
       // ErrorRes errorResponse= new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),"Duplicate Record");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);

    }
}
