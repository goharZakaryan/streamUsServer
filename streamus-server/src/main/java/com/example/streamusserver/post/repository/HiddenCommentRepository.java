package com.example.streamusserver.post.repository;

import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.model.Comment;
import com.example.streamusserver.post.model.HideComment;
import com.example.streamusserver.post.model.HidePost;
import com.example.streamusserver.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HiddenCommentRepository extends JpaRepository<HideComment,Long> {
    void deleteByUserAndComment(UserProfile user, Comment comment);
}
