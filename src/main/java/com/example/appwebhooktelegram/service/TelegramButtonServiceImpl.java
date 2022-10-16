package com.example.appwebhooktelegram.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegramButtonServiceImpl implements TelegramButtonService {
    //STRING LIST BERIB YUBORILSA ICHIDAGI ELEMENTLARGA MOS BUTTON LARNI YASAB BERADI,
    // BUTTONLAR BITTA QATORGA IKKITADAN JOYLANADI
    public ReplyKeyboardMarkup replyKeyboardMaker(List<String> buttons) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        for (int i = 0; i < buttons.size(); i++) {
            if (i > 0 && i % 2 == 0) {
                keyboard.add(keyboardRow);
                keyboardRow = new KeyboardRow();
            }
            KeyboardButton keyboardButton = new KeyboardButton();
            keyboardButton.setText(buttons.get(i));
            keyboardRow.add(keyboardButton);
        }
        keyboard.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }


}
