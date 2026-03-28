package com.example.streamusserver.post.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum MediaType {
    AUDIO("audio"),
    VIDEO("video"),
    PHOTO("photo");

    private final String value;

    MediaType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
