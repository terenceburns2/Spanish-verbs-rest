package com.terenceapps.spanishverbs.service;

import com.terenceapps.spanishverbs.exception.EmailExistsException;
import com.terenceapps.spanishverbs.model.User;
import com.terenceapps.spanishverbs.repository.JdbcUserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final JdbcUserRepository userRepository;

    public UserService(JdbcUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public BigDecimal getUserIdFromEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        // We never reach this stage as failed authentication should be
        // caught further up, but as added safety, we'll keep this in.
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User email cannot be found.");
        }

        return ((User) user.get()).getId();
    }

    public void registerUser(User user) {
        if ((userRepository.findByEmail(user.getEmail())).isPresent()) {
            throw new EmailExistsException("This email already exists. Try signing in.");
        }
        userRepository.registerUser(user.getEmail(), passwordEncoder.encode(user.getPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User email cannot be found.");
        }

        return user.get();
    }
}
