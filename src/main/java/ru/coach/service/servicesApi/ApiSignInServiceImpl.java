package ru.coach.service.servicesApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.coach.service.models.User;
import ru.coach.service.rerpositories.UserRepository;

@Service
public class ApiSignInServiceImpl implements ApiSignInService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> {throw new UsernameNotFoundException("User not found");});
    }
}
