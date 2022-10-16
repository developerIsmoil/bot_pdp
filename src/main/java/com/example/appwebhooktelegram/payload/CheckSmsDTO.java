package com.example.appwebhooktelegram.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckSmsDTO {
    private String password;
    private String prePassword;
    private String phoneNumber;
    private String smsCode;
    private String smsCodeId;
}
