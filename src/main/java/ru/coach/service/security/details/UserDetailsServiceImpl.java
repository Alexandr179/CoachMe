package ru.coach.service.security.details;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.coach.service.rerpositories.UserRepository;
import ru.coach.service.models.User;

/**
 * для получения AuthUser из DB (сервис нужен для дополнения реализации UserDetails ..)
 */

@Service(value = "customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {throw new UsernameNotFoundException("User not found");});

        logger.info("loadUserByUsername(): " + user.getEmail());
        return new UserDetailsImpl(user);
    }
}
