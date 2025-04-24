package com.app.finflow.exception;

public class GoogleAccountLoginException extends RuntimeException {
    public GoogleAccountLoginException(String message) {
        super(message);
    }
}