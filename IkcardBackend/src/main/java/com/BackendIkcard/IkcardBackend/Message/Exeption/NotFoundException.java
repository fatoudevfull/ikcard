package com.BackendIkcard.IkcardBackend.Message.Exeption;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
