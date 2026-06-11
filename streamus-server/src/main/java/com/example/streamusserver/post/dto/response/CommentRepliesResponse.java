package com.example.streamusserver.post.dto.response;

import com.example.streamusserver.post.model.Comment;

import java.util.List;

public class CommentRepliesResponse {


    private boolean error;

    private List<CommentResponseDto> replies;

    public boolean isError() {
        return error;
    }

    public List<CommentResponseDto> getReplies() {
        return replies;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setReplies(List<CommentResponseDto> replies) {
        this.replies = replies;
    }
}
