package com.example.streamusserver.dto.guests;


import com.example.streamusserver.model.Guest;

import java.util.List;


public class GuestsResponse {
    private boolean error;

    private int itemId;

    private List<GuestDto> guests;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public List<GuestDto> getGuests() {
        return guests;
    }

    public void setGuests(List<GuestDto> guests) {
        this.guests = guests;
    }
}
