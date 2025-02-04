package com.example.streamusserver.repository;

import com.example.streamusserver.model.Guest;
import com.example.streamusserver.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByGuestTo(UserProfile accountId);
    Boolean existsByGuestToAndViewed(UserProfile accountId, Boolean viewed);
    Optional<Guest> findByGuestToAndGuestUserId(UserProfile guestTo, UserProfile guestUserId);

}
