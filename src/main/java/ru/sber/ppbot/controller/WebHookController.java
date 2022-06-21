package ru.sber.ppbot.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.sber.ppbot.service.PokerPlanningRusBot;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebHookController {

    private final PokerPlanningRusBot pokerPlanningRusBot;

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return pokerPlanningRusBot.onWebhookUpdateReceived(update);
    }
}
