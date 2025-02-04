package com.example.streamusserver.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SendEmailRequestDto {

//    @Email(message = "Enter a valid email address")
    private String receiver;
    private String subject;
    private String body;
}
