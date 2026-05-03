package com.example.streamusserver.market.service.impl;

import com.example.streamusserver.market.dto.request.AdvertisementRequestDto;
import com.example.streamusserver.market.dto.response.AdvertisementResponseDto;
import com.example.streamusserver.market.dto.response.AdvertisementSearchResponseDto;
import com.example.streamusserver.market.entity.Advertisement;
import com.example.streamusserver.market.mapper.AdvertisementMapper;
import com.example.streamusserver.market.repository.AdvertisementRepository;
import com.example.streamusserver.market.service.AdvertisementService;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.service.UserProfileService;
import com.example.streamusserver.util.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final UserProfileService userProfileService;
    private final FileStorageService fileStorageService;
    private final AdvertisementMapper mapper;
    private final AdvertisementRepository repository;
    private static final int LIMIT = 20;

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
        String fileName ="public/"+ fileStorageService.uploadFile(image, userId);

        Advertisement ad = mapper.toEntity(req);
        ad.setOwner(user);
        ad.setCreatedAt(LocalDateTime.now());
        ad.setImageUrl(fileName);
        Advertisement saved = repository.save(ad);


        return mapper.toDto(saved);
    }

    @Override
    public AdvertisementSearchResponseDto search(String query, int itemId) {
        List<Advertisement> items;

        if (query == null || query.isEmpty()) {
            items = repository.findAllWithPagination(itemId);
        } else {
            items = repository.search(query, itemId);
        }

        AdvertisementSearchResponseDto response = new AdvertisementSearchResponseDto();

        response.setError(false);
        response.setQuery(query);
        response.setItemId(items.isEmpty() ? itemId : items.get(items.size() - 1).getId());
        response.setItemCount(items.size());
        response.setItems(mapper.toDtoList(items));

        return response;
    }

    @Override
    public AdvertisementSearchResponseDto preload(long itemId) {

        List<Advertisement> items;

        if (itemId == 0) {
            // առաջին բեռնում (ամենավերջին item-ները)
            items = repository.findLatest(PageRequest.of(0, LIMIT));
        } else {
            // pagination (cursor-based)
            items = repository.findNext(itemId, PageRequest.of(0, LIMIT));
        }

        AdvertisementSearchResponseDto response = new AdvertisementSearchResponseDto();

        response.setError(false);
        response.setQuery(""); // preload → query չկա
        response.setItemCount(items.size());

        // 🔥 cursor update
        if (items.isEmpty()) {
            response.setItemId(itemId);
        } else {
            response.setItemId(items.get(items.size() - 1).getId());
        }

        // DTO mapping
        response.setItems(mapper.toDtoList(items));

        return response;
    }

    @Override
    public AdvertisementResponseDto getAdsObj(long itemId) {
        return mapper.toDto(repository.findById(itemId).get());
    }
}
