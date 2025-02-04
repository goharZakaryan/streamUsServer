package com.example.streamusserver.controller;

import com.example.streamusserver.dto.FollowRequestDto;
import com.example.streamusserver.dto.FollowResponseDto;
import com.example.streamusserver.service.FollowRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class FollowerController {
    private final FollowRequestService service;

    @PostMapping("/follow")
    public ResponseEntity<FollowResponseDto> followProfile(@RequestBody FollowRequestDto request) {

        FollowResponseDto response = service.handleFollow(request);

        if (response.isError()) {
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
    @GetMapping("/checkIsFollowed")
    public ResponseEntity<Boolean> checkIsFollow(@RequestParam("id") Long id, @RequestParam("authId") Long authId) {
        return ResponseEntity.ok( service.isFollowing(authId,id));
    }

}
