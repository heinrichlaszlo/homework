package com.kesmarki.homework.exception;

public class PersonNotFoundException extends IllegalArgumentException{
    public PersonNotFoundException(String message){
        super(message);
    }
}