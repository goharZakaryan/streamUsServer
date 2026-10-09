package com.example.streamusserver.market.controller;

import com.example.streamusserver.market.dto.request.AdvertisementRequestDto;
import com.example.streamusserver.market.dto.response.AdvertisementResponseDto;
import com.example.streamusserver.market.dto.response.AdvertisementSearchResponseDto;
import com.example.streamusserver.market.dto.response.CommonResponse;
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

    @PostMapping(value = "/add/ads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

    @PostMapping("/ads/search")
    public ResponseEntity<AdvertisementSearchResponseDto> search(
            @RequestParam(required = false) String query, @RequestParam(defaultValue = "0") int itemId) {

        AdvertisementSearchResponseDto response =
                advertisementService.search(query, itemId);

        return ResponseEntity.ok(response);
    }


    @PostMapping("/ads/preload")
    public AdvertisementSearchResponseDto preload(
            @RequestParam(defaultValue = "0") long itemId) {

        return advertisementService.preload(itemId);
    }

    @PostMapping("/ads/user/preload")
    public AdvertisementSearchResponseDto preload(
            @RequestParam(defaultValue = "0") long itemId, @RequestParam(defaultValue = "0") long userId) {

        return advertisementService.preload(itemId, userId);
    }

    @PostMapping("/ads/delete")
    public ResponseEntity<CommonResponse> delete(
            @RequestParam(defaultValue = "0") Long accountId, @RequestParam(name = "accessToken") String accessToken, @RequestParam(defaultValue = "0") long itemId) {

        return ResponseEntity.ok(advertisementService.delete(itemId, accessToken, accountId));
    }

    @PutMapping(value = "/ads/{advertisementId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdvertisementResponseDto> updateAd(
            @PathVariable long advertisementId,
            @RequestPart("data") AdvertisementRequestDto request,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestParam Long userId
    ) {

        AdvertisementResponseDto response =

                advertisementService.updateAd(advertisementId, request, image, userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/ads/obj")
    public AdvertisementResponseDto getAdsObj(
            @RequestParam(value = "id") long itemId) {

        return advertisementService.getAdsObj(itemId);
    }

    @PostMapping("/ads/{advertisementId}/like")
    public ResponseEntity<AdvertisementResponseDto> likeAdvertisement(
            @PathVariable long advertisementId,
            @RequestParam long userId
    ) {


        return ResponseEntity.ok(advertisementService.likeAdvertisement(advertisementId, userId));
    }

}
