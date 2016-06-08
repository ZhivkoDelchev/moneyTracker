package com.jako.moneytracker.config.security;

import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserDao userRepository;

    @Autowired
    public UserDetailsService(final UserDao userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                return new org.springframework.security.core.userdetails.User(
                        " ", " ", true, true, true, true,
                        getAuthorities("NONE"));
            }

            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), true, true, true, true,
                    getAuthorities("USER"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final String role) {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }
}
