package com.example.appwebhooktelegram.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SendUser implements Serializable {
    private String registered;
    private String application;
    private String applicationStatus;
    private String examDTO;
    private String results;
    private String contractDTO;
    private String hasNotification;
    private String callHistories;
    private String callStatus;
    private String edit;
    private String confirm;
    private String language;
    private String changeMathExam;
    private String examInfo;
    private String rotates;
    private String applicantExam;
    private String grant;
}
