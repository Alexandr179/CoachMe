package ru.coach.service.controllers.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.coach.service.dto.UserDto;
import ru.coach.service.models.User;
import ru.coach.service.rerpositories.UserRepository;
import ru.coach.service.security.authentication.TokenAndPassAuthentication;

import java.util.List;

/**
 * в RESR др.подход: нет браузера, который создает сесию (JSESSIONID).
 * подтверждение аутентификации User-ра возможно в запросе через token, передаваемый с header-ом
  **/

@RestController
public class RestUsersController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/rest/users")
    public ResponseEntity<List<UserDto>> getUsers(TokenAndPassAuthentication authentication) {// ищем ошибку
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(UserDto.from(users));
        }
    }
}
