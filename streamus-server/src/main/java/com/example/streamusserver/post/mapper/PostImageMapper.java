package com.example.streamusserver.post.mapper;

import com.example.streamusserver.post.model.Post;
import com.example.streamusserver.post.model.PostImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostImageMapper {
    @Mapping(source = "imageUrl", target = "imageUrl")
    @Mapping(source = "post", target = "post")
    PostImage toEntity(String imageUrl, Post post);

    default List<PostImage> mapImageUrls(List<String> imageUrls, Post post) {
        return imageUrls.stream()
                .map(imageUrl -> toEntity(imageUrl, post))
                .collect(Collectors.toList());
    }
}
