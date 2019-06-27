package jannkasper.spring.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler({ResourceNotFoundException.class})
//    public ResponseEntity<Object> handleNotFoundException(Exception exception, WebRequest request){
//
//        return new ResponseEntity<Object>("Resource Not Found", new HttpHeaders(), HttpStatus.NOT_FOUND);
//
//    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void springHandleNotFound(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(UserUnSupportedFieldPathException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public void springUnSupportedFieldPatch(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.METHOD_NOT_ALLOWED.value());
    }
}
