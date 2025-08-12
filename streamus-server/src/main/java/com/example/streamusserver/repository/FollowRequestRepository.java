package com.example.streamusserver.repository;

import com.example.streamusserver.model.Follower;
import com.example.streamusserver.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowRequestRepository extends JpaRepository<Follower, Long> {
    boolean existsByFollowerAndFollowing(UserProfile follower, UserProfile following);

    void deleteByFollowerAndFollowing(UserProfile follower, UserProfile following);

    int countByFollowing(UserProfile following);

    int countByFollower(UserProfile following);

    @Query("SELECT f.follower FROM Follower f WHERE f.following.id = :followingId")
    List<UserProfile> findAllFollowerByFollowingId(@Param("followingId") Long followingId);
 @Query("SELECT f.follower FROM Follower f WHERE f.follower.id = :followerId")
    List<UserProfile> findAllFollowerByFollowerId(@Param("followerId") Long followerId);

}
