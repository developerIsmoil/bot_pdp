package com.example.appwebhooktelegram.payload;

import lombok.Data;

@Data
public class CheckNumberDTO {
    private Boolean success;
    private CheckNumberData data;

}

