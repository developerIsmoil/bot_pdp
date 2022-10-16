package com.example.appwebhooktelegram.service;

import com.example.appwebhooktelegram.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotStatusService {
    void cabinetMessageService(String text, SendMessage sendMessage, User user);

    void statusManagerService(User user, SendMessage sendMessage, Update update);
}
