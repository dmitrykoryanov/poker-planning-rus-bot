package ru.sber.ppbot.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.ppbot.model.Task;
import ru.sber.ppbot.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

/**
 * <p>Service for working with tasks</p>
 *
 *  @author Dmitry Koryanov
 */
@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TaskServiceImpl implements TaskService{

    TaskRepository taskRepository;

    @Override
    public void addTask(Task task) {

        //in case it is not update, we need to set Id manually
        if (task.getId() == null) {
            Long currentMax = ((List<Task>) taskRepository.findAll())
                    .stream()
                    .map(Task::getId)
                    .max((L1, L2) -> (int) (L1 - L2))
                    .orElseGet(() -> 0L);
            log.info("Текущий максимальный идентификатор репозитория задач = {}", currentMax);
            task.setId(currentMax + 1L);
        }
        taskRepository.save(task);
    }

    @Override
    public List<Task> findAllTasks() {
        return (List<Task>) taskRepository.findAll();
    }

    @Override
    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }
}
