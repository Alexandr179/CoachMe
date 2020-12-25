package ru.coach.service.rerpositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coach.service.models.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

//    @Query("select user FROM User user JOIN user.tokens token WHERE token = ?1")
//    Optional<User> findByToken(String token);
}