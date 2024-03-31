package com.terenceapps.spanishverbs.repository;

import com.terenceapps.spanishverbs.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;

@JdbcTest
@Sql("/user-test-data.sql")
@Sql(scripts = {"/delete-user-test-data.sql"}, executionPhase = AFTER_TEST_CLASS)
class JdbcUserRepositoryTest {

    private final JdbcTemplate jdbcTemplate;
    private final UserRespository userRespository;

    @Autowired
    public JdbcUserRepositoryTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        userRespository = new JdbcUserRepository(jdbcTemplate);
    }

    @Test
    public void findByEmail_shouldReturnUserObject() {
        Optional<User> userOptional = userRespository.findByEmail("test@account.com");
        assertTrue(userOptional.isPresent());
    }

    @Test
    public void findByEmail_shouldReturnEmptyOptional() {
        Optional<User> userOptional = userRespository.findByEmail("nothing@account.com");
        assertFalse(userOptional.isPresent());
    }

    @Test
    public void registerUser_shouldAddUserToDatabase() {
        String accountEmail = "second@account.com";
        userRespository.registerUser(accountEmail, "password123");
        Optional<User> userOptional = userRespository.findByEmail(accountEmail);
        assertTrue(userOptional.isPresent());
    }
}