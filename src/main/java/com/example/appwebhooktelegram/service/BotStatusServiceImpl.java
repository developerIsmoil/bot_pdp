package com.example.appwebhooktelegram.service;

import com.example.appwebhooktelegram.entity.User;
import com.example.appwebhooktelegram.feign.TelegramFeign;
import com.example.appwebhooktelegram.feign.UniversityFeign;
import com.example.appwebhooktelegram.payload.ResultMessage;
import com.example.appwebhooktelegram.payload.SendPhoto;
import com.example.appwebhooktelegram.repository.UserRepository;
import com.example.appwebhooktelegram.utils.BotState;
import com.example.appwebhooktelegram.utils.Buttons;
import com.example.appwebhooktelegram.utils.RestConstants;
import com.example.appwebhooktelegram.utils.StateButtonMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BotStatusServiceImpl implements BotStatusService {
    public final UserRepository userRepository;
    public final BotAuthService botAuthService;
    public final TelegramButtonService telegramButtonService;
    public final TelegramFeign telegramFeign;
    public final UniversityFeign universityFeign;

    public void statusManagerService(User user, SendMessage sendMessage, Update update) {
        switch (user.getState()) {
            case BotState.WAITING_PHONE_NUMBER -> {
                botAuthService.gettingNumber(user, update, sendMessage);
            }
            case BotState.REGISTER_PENDING -> {
                if (botAuthService.checkUserIfRegistered(user.getPhoneNumber()))
                    botAuthService.cabinet(sendMessage, user);
                else if (update.getMessage().getText().equals(Buttons.SET_PASSWORD)) {
                    user.setState(BotState.WAITING_FOR_PASSWORD);
                    sendMessage.setReplyMarkup(null);
                    sendMessage.setText(RestConstants.MESSAGE_REQUIREMENTS);
                } else {
                    sendMessage.setReplyMarkup(telegramButtonService.replyKeyboardMaker(StateButtonMap.stateButtonMap.get(BotState.REGISTER_PENDING)));//o'chirilsin
                    sendMessage.setText("Botdan foydalanish uchun avval ro'yxatdan o'tishingiz kerak");//kerak emas
                    user.setState(BotState.SET_PASSWORD);
                }
            }
            case BotState.CABINET -> {
                cabinetMessageService(update.getMessage().getText(), sendMessage, user);
            }
            case BotState.SET_PASSWORD -> {
                botAuthService.setPasswordState(sendMessage, update, user);
            }
            case BotState.WAITING_FOR_PASSWORD -> {
                botAuthService.setPassword(update, sendMessage, user);
            }
            case BotState.WAITING_FOR_SMS_CODE -> {
                if (Objects.equals(Buttons.BACK, update.getMessage().getText())) {
                    user.setState(BotState.REGISTER_PENDING);
                    return;
                }
                botAuthService.checkSmsCode(update, sendMessage, user);
            }
            case BotState.CHOOSE_EXAMS -> {
                try {
                    sendMessage.setText("To'lov summasi 100.000 UZS");
                    sendMessage.setReplyMarkup(telegramButtonService.replyKeyboardMaker(List.of("Click")));
                    user.setState(BotState.CLICK);
                } catch (Exception e) {
                    user.setState(BotState.CABINET);
                }
            }
            case BotState.CLICK -> {
                try {
                    if (user.getState().equals(BotState.CLICK)) {
                        makeInvoice(user);
                    }
                } catch (Exception e) {
                    user.setState(BotState.CABINET);
                }
            }
            case BotState.VIEW_HISTORY -> {

                // natijani koradi
            }
            default -> {
                System.out.println(update);
                System.out.println("Bu case uchun hali yozilmagan");
            }
        }
    }

    public void cabinetMessageService(String text, SendMessage sendMessage, User user) {
        switch (text) {
            case Buttons.PROFILE -> {
                sendMessage.setReplyMarkup(telegramButtonService.replyKeyboardMaker(StateButtonMap.stateButtonMap.get(Buttons.PROFILE)));
                sendMessage.setText("Profile info");
            }
            case Buttons.ABOUT_US -> {
                botAuthService.cabinet(sendMessage, user);
                sendMessage.setText(RestConstants.CONTACTS);
            }
            case Buttons.BACK -> {
                botAuthService.cabinet(sendMessage, user);
            }
            case Buttons.PERSONAL_ID -> {
                try {
                    String s = RestConstants.UNIVERSITY_BASE_PATH + RestConstants.BAR_CODE_ATTACHMENT_PATH + user.getPhoneNumber();
                    SendPhoto sendPhoto = new SendPhoto(user.getChatId(), "Personal ID", s);
                    telegramFeign.sendPhoto(RestConstants.BOT_TOKEN, sendPhoto);
                    user.setState(BotState.CABINET);
                } catch (Exception e) {
                    sendMessage.setText("register exam tugmasini bosing");
                }
            }
            case Buttons.REGISTER_EXAM -> {
//                ApiResult<HashMap<String, List<ExamDTO>>> apiResult = universityFeign.getExams();
//                HashMap<String, List<ExamDTO>> hashMap = apiResult.getData();
//                List<ExamDTO> examDTOList = hashMap.get("Math");
//                List<String> dtoName = new ArrayList<>();
//                for (ExamDTO dto : examDTOList) {
//                    dtoName.add(dto.getName());
//                }
                List<String> dtoName = new ArrayList<>(Arrays.asList("abs", "dfg"));
                Map<String, List<String>> stateButtonMap = new HashMap<>() {
                    {
                        put(Buttons.EXAMS, dtoName);
                    }
                };
                user.setState(BotState.CHOOSE_EXAMS);
                sendMessage.setText(stateButtonMap.toString());
                sendMessage.setReplyMarkup(telegramButtonService.replyKeyboardMaker(dtoName));
            }
            case Buttons.EXAM_HISTORY -> {
                try {
                    if (user.getPhoneNumber().startsWith("+")){
                        user.setPhoneNumber(user.getPhoneNumber().substring(1));
                        userRepository.save(user);
                    }
                    sendMessage.setText(universityFeign.searchById(
                            universityFeign.searchByPhoneNumber(user.getPhoneNumber()).getId()).getResults());
                    user.setState(BotState.CABINET);
                } catch (Exception e) {
                    sendMessage.setText("Registem exam yoliga oting");
                }
            }
            default -> {
                sendMessage.setText("Enter button");
                sendMessage.setReplyMarkup(telegramButtonService.replyKeyboardMaker(StateButtonMap.stateButtonMap.get(BotState.CABINET)));

            }
        }
    }

    public void makeInvoice(User user) {
        SendInvoice sendInvoice = new SendInvoice();
        List<LabeledPrice> prices = new ArrayList<>();
        prices.add(new LabeledPrice("Order price", 10000000));
        prices.add(new LabeledPrice("Delivery price", 0));
        sendInvoice.setChatId(user.getChatId());
        sendInvoice.setProviderToken("398062629:TEST:999999999_F91D8F69C042267444B74CC0B3C747757EB0E065");
        sendInvoice.setPrices(prices);
        sendInvoice.setCurrency("UZS");
        sendInvoice.setPayload("Payload");
        sendInvoice.setTitle("Pay with click");
        sendInvoice.setDescription("Click orqali tolov");


        ResultMessage resultMessage = new ResultMessage();
        telegramFeign.sendInvoice(RestConstants.BOT_TOKEN, sendInvoice);
        user.setState(BotState.CABINET);
    }
}
