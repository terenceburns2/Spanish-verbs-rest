package com.terenceapps.spanishverbs.repository;

import com.terenceapps.spanishverbs.model.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRespository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM accounts WHERE email=?";
        List<User> user = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(User.class), email);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(user.get(0));
    }

    @Override
    public void registerUser(String email, String password) {
        String sql = "INSERT INTO accounts (email, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, email, password);
    }
}
