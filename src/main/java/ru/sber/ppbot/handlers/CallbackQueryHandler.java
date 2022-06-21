package ru.sber.ppbot.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.sber.ppbot.model.Complexity;
import ru.sber.ppbot.model.Task;
import ru.sber.ppbot.service.TaskService;
import ru.sber.ppbot.service.UserService;
import ru.sber.ppbot.util.SendMessageFactory;

import java.util.Map;

/**
 * <p>Class for handling button callbacks.</p>
 *
 *  @author Dmitry Koryanov
 */
@Slf4j
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CallbackQueryHandler {

    TaskService taskService;

    UserService userService;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {

        //split TaskId and difficulty arrived
        String data[] = callbackQuery.getData().split("@");

        log.info("Сохраняем оценку {} для задачи с id={}",data[1], data[0]);

        Task task = taskService.findById(Long.parseLong(data[0]))
                .orElseThrow(() -> new RuntimeException("Задача с id="+data[0]+"не найдена!"));

        Map<String, Complexity> complexities = task.getComplexities();

        log.info("Текущий список оценок {}", complexities);
        complexities.put(callbackQuery.getMessage().getChat().getUserName(),
                Complexity.valueOf(data[1]));
        log.info("Новый список оценок {}", complexities);

        //save new estimate
        task.setComplexities(complexities);
        taskService.addTask(task);

        log.info("Оценка {} для задачи с id={} сохранена",data[1], data[0]);

        log.info("Формируем ответное сообщение пользователю {}",callbackQuery.getMessage().getChat().getUserName());

        SendMessage sendMessage = SendMessageFactory.getSendMessageInstance(
                callbackQuery.getMessage().getChatId().toString(),
                "Уважаемый *@" + callbackQuery.getMessage().getChat().getUserName()
                        + "*! Ваша оценка сложности *" + Complexity.valueOf(data[1]) + "* по задаче *"
                        + task.getName()+"* была учтена! Спасибо за участие в голосовании!");

        log.info("Сформировано ответное сообщение {}",sendMessage);

        return sendMessage;
    }
}
