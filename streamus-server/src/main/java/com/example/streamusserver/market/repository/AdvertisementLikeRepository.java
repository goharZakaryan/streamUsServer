package com.example.streamusserver.market.repository;

import com.example.streamusserver.market.entity.AdvertisementLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertisementLikeRepository extends JpaRepository<AdvertisementLike, Long> {

    Optional<AdvertisementLike> findByAdvertisementIdAndUserId(
            Long advertisementId,
            Long userId
    );

    List<AdvertisementLike> findByUserId(Long userId);

    boolean existsByAdvertisementIdAndUserId(
            Long advertisementId,
            Long userId
    );

    void deleteByAdvertisementIdAndUserId(
            Long advertisementId,
            Long userId
    );
}
