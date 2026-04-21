package com.example.streamusserver.market.repository;

import com.example.streamusserver.market.entity.Advertisement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

        @Query("SELECT m FROM Advertisement m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :query, '%')) AND m.id < :itemId ORDER BY m.id DESC")
        List<Advertisement> search(@Param("query") String query, @Param("itemId") int itemId);

        @Query("SELECT m FROM Advertisement m WHERE m.id < :itemId ORDER BY m.id DESC")
        List<Advertisement> findAllWithPagination(@Param("itemId") int itemId);
    @Query("SELECT a FROM Advertisement a ORDER BY a.id DESC")
    List<Advertisement> findLatest(Pageable pageable);

    // հաջորդ էջ (cursor pagination)
    @Query("SELECT a FROM Advertisement a WHERE a.id < :itemId ORDER BY a.id DESC")
    List<Advertisement> findNext(@Param("itemId") long itemId, Pageable pageable);
}
