package ru.sber.ppbot.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import reactor.core.publisher.Mono;
import ru.sber.ppbot.model.Complexity;
import ru.sber.ppbot.model.Task;
import ru.sber.ppbot.model.User;
import ru.sber.ppbot.util.KeyboardsFactory;
import ru.sber.ppbot.util.MessageUtil;
import ru.sber.ppbot.util.SendMessageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>Service for sending messages to users in order they could estimate the complexity</p>
 *
 *  @author Dmitry Koryanov
 */
@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DistributionServiceImpl implements DistributionService {

    private UserService userService;

    private TaskService taskService;

    private WebClient webClient;

    class Helper {
        static String result;
    }

    @Override
    public String distribute(Message message) {

        taskService
                .findById(Long.parseLong(MessageUtil.getIdFromMessage(message)))
                .ifPresentOrElse(task -> {
                            //initializing complexities list before next vote for this task
                            task.setComplexities(null);
                            taskService.addTask(task);

                            //find all users with not null chatId and sending this task for voting
                            userService
                                    .findAll()
                                    .stream()
                                    .filter(u -> u.getChatId() != null && !u.getChatId().equals(""))
                                    .forEach(u -> sendUserMessageForVote(u, task));

                            Helper.result = "Задача *" + task.getName()
                                    + "* была успешно отправлена на голосование.\n";
                        },
                        //if task not found, setting the corresponding message
                        () -> Helper.result = "Не найдена задача с id=" + MessageUtil.getIdFromMessage(message));

        return Helper.result;
    }

    public void sendUserMessageForVote(User u, Task t) {

        SendMessage sendMessage = SendMessageFactory.getSendMessageInstance(u.getChatId(),
                "Уважаемый *@" + u.getUsername() + "*! Проголосуйте за задачу " + t.getName());

        sendMessage.setReplyMarkup(KeyboardsFactory.getVoteInlineKeyboardMarkup(t));

        log.info("Отправляем задачу с id={} пользователю {} на голосование", t.getId(), u.getUsername());
        webClient
                .post()
                .uri("sendMessage")
                .contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE))
                .body(Mono.just(sendMessage), SendMessage.class)
                .retrieve().bodyToMono(String.class)
                .subscribe(r -> log.info("Ответ телеграмма: {}", r));
        log.info("Закончили отправку задачи с id={} пользователю {} на голосование", t.getId(), u.getUsername());
    }
}
