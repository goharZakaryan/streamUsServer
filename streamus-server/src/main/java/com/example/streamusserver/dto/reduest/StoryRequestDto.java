package com.example.streamusserver.dto.reduest;

import lombok.Data;

@Data
public class StoryRequestDto {
     private String storyType;
     private Long userId;
     private String caption;
}
