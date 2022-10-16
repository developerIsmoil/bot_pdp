package com.example.appwebhooktelegram.payload;

import lombok.Data;

@Data
public class SmsSendDTO {
    private boolean reliableDevice;
    private String smsCodeId;
}
