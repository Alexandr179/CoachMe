package ru.coach.service.security.details;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.coach.service.models.User;
import java.util.Collection;
import java.util.Collections;

/**
 * изначально Spring Security содержит аутентификацию для одного user-ра
 *  сущность UserDetailsImpl ..описывает наш шаблон аутент.user-ра. UserDetailsImpl подтягивает в аутентификацию системы реального User
 */
public class UserDetailsImpl implements UserDetails {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private User user;// ..нет такого бина- user (он не компонент бизнес-логики). поэтому - через конструктор

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {// Granted (разрешение авторизации) по ..User.Authority
        logger.info("getAuthorities(): " + Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority().name())));

        return Collections.singletonList(new SimpleGrantedAuthority(user.getAuthority().name()));
    }

    @Override
    public String getPassword() {
        return user.getHashPassword();
    }

    @Override
    public String getUsername() {// реализация по  ..email
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
