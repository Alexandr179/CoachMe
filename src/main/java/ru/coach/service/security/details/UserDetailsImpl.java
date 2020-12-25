package ru.coach.service.security.details;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.coach.service.models.User;
import java.util.Collection;
import java.util.Collections;

/**
 * описание сущности для авторизации. Spring -> знает как работать с UserDetails пользователя..
 *  Achtung!! Содержит 'между прочим' User..
 */

public class UserDetailsImpl implements UserDetails {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private User user;// ..нет такого бина- user (он не компонент бизнес-логики). поэтому - через констуктор <<

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
    public String getUsername() {
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
