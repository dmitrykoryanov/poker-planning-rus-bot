package ru.sber.ppbot.service;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import ru.sber.ppbot.handlers.CallbackQueryHandler;
import ru.sber.ppbot.handlers.MessageHandler;

import static ru.sber.ppbot.model.MainMenu.*;

/**
 * <p>Telegramm bot (using webhook)</p>
 *
 * @author Dmitry Koryanov
 */
@Getter
@Setter
@Slf4j
public class PokerPlanningRusBot extends SpringWebhookBot {

    private String botPath;
    private String botUsername;
    private String botToken;

    MessageHandler messageHandler;
    CallbackQueryHandler callbackQueryHandler;

    public PokerPlanningRusBot(SetWebhook setWebhook,
                               MessageHandler messageHandler,
                               CallbackQueryHandler callbackQueryHandler) {
        super(setWebhook);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }

    //the only method which needs to be overridden
    //messages are being handled using class MessageHandler and callbacks using CallbackQueryHandler
    //no particular reason for this - that's just more convenient
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {

        try {
            if (update.hasCallbackQuery()) {
                return callbackQueryHandler.processCallbackQuery(update.getCallbackQuery());
            } else {
                return messageHandler.answerMessage(update.getMessage());
            }
        } catch (IllegalArgumentException e) {
            setHelpMessage("*Неправильная команда!*\n");
            return START.getSendMessage(update.getMessage());
        } catch (Exception e) {
            setHelpMessage("*Возникло исключение!*\n");
            return START.getSendMessage(update.getMessage());
        }
    }
}
