package com.example.appwebhooktelegram.service;

import com.example.appwebhooktelegram.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotAuthService {
    void gettingNumber(User user, Update update, SendMessage sendMessage);

    boolean checkUserIfRegistered(String phoneNumber);

    void cabinet(SendMessage sendMessage, User user);

    void register(Update update, SendMessage sendMessage);

    void setPasswordState(SendMessage sendMessage, Update update, User user);

    void setPassword(Update update, SendMessage sendMessage, User user);

    void checkSmsCode(Update update, SendMessage sendMessage, User user);
}
