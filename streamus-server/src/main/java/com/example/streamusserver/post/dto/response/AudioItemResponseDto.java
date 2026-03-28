package com.example.streamusserver.post.dto.response;

public class AudioItemResponseDto {
    private Long id;
    private String audioUrl;
    private String selectedAudioFileName;
    private String duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getSelectedAudioFileName() {
        return selectedAudioFileName;
    }

    public void setSelectedAudioFileName(String selectedAudioFileName) {
        this.selectedAudioFileName = selectedAudioFileName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
