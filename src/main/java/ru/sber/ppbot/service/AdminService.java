package ru.sber.ppbot.service;

import ru.sber.ppbot.model.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    void addAdmin(Admin admin);

    List<Admin> getAdminsList();

    Admin findByName(String adminUsername);

    Optional<Admin> findById(String id);

    void deleteById(String id);

    void delete(Admin admin);
}
