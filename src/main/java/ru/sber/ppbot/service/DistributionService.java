package ru.sber.ppbot.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface DistributionService {

    String distribute(Message message);
}
