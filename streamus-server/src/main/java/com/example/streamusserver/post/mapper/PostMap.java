package com.example.streamusserver.post.mapper;

import com.example.streamusserver.post.dto.response.PostResponse;
import com.example.streamusserver.post.model.Post;
import com.example.streamusserver.post.model.PostImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMap {
    @Autowired
    private MediaItemMapper mediaItemMapper;

    public List<PostImage> mapImageUrls(List<String> imageUrls, Post post) {
        if (imageUrls == null) {
            return List.of();
        }
        return imageUrls.stream()
                .map(imageUrl -> {
                    PostImage postImage = new PostImage();
                    postImage.setImageUrl(imageUrl);
                    postImage.setPost(post);
                    return postImage;
                })
                .collect(Collectors.toList());
    }

    public List<PostResponse> converttoDto(List<Post> posts) {
        List<PostResponse> postResponses = new ArrayList<>();
        for (Post post : posts) {
            PostResponse postResponse = new PostResponse();
            postResponse.setId(post.getId());
            postResponse.setFromUserUsername(post.getAccount().getFullname());
            postResponse.setFromUserPhotoUrl(post.getAccount().getPhoto_url());
            postResponse.setFromUserId(post.getAccount().getId());
            postResponse.setPostText(post.getPostText());
            postResponse.setMediaItem(mediaItemMapper.convertToMediaItemDTO(post.getMediaItem()));
            postResponses.add(postResponse);
        }
        return postResponses;
    }
}
