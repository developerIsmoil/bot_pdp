package com.example.appwebhooktelegram.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface StateButtonMap {

    Map<String, List<String>> stateButtonMap = new HashMap<>() {
        {
            put(BotState.CABINET, Arrays.asList(Buttons.ABOUT_US, Buttons.PROFILE));
            put(Buttons.PROFILE, Arrays.asList(Buttons.REGISTER_EXAM, Buttons.PERSONAL_ID, Buttons.EXAM_HISTORY, Buttons.BACK));
            put(BotState.REGISTER_PENDING, List.of(Buttons.SET_PASSWORD));
        }
    };
}
