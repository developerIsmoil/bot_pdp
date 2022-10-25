package com.example.appwebhooktelegram.utils;

public interface RestConstants {
    String TELEGRAM_BASE_URL_WITHOUT_BOT = "https://api.telegram.org/";
    String TELEGRAM_BASE_URL = "https://api.telegram.org/bot";
    String BOT_TOKEN = "5706802021:AAHwjnbFMti2Fl7A1pWYP5DpO0wcBu-qlVw";
    String UNIVERSITY_BASE_PATH = "http://api.pdp.uz/api/";
    String UNIVERSITY_EXAMS = "/university-application/v1/application/exams";
    String UNIVERSITY_EXAMS_BOT = "/university-application/v1/application/exams-for-bot";
    String UNIVERSITY_EXAMS_BOTS_API = "university-application/v1/application/exams-for-bot";
    String UNIVERSITY_EXAMS_BOTS = "http://185.74.5.104:8901/api/university-application/v1/application/exams-for-bot";
    String CHECK_REGISTERED = "auth/v1/auth/check-phone";
    String GET_INFO_USER="university-application/v1/application/left-info";
    String BAR_CODE_ATTACHMENT_PATH = "university-application/v1/application/download-student-id-telegram/";
    String CONTACTS = "bizni instagram(https://www.instagram.com/pdp.university/) sahifasida topishingiz mumkin.\n" +
            "Telegram orqali @PDPSupportBot dan savollaringizga javob olishingiz mumkin.";
    String SET_PASSWORD_SEND_SMS_URL = "/auth/v1/auth/university-send-sms-for-register";
    String CHECK_PASSWORD_URL = "/auth/v1/auth/university-sign-up";
    String MESSAGE_REQUIREMENTS = "parol kamida 6 ta harfdan iborat va probellarsiz bo'lishi kerak";
    String SEARCH_BY_PHONE_NUMBER_PATH = "university-application/v1/application/search-by-phone-number";
    String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=])(?=\\S+$).{8,}$";
    String MY_ID = "6ed62fbd-b6bc-4d20-a12b-aa62103fc829";
    String RESULT_EXAM = "https://university.pdp.uz/uz/dashboard/exam";
}
