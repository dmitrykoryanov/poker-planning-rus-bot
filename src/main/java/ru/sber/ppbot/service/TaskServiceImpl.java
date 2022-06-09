package ru.sber.ppbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.sber.ppbot.model.Task;
import ru.sber.ppbot.repository.TaskRepository;

import java.util.List;

public class TaskServiceImpl implements TaskService{

    @Autowired
    TaskRepository taskRepository;

    @Override
    public void addTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public List<Task> findAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }
}
