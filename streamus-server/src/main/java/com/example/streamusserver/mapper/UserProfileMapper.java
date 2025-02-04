package com.example.streamusserver.mapper;


import com.example.streamusserver.dto.ProfileDto;
import com.example.streamusserver.dto.ProfileRequestDto;
import com.example.streamusserver.model.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {
    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    UserProfile toEntity(ProfileRequestDto dto);

    ProfileRequestDto toDto(UserProfile userProfile);

    @Mapping(target = "followingsCount", source = "followingsCount")
    @Mapping(target = "followersCount", source = "followersCount")
    ProfileDto toProfileDto(UserProfile userProfile);
}
