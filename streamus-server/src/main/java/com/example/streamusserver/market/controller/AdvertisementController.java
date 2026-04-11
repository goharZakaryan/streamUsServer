package com.example.streamusserver.market.controller;

import com.example.streamusserver.market.dto.request.AdvertisementRequestDto;
import com.example.streamusserver.market.dto.response.AdvertisementResponseDto;
import com.example.streamusserver.market.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @PostMapping(value = "/add/ads",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdvertisementResponseDto> createAd(
            @RequestPart("data") AdvertisementRequestDto request,
            @RequestPart("image") MultipartFile image,
            @RequestParam Long userId
    ) {

        AdvertisementResponseDto response = advertisementService.createAd(request, image, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
