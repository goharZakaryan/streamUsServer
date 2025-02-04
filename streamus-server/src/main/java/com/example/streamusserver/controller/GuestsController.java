package com.example.streamusserver.controller;

import com.example.streamusserver.dto.guests.GuestSaveRequestDto;
import com.example.streamusserver.dto.guests.GuestsRequest;
import com.example.streamusserver.dto.guests.GuestsResponse;
import com.example.streamusserver.model.Guest;
import com.example.streamusserver.service.GuestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/guests")
public class GuestsController {
    @Autowired
    private GuestsService guestsService;

    @PostMapping("/get")
    public ResponseEntity<GuestsResponse> getGuests(@RequestBody GuestsRequest request) {
        return ResponseEntity.ok(guestsService.getGuests(request));
    }
    @GetMapping("/isExist")
    public ResponseEntity<Boolean> isExist(@RequestParam("profileID") Long request) {
        System.out.println("isExist");
        return ResponseEntity.ok(guestsService.isGuestExist(request));
    }

    @PostMapping("/save")
    public ResponseEntity<Guest> saveGuest(@RequestBody GuestSaveRequestDto request) {
        return ResponseEntity.ok(guestsService.saveGuest(request));
    }
}

