package com.example.streamusserver.post.dto.response;

import java.util.List;

public class CommentsResponseDto {
    private List<CommentResponseDto> commentsResponseDtos;

    public CommentsResponseDto(List<CommentResponseDto> commentsResponseDtos) {
        this.commentsResponseDtos = commentsResponseDtos;
    }

    public List<CommentResponseDto> getCommentsResponseDtos() {
        return commentsResponseDtos;
    }

    public void setCommentsResponseDtos(List<CommentResponseDto> commentsResponseDtos) {
        this.commentsResponseDtos = commentsResponseDtos;
    }
}
