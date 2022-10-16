package com.example.appwebhooktelegram.payload;

import lombok.Data;

@Data
public class SendSmsCodeDTO {
    private Boolean success;
    private SmsSendDTO data;
}
