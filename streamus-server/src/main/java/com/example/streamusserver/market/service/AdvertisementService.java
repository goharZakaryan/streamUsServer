package com.example.streamusserver.market.service;

import com.example.streamusserver.market.dto.request.AdvertisementRequestDto;
import com.example.streamusserver.market.dto.response.AdvertisementResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface AdvertisementService {
    AdvertisementResponseDto createAd(AdvertisementRequestDto req, MultipartFile image, Long userId);
}
