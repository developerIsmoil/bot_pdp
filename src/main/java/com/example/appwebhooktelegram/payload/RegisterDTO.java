package com.example.appwebhooktelegram.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDTO {
    private String phoneNumber;
    private String password;
    private String prePassword;

    public RegisterDTO(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
