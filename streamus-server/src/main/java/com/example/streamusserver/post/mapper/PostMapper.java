package com.example.streamusserver.post.mapper;

import com.example.streamusserver.post.dto.response.PostResponse;
import com.example.streamusserver.post.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class PostMapper {

    public static PostResponse mapToDto(Post post) {
        PostResponse response = new PostResponse();
        response.setFromUserUsername(post.getAccount().getUsername());
        response.setFromUserPhotoUrl(post.getAccount().getPhotoUrl());
        response.setOwner(post.getAccount());
        response.setFromUserId(post.getFromUserId());
        response.setMediaItem(MediaItemMapper.convertToMediaItemDTO(post.getMediaItem()));
        response.setPreviewImgUrl(post.getMediaItem().get(0).getImageUrl());

        return response;
    }
}
