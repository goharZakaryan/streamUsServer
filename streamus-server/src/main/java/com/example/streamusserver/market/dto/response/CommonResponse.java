package com.example.streamusserver.market.dto.response;

public class CommonResponse {
    private boolean error;

    public CommonResponse(boolean error) {
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
