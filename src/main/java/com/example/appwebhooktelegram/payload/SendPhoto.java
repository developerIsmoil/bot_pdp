package com.example.appwebhooktelegram.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SendPhoto {
    @JsonProperty("chat_id")
    private String chatId;
    private String photo;
    private String caption;
}
