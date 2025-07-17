package com.example.streamusserver.repository;

import com.example.streamusserver.model.StoryView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryViewRepository extends JpaRepository<StoryView, Long> {
    @Query("SELECT sv FROM StoryView sv WHERE sv.story.id = :storyId ORDER BY sv.viewedAt DESC")
    List<StoryView> findByStoryIdOrderByViewedAtDesc(@Param("storyId") Long storyId);

    @Query("SELECT COUNT(sv) FROM StoryView sv WHERE sv.story.id = :storyId")
    int countViewsByStoryId(@Param("storyId") Long storyId);

    @Query("SELECT CASE WHEN COUNT(sv) > 0 THEN true ELSE false END FROM StoryView sv WHERE sv.story.id = :storyId AND sv.viewer.id = :viewerId")
    boolean existsByStoryIdAndViewerId(@Param("storyId") Long storyId, @Param("viewerId") Long viewerId);
}
