package ru.sber.ppbot.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.sber.ppbot.model.Admin;

public interface AdminRepository extends KeyValueRepository<Admin, String> {

    Admin findByName(String adminUsername);
}
