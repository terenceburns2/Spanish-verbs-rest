package com.terenceapps.spanishverbs.service;

import com.terenceapps.spanishverbs.model.User;
import com.terenceapps.spanishverbs.repository.JdbcUserRepository;
import com.terenceapps.spanishverbs.repository.UserRespository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @MockBean
    private JdbcUserRepository userRespository;
    @Autowired
    private UserService userService;

    @Test
    public void getUserIdFromEmail_shouldReturnUserId() {
        String email = "test@account.com";
        BigDecimal userId = BigDecimal.valueOf(1);
        User user = new User(userId, email, "password123");

        when(userRespository.findByEmail(email)).thenReturn(Optional.of(user));

        assertEquals(userId, userService.getUserIdFromEmail(email));
    }

    @Test
    public void getUserIdFromEmail_shouldThrowNotFoundException() {
        String email = "test@account.com";
        when(userRespository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.getUserIdFromEmail(email));
    }

    @Test
    public void registerUser_shouldCallToRegisterUser() {
        String email = "test@account.com";
        BigDecimal userId = BigDecimal.valueOf(1);
        User user = new User(userId, email, "password123");

        when(userRespository.findByEmail(email)).thenReturn(Optional.empty());

        userService.registerUser(user);

        verify(userRespository).registerUser(eq(email), anyString());
    }

    @Test
    public void loadByUsername_shouldThrowNotFoundException() {
        String email = "test@account.com";
        when(userRespository.findByEmail(email)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
    }
}