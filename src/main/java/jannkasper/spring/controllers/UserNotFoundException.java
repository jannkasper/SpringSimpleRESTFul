package jannkasper.spring.controllers;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id){
        super("User id not found : " + id);
    }
}
