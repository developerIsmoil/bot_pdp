package com.example.appwebhooktelegram.controller;

import com.example.appwebhooktelegram.service.TelegramBotServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("/api/telegram")
@RequiredArgsConstructor
public class WebhookController {
    private final TelegramBotServiceImpl telegramBotServiceImpl;

    @PostMapping
    public void getUpdate(@RequestBody Update update) {
        telegramBotServiceImpl.requestManager(update);
    }
}
