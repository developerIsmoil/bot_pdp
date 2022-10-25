package com.example.appwebhooktelegram.service;

import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;

public interface BotStateContext {
    AnswerPreCheckoutQuery processPreCheckoutQuery(PreCheckoutQuery preCheckoutQuery);

    SendMessage processSuccessfullyPaymentMessage(Message message);
}
