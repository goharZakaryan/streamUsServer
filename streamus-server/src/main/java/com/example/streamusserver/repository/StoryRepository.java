package com.example.streamusserver.repository;

import com.example.streamusserver.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    @Query("SELECT s FROM Story s WHERE s.userId = :userId AND s.isActive = true AND s.expiresAt > :now ORDER BY s.createdAt DESC")
    List<Story> findActiveStoriesByUserId(@Param("userId") Long userId, @Param("now") LocalDateTime now);

    @Query("SELECT s FROM Story s WHERE s.userId IN :userIds AND s.isActive = true AND s.expiresAt > :now ORDER BY s.createdAt DESC")
    List<Story> findActiveStoriesByUserIds(@Param("userIds") List<Long> userIds, @Param("now") LocalDateTime now);

    @Modifying
    @Query("UPDATE Story s SET s.isActive = false WHERE s.expiresAt <= :now")
    void deactivateExpiredStories(@Param("now") LocalDateTime now);
}
