package jannkasper.spring.controllers;

import java.util.Set;

public class UserUnSupportedFieldPathException extends RuntimeException {
    public UserUnSupportedFieldPathException (Set<String> keys){
        super("Field " + keys.toString() + "update is not allow.");
    }
}
