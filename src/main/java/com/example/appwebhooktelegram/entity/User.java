package com.example.appwebhooktelegram.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private String chatId;
    private String username;
    private String phoneNumber;
    private String smsCodeId;
    private String state;
    private String password;
    private String messageId;
    private SuccessfulPayment successfulPayment;
}
