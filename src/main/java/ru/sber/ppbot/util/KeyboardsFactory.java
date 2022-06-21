package ru.sber.ppbot.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.sber.ppbot.model.Complexity;
import ru.sber.ppbot.model.MainMenu;
import ru.sber.ppbot.model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static ru.sber.ppbot.model.MainMenu.*;

public interface KeyboardsFactory {

    class Helper {
        static int counter = 0;
        static KeyboardRow[] row = new KeyboardRow[3];
    }

    static ReplyKeyboardMarkup getMainKeyBoard() {

        Arrays.stream(values())
                .sorted(Comparator.comparingInt(MainMenu::getOptionNumber))
                .forEach(menuOption -> {
                    if (Helper.counter % 3 == 0) {
                        Helper.row[Helper.counter / 3] = new KeyboardRow();
                    }
                    Helper.row[Helper.counter++ / 3]
                            .add(new KeyboardButton(menuOption.getText()));
                });

        Helper.counter = 0;
        List<KeyboardRow> keyboard = new ArrayList<>();
        Arrays.stream(Helper.row).forEachOrdered(keyboard::add);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setKeyboard(keyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        return replyKeyboardMarkup;
    }

    static InlineKeyboardMarkup getVoteInlineKeyboardMarkup(Task t) {

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        Arrays.stream(Complexity.values()).forEachOrdered(
                complexity -> {
                    List<InlineKeyboardButton> row = new ArrayList<>();
                    row.add(InlineKeyboardButton
                            .builder()
                            .text(complexity.getText())
                            .callbackData(t.getId() + "@" + complexity.name())
                            .build());
                    keyboard.add(row);
                }
        );

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;

    }
}
