package com.example.streamusserver.service;

import com.example.streamusserver.dto.guests.GuestSaveRequestDto;
import com.example.streamusserver.dto.guests.GuestsRequest;
import com.example.streamusserver.dto.guests.GuestsResponse;
import com.example.streamusserver.model.Guest;

public interface GuestsService {
    GuestsResponse getGuests(GuestsRequest request);
    Boolean isGuestExist(Long request);

    Guest saveGuest(GuestSaveRequestDto request);
}
