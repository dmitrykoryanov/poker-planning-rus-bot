package ru.sber.ppbot.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import ru.sber.ppbot.handlers.CallbackQueryHandler;
import ru.sber.ppbot.handlers.MessageHandler;
import ru.sber.ppbot.service.PokerPlanningRusBot;

/**
 * <p>Telegram bot configuration</p>
 *
 *  @author Dmitry Koryanov
 */
@Configuration
@AllArgsConstructor
public class WebHookConfig {
    private final TelegramConfig telegramConfig;

    @Bean
    public SetWebhook setWebhookInstance() {

        return SetWebhook
                .builder()
                .url(telegramConfig.getWebhookPath())
                .build();
    }

    @Bean
    public PokerPlanningRusBot springWebhookBot(SetWebhook setWebhook,
                                                MessageHandler messageHandler,
                                                CallbackQueryHandler callbackQueryHandler) {

        PokerPlanningRusBot bot = new PokerPlanningRusBot(setWebhook, messageHandler, callbackQueryHandler);

        bot.setBotPath(telegramConfig.getWebhookPath());
        bot.setBotUsername(telegramConfig.getBotName());
        bot.setBotToken(telegramConfig.getBotToken());

        return bot;
    }
}
