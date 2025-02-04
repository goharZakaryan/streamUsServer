package com.example.streamusserver.dto.guests;

import lombok.Data;

@Data
public class GuestDto {

    private long id, guestTo, guestUserId;

    private int verify, vip;

    private String guestUserUsername, guestUserFullname, guestUserPhoto, timeAgo;

    private Boolean online = false;
    private Boolean viewed = false;


}
