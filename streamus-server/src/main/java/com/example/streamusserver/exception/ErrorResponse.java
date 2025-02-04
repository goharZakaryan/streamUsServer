package com.example.streamusserver.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder  // This enables the builder pattern
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}