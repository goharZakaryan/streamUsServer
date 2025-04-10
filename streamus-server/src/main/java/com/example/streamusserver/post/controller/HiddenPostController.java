package com.example.streamusserver.post.controller;

import com.example.streamusserver.post.dto.request.HideItemRequestDto;
import com.example.streamusserver.post.postService.HiddenPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
public class HiddenPostController {


    @Autowired
    private HiddenPostService hiddenContentService;

    @PostMapping("/hide")
    public ResponseEntity<Void> hidePost(@RequestBody HideItemRequestDto hideItemRequestDto) {
        hiddenContentService.hidePost(hideItemRequestDto);
        return ResponseEntity.ok().build();
    }


}
