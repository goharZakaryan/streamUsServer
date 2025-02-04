package com.example.streamusserver.dto.guests;

import lombok.Data;

@Data
public class GuestSaveRequestDto {

    private String accessToken;

    private long guestTo;

    private long guestUserId;
}
