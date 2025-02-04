package com.example.streamusserver.security;

import com.example.streamusserver.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    @Lazy
    private UserProfileService userProfileService; // Replace with your actual repository


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userProfileService.findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities("USER") // Replace with actual roles/authorities
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

