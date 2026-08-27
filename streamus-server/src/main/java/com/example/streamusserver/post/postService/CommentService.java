package com.example.streamusserver.post.postService;

import com.example.streamusserver.post.dto.request.CommentRequestDto;
import com.example.streamusserver.post.dto.request.HideItemRequestDto;
import com.example.streamusserver.post.dto.response.CommentRepliesResponse;
import com.example.streamusserver.post.dto.response.CommentResponseDto;
import com.example.streamusserver.post.dto.response.CommentsResponseDto;
import com.example.streamusserver.post.model.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(CommentRequestDto commentDTO);
    Page<CommentResponseDto> getCommentsByPostId(Long postId, int page, int size);
    List<CommentResponseDto> getCommentsByPostId(Long postId);
    List<CommentResponseDto> getCommentsByPostId(CommentRequestDto commentRequestDto);
    List<CommentResponseDto> getNotifiedComments(CommentRequestDto commentRequestDto);

    void deleteAll(List<Comment> commentsByPostId);

   CommentRepliesResponse getRepliesForComment(Long commentId, Long accountId, String accessToken);

    Comment findById(Long commentId);

    void deleteComment(HideItemRequestDto hideItemRequestDto);
}
