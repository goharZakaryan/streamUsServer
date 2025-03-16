package com.example.streamusserver.post.controller;


import com.example.streamusserver.post.dto.request.CommentRequestDto;
import com.example.streamusserver.post.dto.response.CommentResponseDto;
import com.example.streamusserver.post.postService.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentDTO) {

        return new ResponseEntity<>(commentService.createComment(commentDTO), HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<Page<CommentResponseDto>> getCommentsByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<CommentResponseDto> comments = commentService.getCommentsByPostId(postId, page, size);
        return ResponseEntity.ok(comments);
    }
    @PostMapping("/get")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(
            @RequestBody CommentRequestDto commentDTO) {

        List<CommentResponseDto> comments = commentService.getCommentsByPostId(commentDTO);
        return ResponseEntity.ok(comments);
    }

//    @GetMapping("/{commentId}/replies")
//    public ResponseEntity<List<CommentDTO>> getRepliesForComment(@PathVariable Long commentId) {
//        List<CommentDTO> replies = commentService.getRepliesForComment(commentId);
//        return ResponseEntity.ok(replies);
//    }
//
//    @PutMapping("/{commentId}")
//    public ResponseEntity<?> updateComment(
//            @PathVariable Long commentId,
//            @RequestBody Map<String, String> payload,
//            @RequestHeader("Authorization") String token) {
//
//        Long userId = extractUserIdFromToken(token);
//        String content = payload.get("content");
//
//        try {
//            CommentDTO updatedComment = commentService.updateComment(commentId, userId, content);
//            return ResponseEntity.ok(updatedComment);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
//        }
//    }
//
//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<?> deleteComment(
//            @PathVariable Long commentId,
//            @RequestHeader("Authorization") String token) {
//
//        Long userId = extractUserIdFromToken(token);
//
//        try {
//            commentService.deleteComment(commentId, userId);
//            return ResponseEntity.noContent().build();
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e)
//        }
    }
