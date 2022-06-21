package ru.sber.ppbot.handlers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import ru.sber.ppbot.model.User;
import ru.sber.ppbot.service.AdminService;
import ru.sber.ppbot.service.UserService;
import ru.sber.ppbot.util.SendMessageFactory;

import java.util.Arrays;

import static ru.sber.ppbot.model.MainMenu.*;

/**
 * <p>Main class for handling user replies.</p>
 *
 *  @author Dmitry Koryanov
 */
@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MessageHandler {

    private AdminService adminService;

    private UserService userService;

    public BotApiMethod<?> answerMessage(Message message) {

        String chatId = message.getChatId().toString();

        String username = message.getChat().getUserName();

        //if user is in the subscription user list, but not admin, we save chatId of this user in order we could
        // later send messages to the user
        if (!adminService.findById(username).isPresent()
                && userService.findById(username).isPresent()){

            User user = userService.findById(username).get();
            user.setChatId(chatId);
            userService.addUser(user);
            return getHelloUserMessage(chatId, username);

        //if user is neither an admin, nor in the subscription list, access is denied
        } else if (!adminService.findById(username).isPresent()
                && !userService.findById(username).isPresent()){
            return getAccessDeniedMessage(chatId, username);
        }

        return Arrays.stream(values())
                .peek(option -> option.processOption(message))
                .filter(option -> option.getText().equals(message.getText()))
                .peek(option -> setHelpMessage(""))
                .peek(option -> option.processAddOption(message))
                .map(choice -> {
                    SendMessage sendMessage = choice.getSendMessage(message);
                    log.info("Ответ в телеграмм: {}", sendMessage);
                    return sendMessage;
                })
                .findFirst()
                .orElseGet(() -> START.getSendMessage(message));
    }

    private SendMessage getAccessDeniedMessage(String chatId, String username) {
        return SendMessageFactory.getSendMessageInstance(chatId, "Здравствуйте *@" + username +
                "*! К сожалению, у вас нет прав на какие-либо действия в данном чате. :(");
    }

    private SendMessage getHelloUserMessage(String chatId, String username) {
        return SendMessageFactory.getSendMessageInstance(chatId, "Здравствуйте *@" + username +
                "*! Вы успешно подписаны на рассылку голосований! chatId="+chatId);
    }
}
