package com.engis.esb.api.timeCheck.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {

    private String userId;
    private String userPw;

    @Builder
    public LoginRequest(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }
}
