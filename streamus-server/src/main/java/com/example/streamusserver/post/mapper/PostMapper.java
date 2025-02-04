package com.example.streamusserver.post.mapper;

import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.dto.PostRequestDto;
import com.example.streamusserver.post.model.Post;
import com.example.streamusserver.post.model.PostImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {PostImageMapper.class})
public interface PostMapper {

    @Mapping(source = "account", target = "account")
    @Mapping(target = "images", expression = "java(mapImageUrls(postRequest.getImages(), post))")
    Post toEntity(PostRequestDto postRequest, UserProfile account);

    default List<PostImage> mapImageUrls(List<String> imageUrls, Post post) {
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
}
