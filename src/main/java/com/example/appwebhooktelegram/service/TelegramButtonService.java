package com.example.appwebhooktelegram.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

public interface TelegramButtonService {
    ReplyKeyboardMarkup replyKeyboardMaker(List<String> buttons);
}
