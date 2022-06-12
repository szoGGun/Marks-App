package org.przemekrutkowski.exammarksapp.config;

import lombok.RequiredArgsConstructor;
import org.przemekrutkowski.exammarksapp.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


import static org.przemekrutkowski.exammarksapp.enums.SecurityRole.*;
@RequiredArgsConstructor
public class MyUserPrincipal implements UserDetails {

    private final User user;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return switch (user.getUserRole()) {
            case "ADMIN" -> List.of(new SimpleGrantedAuthority(ROLE_ADMIN.getFullName()));
            case "USER" -> List.of(new SimpleGrantedAuthority(ROLE_USER.getFullName()));
            default -> List.of(new SimpleGrantedAuthority(ROLE_GUEST.getFullName()));
        };
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
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
