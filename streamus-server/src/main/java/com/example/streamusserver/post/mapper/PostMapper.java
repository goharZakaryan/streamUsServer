package com.example.streamusserver.post.mapper;

import com.example.streamusserver.post.dto.response.PostResponse;
import com.example.streamusserver.post.model.Post;

public class PostMapper {
  public static PostResponse mapToDto(Post post){
      PostResponse response=new PostResponse();
      response.setFromUserUsername(post.getFromUserUsername());
      response.setFromUserPhotoUrl(post.getFromUserPhotoUrl());
      
      response.setFromUserId(post.getFromUserId());

      return response;
  }
}
