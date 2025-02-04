package com.example.streamusserver.dto;

import lombok.Data;

@Data
public class FollowRequestDto {
    private Long accountId;
    private String accessToken;
    private Long profileId;
}
