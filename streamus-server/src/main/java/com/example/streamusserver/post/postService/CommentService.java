package com.example.streamusserver.post.postService;

import com.example.streamusserver.post.dto.request.CommentRequestDto;
import com.example.streamusserver.post.dto.response.CommentResponseDto;
import com.example.streamusserver.post.model.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(CommentRequestDto commentDTO);
    Page<CommentResponseDto> getCommentsByPostId(Long postId, int page, int size);
    List<Comment> getCommentsByPostId(Long postId);
    List<CommentResponseDto> getCommentsByPostId(CommentRequestDto commentRequestDto);

    void deleteAll(List<Comment> commentsByPostId);
}
