package com.example.appwebhooktelegram.service;

import com.example.appwebhooktelegram.entity.User;
import com.example.appwebhooktelegram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.payments.Invoice;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.payments.PreCheckoutQuery;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;

import java.util.Optional;
@Component
@RequiredArgsConstructor
public class BotStateContextImpl implements BotStateContext {
    private final UserRepository userRepository;
    @Override
    public AnswerPreCheckoutQuery processPreCheckoutQuery(PreCheckoutQuery preCheckoutQuery) {
        AnswerPreCheckoutQuery answerPreCheckoutQuery = new AnswerPreCheckoutQuery();
        answerPreCheckoutQuery.setPreCheckoutQueryId(preCheckoutQuery.getId());
        answerPreCheckoutQuery.setOk(true);
        return answerPreCheckoutQuery;
    }

    @Override
    public SendMessage processSuccessfullyPaymentMessage(Message message) {
        String chatId = message.getChatId().toString();
        Invoice invoice = message.getInvoice();
        invoice.setTitle("Pay");
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());

        SuccessfulPayment successfulPayment = message.getSuccessfulPayment();
        successfulPayment.setInvoicePayload(invoice.toString());
        successfulPayment.setTotalAmount(invoice.getTotalAmount());
        Optional<User> optionalUser = userRepository.findByChatId(chatId.toString());
        if (optionalUser.isEmpty()){
            User user = new User();
        }
        User user = optionalUser.get();
        user.setSuccessfulPayment(successfulPayment);
        userRepository.save(user);
        return sendMessage;
    }
}
