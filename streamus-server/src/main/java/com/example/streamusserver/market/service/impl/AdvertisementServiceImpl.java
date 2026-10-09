package com.example.streamusserver.market.service.impl;

import com.example.streamusserver.exception.UserNotFoundException;
import com.example.streamusserver.market.dto.request.AdvertisementRequestDto;
import com.example.streamusserver.market.dto.response.AdvertisementResponseDto;
import com.example.streamusserver.market.dto.response.AdvertisementSearchResponseDto;
import com.example.streamusserver.market.dto.response.CommonResponse;
import com.example.streamusserver.market.entity.Advertisement;
import com.example.streamusserver.market.entity.AdvertisementLike;
import com.example.streamusserver.market.mapper.AdvertisementMapper;
import com.example.streamusserver.market.repository.AdvertisementLikeRepository;
import com.example.streamusserver.market.repository.AdvertisementRepository;
import com.example.streamusserver.market.service.AdvertisementService;
import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.security.JwtUtil;
import com.example.streamusserver.service.UserProfileService;
import com.example.streamusserver.util.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final UserProfileService userProfileService;
    private final FileStorageService fileStorageService;
    private final AdvertisementMapper mapper;
    private final AdvertisementRepository repository;
    private final AdvertisementLikeRepository advertisementLikeRepository;
    private static final int LIMIT = 20;
    private final JwtUtil jwtUtil;

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
        String fileName = "public/" + fileStorageService.uploadFile(image, userId);

        Advertisement ad = mapper.toEntity(req);
        ad.setOwner(user);
        ad.setCreatedAt(LocalDateTime.now());
        ad.setImageUrl(fileName);
        ad.setPrice(req.getPrice());
        ad.setLocation(req.getLocation());
        Advertisement saved = repository.save(ad);


        return mapper.toDto(saved);
    }

    public AdvertisementResponseDto likeAdvertisement(Long advertisementId, Long userId) {

        if (advertisementLikeRepository
                .existsByAdvertisementIdAndUserId(advertisementId, userId)) {
            return new AdvertisementResponseDto(true);
        }

        Advertisement advertisement = repository
                .findById(advertisementId)
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));

        UserProfile user = userProfileService
                .findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        AdvertisementLike like = new AdvertisementLike();

        like.setAdvertisement(advertisement);
        like.setUser(user);
        like.setLikedAt(LocalDateTime.now());

        advertisementLikeRepository.save(like);
        return new AdvertisementResponseDto(true);

    }

    @Override
    public CommonResponse update(long itemId, String accessToken, Long accountId) {
        return null;
    }


    @Override
    public CommonResponse delete(long itemId, String accessToken, Long accountId) {
        if (!repository.existsById(itemId)) {
            throw new RuntimeException("Market item not found: " + itemId);
        }

        if (!jwtUtil.isTokenValid(accessToken)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to follow/unfollow on behalf of this account");
        }
        repository.deleteById(itemId);
        return new CommonResponse(true);
    }

    @Transactional
    public AdvertisementResponseDto updateAd(long id,
                                             AdvertisementRequestDto request,
                                             MultipartFile image,
                                             Long userId) {

        Advertisement ad = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ad not found"));

//        // Միայն սեփականատերը կարող է խմբագրել
        if (!ad.getOwner().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your advertisement");
        }

        ad.setTitle(request.getTitle());
        ad.setDescription(request.getDescription());
        ad.setPrice(request.getPrice());
        ad.setLocation(request.getLocation());

        // Նկարը թարմացնել միայն եթե նոր նկար է եկել
        if (image != null && !image.isEmpty()) {
            String newImageUrl ="public/"+ fileStorageService.uploadFile(image,ad.getOwner().getId());   // քո createAd-ում օգտագործվող նույն մեթոդը
            // ըստ ցանկության՝ ջնջել հին նկարը
            // imageStorageService.delete(ad.getImageUrl());
            ad.setImageUrl(newImageUrl);
            System.out.println(newImageUrl);
        }

        return mapper.toDto(repository.save(ad));
    }

    @Override
    public Advertisement findById(Long id) {
        return repository.findById(id).get();
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

    public AdvertisementSearchResponseDto preload(long itemId, long ownerId) {

        UserProfile userProfile = userProfileService.findById(ownerId).get();

        List<Advertisement> items;

        if (itemId == 0) {
            // Առաջին բեռնում — տվյալ owner-ի ամենավերջին item-ները
            items = repository.findLatestByOwner(
                    userProfile,
                    PageRequest.of(0, LIMIT)
            );
        } else {
            // Pagination — տվյալ owner-ի հաջորդ item-ները
            items = repository.findNextByOwner(
                    userProfile,
                    itemId,
                    PageRequest.of(0, LIMIT)
            );
        }

        // User-ի like արած Advertisement-ները
        List<AdvertisementLike> likedItems =
                advertisementLikeRepository.findByUserId(ownerId);

        Set<Long> itemIds = items.stream()
                .map(Advertisement::getId)
                .collect(Collectors.toSet());

        // Ավելացնում ենք like արածները, եթե արդեն list-ում չկան
        for (AdvertisementLike like : likedItems) {

            Advertisement advertisement = like.getAdvertisement();

            if (itemIds.add(advertisement.getId())) {
                items.add(advertisement);
            }
        }

        AdvertisementSearchResponseDto response =
                new AdvertisementSearchResponseDto();

        response.setError(false);
        response.setQuery("");
        response.setItemCount(items.size());

        // Cursor update
        if (items.isEmpty()) {
            response.setItemId(itemId);
        } else {
            response.setItemId(
                    items.get(items.size() - 1).getId()
            );
        }

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
        Advertisement advertisement = repository.findById(itemId).get();
        System.out.println(advertisement.getLocation());
        AdvertisementResponseDto dto = mapper.toDto(advertisement);
        System.out.println(dto.getLocation());

        return dto;
    }


}
 