package ru.sber.ppbot.service;

import ru.sber.ppbot.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    void addTask(Task task);

    List<Task> findAllTasks();

    Optional<Task> findById(Long id);

    void delete(Task task);

}
