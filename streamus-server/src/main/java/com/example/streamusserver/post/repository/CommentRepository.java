package com.example.streamusserver.post.repository;

import com.example.streamusserver.post.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAtDesc(Long postId);
    Page<Comment> findByPostIdAndParentCommentIsNullOrderByCreatedAtDesc(Long postId, Pageable pageable);
    List<Comment> findByParentCommentIdOrderByCreatedAtAsc(Long parentCommentId);
    List<Comment> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Comment> findByPostId(Long postId);
    int countByPostId(Long postId);
}
