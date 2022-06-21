package ru.sber.ppbot.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.sber.ppbot.model.User;

public interface UserRepository extends KeyValueRepository<User, String> {
}
