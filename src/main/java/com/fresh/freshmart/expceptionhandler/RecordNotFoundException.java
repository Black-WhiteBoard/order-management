package com.fresh.freshmart.expceptionhandler;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(){

    }
    public RecordNotFoundException(String message){
       super(message);
    }
}
