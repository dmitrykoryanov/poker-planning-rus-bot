package ru.sber.ppbot.repository;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import ru.sber.ppbot.model.Task;

public interface TaskRepository extends KeyValueRepository<Task, Long> {
}
