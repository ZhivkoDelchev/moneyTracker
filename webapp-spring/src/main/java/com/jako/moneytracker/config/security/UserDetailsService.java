package com.jako.moneytracker.config.security;

import com.jako.moneytracker.db.dao.UserDao;
import com.jako.moneytracker.db.dao.UserRoleDao;
import com.jako.moneytracker.db.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserDao userRepository;
    private final UserRoleDao userRoleDao;

    @Autowired
    public UserDetailsService(final UserDao userRepository, final UserRoleDao userRoleDao) {
        this.userRepository = userRepository;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            UserEntity user = userRepository.findByEmail(email);
            if (user == null) {
                return new org.springframework.security.core.userdetails.User(
                        " ", " ", true, true, true, true,
                        getAuthorities(Collections.singletonList("NONE")));
            }

            final List<String> roles = userRoleDao.findRoleByUser(user);
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(), user.getPassword(), true, true, true, true,
                    getAuthorities(roles));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final List<String> roles) {
        final List<GrantedAuthority> result = new ArrayList<>();
        for (String role : roles) {
            result.add(new SimpleGrantedAuthority(role));
        }
        return result;
    }
}
