package com.graduation.onlineclasses.bookingonlineclasses.security;

import com.graduation.onlineclasses.bookingonlineclasses.repository.BaseUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final BaseUserRepository baseUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = baseUserRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return UserPrincipal.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .authorities(List.of(new SimpleGrantedAuthority(user.getUserRole().toString())))
                .password(user.getPassword())
                .build();
    }
}
