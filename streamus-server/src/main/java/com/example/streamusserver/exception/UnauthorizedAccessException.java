package com.example.streamusserver.exception;

public class UnauthorizedAccessException extends Throwable {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
