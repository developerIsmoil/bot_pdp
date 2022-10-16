package com.example.appwebhooktelegram.repository;

import com.example.appwebhooktelegram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByChatId(String id);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
