package com.example.streamusserver.post.repository;

import com.example.streamusserver.post.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPostIdAndParentCommentIsNullOrderByCreatedAtDesc(Long postId, Pageable pageable);

    List<Comment> findByPostIdAndParentCommentIsNullOrderByCreatedAtDesc(Long postId);

    List<Comment> findByPostId(Long postId);

    int countByPostId(Long postId);

    @Query("""
            SELECT c
            FROM Comment c
            WHERE c.post.id = :postId
            AND c.parentComment IS NULL
            AND c.id NOT IN (
                SELECT hc.comment.id
                FROM HideComment hc
                WHERE hc.user.id = :userId
            )
            ORDER BY c.createdAt DESC
            """)
    List<Comment> findVisibleComments(Long userId, Long postId);

    @Query("""
            SELECT c
            FROM Comment c
            WHERE c.rootId = :rootId
            AND c.id NOT IN (
                SELECT hc.comment.id
                FROM HideComment hc
                WHERE hc.user.id = :userId
            )
            ORDER BY c.createdAt ASC, c.id ASC
            """)
    List<Comment> findVisibleRepliesByRootId(@Param("rootId") Long rootId,
                                             @Param("userId") Long userId);

    List<Comment> findAllByRootIdOrderByCreatedAtAscIdAsc(Long id);

    @Query("""
                SELECT COUNT(c)
                FROM Comment c
                WHERE c.rootId = :rootId
                  AND NOT EXISTS (
                      SELECT hc.id
                      FROM HideComment hc
                      WHERE hc.comment = c
                        AND hc.user.id = :userId
                  )
            """)
    int countVisibleReplies(@Param("rootId") Long rootId,
                            @Param("userId") Long userId);

    Optional<Comment> findByIdAndUser_Id(Long commentId, Long userId);

    void deleteRepliesByRootId(Long rootId);

 List<Comment> findAllByRootId(Long id);
    @Query("""
    SELECT c
    FROM Comment c
    WHERE c.rootId = :commentId
    ORDER BY c.createdAt ASC, c.id ASC
""")
    List<Comment> findRepliesByRootId(@Param("commentId") Long commentId);
}
