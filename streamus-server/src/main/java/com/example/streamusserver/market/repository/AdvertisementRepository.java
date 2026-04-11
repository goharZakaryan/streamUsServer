package com.example.streamusserver.market.repository;

import com.example.streamusserver.market.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
}
