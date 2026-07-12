package com.authservice.demo.security;

import com.authservice.demo.model.UserAccount;
import com.authservice.demo.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // ✅ try username first, then email
        UserAccount user = userAccountRepository.findByUsername(usernameOrEmail)
                .orElseGet(() -> userAccountRepository.findByEmail(usernameOrEmail)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + usernameOrEmail)));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPasswordHash())
                .roles(user.getRole()) // expects USER, ADMIN etc.
                .build();
    }
}
