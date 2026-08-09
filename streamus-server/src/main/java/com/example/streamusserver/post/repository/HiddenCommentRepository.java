package com.example.streamusserver.post.repository;

import com.example.streamusserver.model.UserProfile;
import com.example.streamusserver.post.model.Comment;
import com.example.streamusserver.post.model.HideComment;
import com.example.streamusserver.post.model.HidePost;
import com.example.streamusserver.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HiddenCommentRepository extends JpaRepository<HideComment,Long> {
    void deleteByUserAndComment(UserProfile user, Comment comment);
    void deleteAllByCommentId(Long commentId);
    @Modifying
    @Query("""
    DELETE FROM HideComment hc
    WHERE hc.comment.id = :commentId
       OR hc.comment.rootId = :commentId
""")
    void deleteByCommentOrRootId(@Param("commentId") Long commentId);

}
