package ru.sber.ppbot.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.ppbot.model.Admin;
import ru.sber.ppbot.repository.AdminRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AdminServiceImpl implements AdminService{

    private AdminRepository adminRepository;

    @Override
    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAdminsList() {
        return (List<Admin>) adminRepository.findAll();
    }

    @Override
    public Admin findByName(String adminUsername) {
        return adminRepository.findByName(adminUsername);
    }

    @Override
    public Optional<Admin> findById(String id) {
        return adminRepository.findById(id);
    }

    @Override
    public void deleteById(String id) {
        adminRepository.deleteById(id);
    }

    @Override
    public void delete(Admin admin) {
        adminRepository.delete(admin);
    }
}
