package com.example.streamusserver.post.controller;

import com.example.streamusserver.post.dto.request.HideItemRequestDto;
import com.example.streamusserver.post.postService.HiddenCommentService;
import com.example.streamusserver.post.postService.HiddenPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/hide")
public class HiddenCommentController {


    @Autowired
    private HiddenCommentService hiddenContentService;

    @PostMapping("/comment")
    public ResponseEntity<Void> hideComment(@RequestBody HideItemRequestDto hideItemRequestDto) {
        hiddenContentService.hidePost(hideItemRequestDto);
        return ResponseEntity.ok().build();
    }


}
