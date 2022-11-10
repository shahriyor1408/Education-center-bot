package com.company.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyboardUtil {

    public static KeyboardButton getKeyBoardButton(String demo) {
        return new KeyboardButton(demo);
    }

    public static KeyboardRow getKeyboardRow(KeyboardButton... keyboardButtons) {
        return new KeyboardRow(Arrays.asList(keyboardButtons));
    }

    public static List<KeyboardRow> getKeyboardRowList(KeyboardRow... keyboardRows){
        return new ArrayList<>(Arrays.asList(keyboardRows));
    }

    public static ReplyKeyboardMarkup getKeyboardMarkup(List<KeyboardRow> keyboardRows) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setKeyboard(keyboardRows);

        return replyKeyboardMarkup;
    }

    public static KeyboardButton getContactButton(String demo) {
        KeyboardButton contactButton = new KeyboardButton(demo);
        contactButton.setRequestContact(true);
        return contactButton;
    }
}
