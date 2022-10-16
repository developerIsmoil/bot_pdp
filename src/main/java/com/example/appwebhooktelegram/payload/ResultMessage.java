package com.example.appwebhooktelegram.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Message;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultMessage {
    private boolean ok;
    private Message result;
}
