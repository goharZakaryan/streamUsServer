package com.example.streamusserver.market.service;

import com.example.streamusserver.market.dto.request.AdvertisementRequestDto;
import com.example.streamusserver.market.dto.response.AdvertisementResponseDto;
import com.example.streamusserver.market.dto.response.AdvertisementSearchResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface AdvertisementService {
    AdvertisementResponseDto createAd(AdvertisementRequestDto req, MultipartFile image, Long userId);

    AdvertisementSearchResponseDto search(String query, int itemId);

    AdvertisementSearchResponseDto preload(long itemId);
}
