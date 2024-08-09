package com.news.model;

import lombok.Data;

@Data
public class AccessTokenResponse {
    private String accessToken;
    private String refreshToken;

    public AccessTokenResponse(String accessToken,String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
