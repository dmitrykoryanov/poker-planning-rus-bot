package ru.sber.ppbot.service;

import ru.sber.ppbot.model.Task;

import java.util.List;

public interface TaskService {

    void addTask(Task task);

    List<Task> findAllTasks();
}
