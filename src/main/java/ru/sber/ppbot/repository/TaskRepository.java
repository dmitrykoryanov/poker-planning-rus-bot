package ru.sber.ppbot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sber.ppbot.model.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
