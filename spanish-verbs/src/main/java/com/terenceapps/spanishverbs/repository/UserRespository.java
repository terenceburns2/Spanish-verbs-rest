package com.terenceapps.spanishverbs.repository;

import com.terenceapps.spanishverbs.model.User;

import java.util.Optional;

public interface UserRespository {
    Optional<User> findByEmail(String email);
    void registerUser(String email, String password);
}
