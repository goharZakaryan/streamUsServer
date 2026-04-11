package com.example.streamusserver.market.service.impl;

import com.example.streamusserver.market.dto.request.AdvertisementRequestDto;
import com.example.streamusserver.market.dto.response.AdvertisementResponseDto;
import com.example.streamusserver.market.entity.Advertisement;
import com.example.streamusserver.market.mapper.AdvertisementMapper;
import com.example.streamusserver.market.repository.AdvertisementRepository;
import com.example.streamusserver.market.service.AdvertisementService;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.service.UserProfileService;
import com.example.streamusserver.util.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final UserProfileService userProfileService;
    private final FileStorageService fileStorageService;
    private final AdvertisementMapper mapper;
    private final AdvertisementRepository repository;

    @Override
    public AdvertisementResponseDto createAd(AdvertisementRequestDto req, MultipartFile image, Long userId) {
        UserProfile user = userProfileService.findById(userId)
                .orElseThrow();

//        double price = pricingFactory.getPrice(req.getType());

        // optional: balance check
//        if (user.getBalance() < price) {
//            throw new RuntimeException("Not enough balance");
//        }

//        user.setBalance(user.getBalance() - price);
        String fileName = fileStorageService.uploadFile(image, userId);

        Advertisement ad = mapper.toEntity(req);
        ad.setOwner(user);
        ad.setCreatedAt(LocalDateTime.now());
        ad.setImageUrl(fileName);
        Advertisement saved = repository.save(ad);


        return mapper.toDto(saved);
    }
}
