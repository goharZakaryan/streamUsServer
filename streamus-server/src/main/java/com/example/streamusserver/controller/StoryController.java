package com.example.streamusserver.controller;

import com.example.streamusserver.dto.reduest.StoryRequestDto;
import com.example.streamusserver.model.Story;
import com.example.streamusserver.model.StoryGroup;
import com.example.streamusserver.service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stories")
public class StoryController {

    @Autowired
    private StoryService storyService;

    @PostMapping("/create")
    public ResponseEntity<Story> createStory(
            @RequestParam("userId") Long userId,
            @RequestParam("storyType") String storyType,
            @RequestParam("caption") String caption,
            @RequestParam("media") MultipartFile mediaFile
    ) {

        try {
            Story story = storyService.createStory(userId,storyType,caption, mediaFile);
            return ResponseEntity.ok(story);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/feed/{userId}")
    public ResponseEntity<List<StoryGroup>> getStoriesFeed(
            @PathVariable Long userId,
            @RequestParam List<Long> followingIds) {

        List<StoryGroup> stories = storyService.getStoriesForUser(userId, followingIds);
        return ResponseEntity.ok(stories);
    }

    @PostMapping("/{storyId}/view")
    public ResponseEntity<Void> viewStory(
            @PathVariable Long storyId,
            @RequestParam Long viewerId) {

        storyService.viewStory(storyId, viewerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{storyId}/viewers")
    public ResponseEntity<List<Long>> getStoryViewers(
            @PathVariable Long storyId,
            @RequestParam Long ownerId) {

        List<Long> viewers = storyService.getStoryViewers(storyId, ownerId);
        return ResponseEntity.ok(viewers);
    }

    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(
            @PathVariable Long storyId,
            @RequestParam Long userId) {

        storyService.deleteStory(storyId, userId);
        return ResponseEntity.ok().build();
    }
}
