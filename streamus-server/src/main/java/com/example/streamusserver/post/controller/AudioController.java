package com.example.streamusserver.post.controller;

import com.example.streamusserver.post.dto.response.AudioItemResponseDto;
import com.example.streamusserver.post.dto.response.LikeResponse;
import com.example.streamusserver.post.postService.AudioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audios")
@RequiredArgsConstructor
public class AudioController {
    private final AudioService audioService;

    @GetMapping("/profile")
    public ResponseEntity<List<AudioItemResponseDto>> loadProfileAudio(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.ok(audioService.getUserAudios(id));
        } catch (Exception e) {
            e.printStackTrace();
            // Return a meaningful error code (e.g., HTTP 500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping()
    public ResponseEntity<List<AudioItemResponseDto>> loadAudios() {
        try {
            return ResponseEntity.ok(audioService.getAudios());
        } catch (Exception e) {
            e.printStackTrace();
            // Return a meaningful error code (e.g., HTTP 500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/user/add-audio/audioId")
    public ResponseEntity<LikeResponse> addAudioToUser(
            @RequestParam Long userId,
            @RequestParam Long audioId) {
        return ResponseEntity.ok(audioService.likeAudio(userId, audioId));
    }
    @DeleteMapping("/users/{userId}/audio/{audioId}")
    public ResponseEntity<?> deleteAudio(
            @PathVariable(name = "userId") Long userId,
            @PathVariable (name = "audioId")Long audioId
    ) {
        audioService.deleteAudio(userId, audioId);
        return ResponseEntity.ok().build();
    }
}
