package ru.sber.ppbot.util;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendMessageFactory {

    boolean ENABLE_MARKDOWN = true;
    String PARSE_MODE = "Markdown";

    static SendMessage getSendMessageInstance(String chatId, String messageText) {

        SendMessage sendMessage = new SendMessage(chatId, messageText);

        sendMessage.enableMarkdown(ENABLE_MARKDOWN);
        sendMessage.setParseMode(PARSE_MODE);

        return sendMessage;
    }
}
