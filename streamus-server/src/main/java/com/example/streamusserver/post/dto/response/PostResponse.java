package com.example.streamusserver.post.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
   private Long id;
    private String fromUserUsername;
    private String fromUserPhotoUrl;
    private Long fromUserId;
    private String postText;
    private int commentsCount;
   private List<MediaItemResponseDto> mediaItem;

}
