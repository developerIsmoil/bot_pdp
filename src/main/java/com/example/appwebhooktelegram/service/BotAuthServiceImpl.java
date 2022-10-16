package com.example.appwebhooktelegram.service;

import com.example.appwebhooktelegram.entity.User;
import com.example.appwebhooktelegram.feign.UniversityFeign;
import com.example.appwebhooktelegram.payload.CheckSmsDTO;
import com.example.appwebhooktelegram.payload.RegisterDTO;
import com.example.appwebhooktelegram.payload.RegisterResultDTO;
import com.example.appwebhooktelegram.payload.SendSmsCodeDTO;
import com.example.appwebhooktelegram.repository.UserRepository;
import com.example.appwebhooktelegram.utils.BotState;
import com.example.appwebhooktelegram.utils.Buttons;
import com.example.appwebhooktelegram.utils.RestConstants;
import com.example.appwebhooktelegram.utils.StateButtonMap;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BotAuthServiceImpl implements BotAuthService {
    private final UserRepository userRepository;
    private final UniversityFeign universityFeign;
    private final TelegramButtonService telegramButtonService;

    public void gettingNumber(User user, Update update, SendMessage sendMessage) {
        Contact contact = update.getMessage().getContact();
        if (contact == null) {
            register(update, sendMessage);
            return;
        }

        user.setPhoneNumber(validPhone(contact.getPhoneNumber()));

        if (!checkUserIfRegistered(user.getPhoneNumber())) {
            sendMessage.setText("Bu botdan foydalanish uchun avval platformadan ro'yxatdan o'tishingiz kerak.");
            sendMessage.setReplyMarkup(telegramButtonService.replyKeyboardMaker(StateButtonMap.stateButtonMap.get(BotState.REGISTER_PENDING)));
            user.setState(BotState.REGISTER_PENDING);
        } else {
            cabinet(sendMessage, user);
            user.setState(BotState.CABINET);
        }
    }


    //USERGA UNIVERSITY TOMONIDAN JO'NATILGAN SMS CODE NI TASDIQLAYDI VA RO'YXATDAN O'TADI
    @Override
    public void checkSmsCode(Update update, SendMessage sendMessage, User user) {
        String smsCode = update.getMessage().getText();
        if (checkText(smsCode, sendMessage)) return;
        CheckSmsDTO checkSmsDTO = new CheckSmsDTO(user.getPassword(), user.getPassword(), user.getPhoneNumber(), smsCode, user.getSmsCodeId());
        try {
            HttpEntity<RegisterResultDTO> httpEntity = universityFeign.checkSmsCode(checkSmsDTO);
            if (Objects.requireNonNull(httpEntity.getBody()).getSuccess()) {
                sendMessage.setText("muvaffaqiyatli ro'yxatdan o'tdingiz");
                user.setState(BotState.CABINET);
            }

        } catch (Exception e) {
            sendMessage.setText("Tasdiqlash kodi xato yoki avval ishlatilgan");
//            user.setState(BotState.CABINET);
        }
    }

    //UNIVERSITET DAN RO'YXATDAN O'TGAN YOKI O'TMAGANLIGINI TEKSHIRADI
    public boolean checkUserIfRegistered(String phoneNumber) {
        return true;
//        return Objects.requireNonNull(universityFeign.checkRegister(new RegisterDTO(phoneNumber)).getBody()).getData().getRegistered();
    }

    // AGAR RO'YXATDAN O'TGAN BO'LSA CABINET GA O'TADI
    public void cabinet(SendMessage sendMessage, User user) {
        sendMessage.setReplyMarkup(telegramButtonService.replyKeyboardMaker(StateButtonMap.stateButtonMap.get(BotState.CABINET)));
        sendMessage.setText("personal cabinet");
        user.setState(BotState.CABINET);
    }


    // BU BO'LIMDA FOYDALANUVCHI FAQATGINA BOT UCHUN RO'YXATDAN O'TADI
    public void register(Update update, SendMessage sendMessage) {
        String chatId = update.getMessage().getChatId().toString();
        Optional<User> optionalUser = userRepository.findByChatId(chatId);
        User user = optionalUser.orElseGet(User::new);
        user.setChatId(chatId);
        user.setUsername(update.getMessage().getChat().getUserName());
        user.setState(BotState.WAITING_PHONE_NUMBER);
        userRepository.save(user);
        sendMessage.setText("please send your phone number");
        // create keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        // new list
        List<KeyboardRow> keyboard = new ArrayList<>();

        // first keyboard line
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText(Buttons.SEND_NUMBER);
        keyboardButton.setRequestContact(true);
        keyboardFirstRow.add(keyboardButton);

        // add array to list
        keyboard.add(keyboardFirstRow);

        // add list to our keyboard
        replyKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
    }


    //USER PAROL KIRITMOQCHI BO'LSA UNI SATE NI PAROL KIRITISHGA O'ZGARTIRILADI
    // USER PAROL KIRITISHI UCHUN UNING HOLATI WAITING_FOR PASSWORD GA TUSHADI
    @Override
    public void setPasswordState(SendMessage sendMessage, Update update, User user) {
        String text = update.getMessage().getText();
        if (checkText(text, sendMessage)) return;

        if (text.equals(Buttons.SET_PASSWORD)) {
            sendMessage.setText(RestConstants.MESSAGE_REQUIREMENTS);
            sendMessage.setReplyMarkup(null);
            user.setState(BotState.WAITING_FOR_PASSWORD);
        } else {
            sendMessage.setText("to'g'ri ma'lumot kiritish uchun menu dan foydalaning");
        }
    }

    @Override
    public void setPassword(Update update, SendMessage sendMessage, User user) {
        String text = update.getMessage().getText();
        if (checkText(text, sendMessage)) return;
        if (text.contains(" ") || text.length() <= 6) {
            sendMessage.setReplyMarkup(null);
            sendMessage.setText(RestConstants.MESSAGE_REQUIREMENTS);
            return;
        }
//        if (!text.matches(RestConstants.PASSWORD_REGEX)) {
//            sendMessage.setText("Parol kamida bitta katta harf, bitta kichik harf, bitta belgi va bitta raqamdan iborat bo'lishi kerak. Qaytadan kiriting!");
//            return;
//        }
        RegisterDTO registerDTO = new RegisterDTO(user.getPhoneNumber(), text, text);
        user.setPassword(text);
        try {

            HttpEntity<SendSmsCodeDTO> httpEntity = universityFeign.setPassword(registerDTO);

            if (Objects.requireNonNull(httpEntity.getBody()).getSuccess()) {
                user.setSmsCodeId(httpEntity.getBody().getData().getSmsCodeId());
                user.setState(BotState.WAITING_FOR_SMS_CODE);
                sendMessage.setReplyMarkup(telegramButtonService.replyKeyboardMaker(List.of(Buttons.BACK)));
                sendMessage.setText("iltimos sms code kelgunicha botga hech narsa yozmang!");
            }
        } catch (Exception ignored) {

        }
    }


    public boolean checkText(String text, SendMessage sendMessage) {
        if (text == null) {
            sendMessage.setText("noto'g'ri ma'lumot");
            return true;
        }
        return false;
    }

    public String validPhone(String phoneNumber) {
        return phoneNumber.charAt(0) == '+' ? phoneNumber : "+" + phoneNumber;
    }
}
