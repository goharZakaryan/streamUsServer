package com.example.streamusserver.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowResponseDto {
    private boolean error;
    private boolean follow;
    private int followersCount;
}
