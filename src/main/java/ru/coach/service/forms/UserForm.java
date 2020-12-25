package ru.coach.service.forms;

import lombok.Data;

@Data// ---------------------------->> get-теры, set-теры....
public class UserForm {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
