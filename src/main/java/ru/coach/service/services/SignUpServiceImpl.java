package ru.coach.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.coach.service.rerpositories.UserRepository;
import ru.coach.service.forms.UserForm;
import ru.coach.service.models.Authority;
import ru.coach.service.models.User;

@Service
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void signUp(UserForm userForm) {
        User user = User.builder()
                .email(userForm.getEmail())
                .firstName(userForm.getFirstName())
                .lastName(userForm.getLastName())
                .hashPassword(passwordEncoder.encode(userForm.getPassword()))
                .authority(Authority.USER)
                .build();

        userRepository.save(user);
    }
}
