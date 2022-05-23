package com.softvision.books.controllers.advices;

import com.softvision.books.exeptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Object> handleUnknownError(RuntimeException runtimeException, WebRequest request) {
        return ResponseEntity
                .internalServerError()
                .build();
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<Object> handleNotFoundError(RuntimeException runtimeException, WebRequest request) {
        return ResponseEntity
                .notFound()
                .build();
    }

}
