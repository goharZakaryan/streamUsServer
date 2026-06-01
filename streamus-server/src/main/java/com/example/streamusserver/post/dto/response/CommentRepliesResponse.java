package com.example.streamusserver.post.dto.response;

import com.example.streamusserver.post.model.Comment;

import java.util.List;

public class CommentRepliesResponse {


    private boolean error;

    private List<Comment> replies;

    public boolean isError() {
        return error;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }
}
