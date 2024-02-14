package com.kesmarki.homework.exception;
/**
 * Exception for person not found
 */
public class PersonNotFoundException extends IllegalArgumentException{
    public PersonNotFoundException(String message){
        super(message);
    }
}