package com.engis.esb.api.timeCheck.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private Long userNo;
    private String userName;
    private boolean success;
}
