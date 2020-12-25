package ru.coach.service.controllers.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.coach.service.dto.UserDto;
import ru.coach.service.models.User;
import ru.coach.service.rerpositories.UserRepository;
import java.util.List;

@RestController
public class RestUsersController {

    @Autowired
    private UserRepository userRepository;

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/rest/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userRepository.findAll();// A! not getAll *JPA persistence
        return ResponseEntity.ok(UserDto.from(users));
    }
}
