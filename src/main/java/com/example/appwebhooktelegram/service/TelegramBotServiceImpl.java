package com.example.appwebhooktelegram.service;

import com.example.appwebhooktelegram.entity.User;
import com.example.appwebhooktelegram.feign.TelegramFeign;
import com.example.appwebhooktelegram.repository.UserRepository;
import com.example.appwebhooktelegram.utils.BotState;
import com.example.appwebhooktelegram.utils.RestConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.AnswerPreCheckoutQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TelegramBotServiceImpl implements TelegramBotService {

    private final BotStatusService botStatusService;
    private final BotAuthService botAuthService;
    private final RestTemplate restTemplate;
    public final UserRepository userRepository;
    public final TelegramFeign telegramFeign;
    public final BotStateContextImpl botStateContext;

    public void requestManagerClick(Update update) {
        try {
            if (update.getMessage() == null) return;
            AnswerPreCheckoutQuery answerPreCheckoutQuery = botStateContext.processPreCheckoutQuery(update.getPreCheckoutQuery());
            SendMessage sendMessage = botStateContext.processSuccessfullyPaymentMessage(update.getMessage());

            String chatId = update.getMessage().getChatId().toString();
            Optional<User> optionalUser = userRepository.findByChatId(chatId);
            sendMessage.setChatId(chatId);
            sendMessage.setText("Successfully pay");

            if (optionalUser.isEmpty()) {
                //AGAR USER HALI TELEGRAM BOT DAN RO'YXATDAN O'TMAGAN BO'LSA BA'ZAGA SAQLANADI
                botAuthService.register(update, sendMessage);
            } else {
                //USER NING STATUSIGA QARAB KELGAN MESSAGE GA JAVOB QAYTARADI
                User user = optionalUser.get();
                user.setState(BotState.CABINET);
                botStatusService.statusManagerService(user, sendMessage, update);
                userRepository.save(user);
            }
            sendCustomMessage(sendMessage);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void requestManager(Update update) {
        SendMessage sendMessage = new SendMessage();
        if (update.getMessage() == null) return;
        String chatId = update.getMessage().getChatId().toString();
        Optional<User> optionalUser = userRepository.findByChatId(chatId);
        sendMessage.setChatId(chatId);
        sendMessage.setText("Enter button");
        if (optionalUser.isEmpty()) {
            //AGAR USER HALI TELEGRAM BOT DAN RO'YXATDAN O'TMAGAN BO'LSA BA'ZAGA SAQLANADI
            botAuthService.register(update, sendMessage);
        } else {
            //USER NING STATUSIGA QARAB KELGAN MESSAGE GA JAVOB QAYTARADI
            User user = optionalUser.get();
            botStatusService.statusManagerService(user, sendMessage, update);
            userRepository.save(user);
        }
        sendCustomMessage(sendMessage);
    }

    private void sendCustomMessage(SendMessage sendMessage) {
        telegramFeign.sendMessage(RestConstants.BOT_TOKEN, sendMessage);
    }
}
