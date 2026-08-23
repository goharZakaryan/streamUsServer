package com.example.streamusserver.post.dto.response;

import com.example.streamusserver.model.UserProfile;
import lombok.Data;

import java.util.List;

@Data
public class PostResponse {
   private Long id;
    private String fromUserUsername;
    private String fromUserPhotoUrl;
    private Long fromUserId;
    private String postText;
    private String previewImgUrl;
    private int commentsCount;
    private UserProfile owner;
   private List<MediaItemResponseDto> mediaItem;

}
