/*
 * 44.Rest_API_Spring_Boot. 16.12.2020, 14:48 / 16.12.2020, 14:48   @A.Alexandr
 * Copyright (c)
 */
package ru.coach.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.coach.service.models.Authority;
import ru.coach.service.models.User;
import ru.coach.service.rerpositories.UserRepository;

import java.util.Arrays;

@Component
public class TestDataUtil {

    @Autowired
    @Qualifier("bcPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public void initializeData() {

        User user1 = User.builder()
                .firstName("User1")
                .lastName("U.")
                .email("user1@gmail.com")
                .hashPassword(passwordEncoder.encode("qwerty001"))
                .tokens(Arrays.asList("token1", "token11"))
                .authority(Authority.USER)
                .build();

        User user2 = User.builder()
                .firstName("User2")
                .lastName("U.")
                .email("user2@gmail.com")
                .hashPassword(passwordEncoder.encode("qwerty002"))
                .tokens(Arrays.asList("token2", "token22"))
                .authority(Authority.USER)
                .build();

        User user3 = User.builder()
                .firstName("User3")
                .lastName("U.")
                .email("user3@gmail.com")
                .hashPassword(passwordEncoder.encode("qwerty003"))
                .tokens(Arrays.asList("token3", "token33"))
                .authority(Authority.USER)
                .build();

        User admin = User.builder()
                .firstName("Admin")
                .lastName("A.")
                .email("admin@gmail.com")
                .hashPassword(passwordEncoder.encode("qwerty"))
                .tokens(Arrays.asList("token0", "token"))
                .authority(Authority.ADMIN)
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(admin);
    }
}
