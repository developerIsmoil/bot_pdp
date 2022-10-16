package com.example.appwebhooktelegram.utils;

public interface BotState {
    String WAITING_PHONE_NUMBER="WAITING_PHONE_NUMBER";
    String CABINET = "CABINET";
    String REGISTER_PENDING = "REGISTER_PENDING";
    String SET_PASSWORD = "SET_PASSWORD";
    String WAITING_FOR_PASSWORD = "WAITING_FOR_PASSWORD";
    String WAITING_FOR_SMS_CODE = "WAITING_FOR_SMS_CODE";
    String CHOOSE_EXAMS = "CHOOSE_EXAMS";
    String VIEW_HISTORY = "exam history";
    String CLICK = "click";
}
