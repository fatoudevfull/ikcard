package com.BackendIkcard.IkcardBackend.Configuration;

import com.BackendIkcard.IkcardBackend.Message.Reponse.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRecupereur {

    @ExceptionHandler({Exception.class})
    ResponseEntity<?> traiterException(Exception e) {
        return ResponseMessage.generateResponse("exeption", HttpStatus.EXPECTATION_FAILED, e.getMessage());
    }

}
