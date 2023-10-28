package com.example.maritimeapp.service.impl;

import com.example.maritimeapp.model.entity.RoleEntity;
import com.example.maritimeapp.model.entity.UserEntity;
import com.example.maritimeapp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MaritimeAppUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public MaritimeAppUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
            .map(MaritimeAppUserDetailService::map)
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }

    private static UserDetails map(UserEntity userEntity) {
        return User.withUsername(userEntity.getEmail())
            .password(userEntity.getPassword())
            .authorities(userEntity.getRoles()
                             .stream()
                             .map(MaritimeAppUserDetailService::map)
                             .toList())
            .build();
    }

    private static GrantedAuthority map(RoleEntity role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getRole()
            .name());
    }
}
