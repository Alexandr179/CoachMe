package ru.coach.service.servicesApi;

import ru.coach.service.models.User;

public interface ApiSignInService {

    User loadUserByUsername(String email);
}
