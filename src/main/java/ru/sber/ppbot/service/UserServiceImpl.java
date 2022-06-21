package ru.sber.ppbot.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.ppbot.model.User;
import ru.sber.ppbot.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return (List<User>)userRepository.findAll();
    }
}
