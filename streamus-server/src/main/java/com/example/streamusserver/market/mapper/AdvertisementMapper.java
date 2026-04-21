package com.example.streamusserver.market.mapper;


import com.example.streamusserver.market.dto.request.AdvertisementRequestDto;
import com.example.streamusserver.market.dto.response.AdvertisementResponseDto;
import com.example.streamusserver.market.entity.Advertisement;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {

    // Entity → Response DTO
    @Mapping(source = "owner.id", target = "ownerId")
    AdvertisementResponseDto toDto(Advertisement ad);

    List<AdvertisementResponseDto> toDtoList(List<Advertisement> ads);

    // Optional: DTO → Entity (basic mapping)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "price", ignore = true) // business logic sets it
    @Mapping(target = "owner", ignore = true) // set in service
    Advertisement toEntity(AdvertisementRequestDto request);
}
