package ru.sber.ppbot.util;

import org.telegram.telegrambots.meta.api.objects.Message;

public class MessageUtil {

    public static String getIdFromMessage(Message message){
        return message.getText().substring(1);
    }
}
