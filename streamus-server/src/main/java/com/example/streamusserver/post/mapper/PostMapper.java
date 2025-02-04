package com.example.streamusserver.post.mapper;

import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.dto.PostRequestDto;
import com.example.streamusserver.post.dto.PostResponseDto;
import com.example.streamusserver.post.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    @Mapping(source = "account", target = "account")
    @Mapping(source = "postRequest.images", target = "images")
    Post toEntity(PostRequestDto postRequest, UserProfile account);

    @Mapping(source = "post.account.id", target = "accountId")
    PostResponseDto toResponseDto(Post post);
}
