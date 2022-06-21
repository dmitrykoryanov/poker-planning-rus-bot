package ru.sber.ppbot.service;

import ru.sber.ppbot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void addUser(User user);

    Optional<User> findById(String id);

    void delete(User user);
    List<User> findAll();
}
