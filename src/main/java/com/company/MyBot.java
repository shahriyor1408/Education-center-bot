package com.company;

import com.company.container.ComponentContainer;
import com.company.controller.AdminController;
import com.company.controller.AppController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends TelegramLongPollingBot {
    @Override
    public String getBotToken() {
        return "5385578860:AAEjYG_uHj7uEkaKcsIh9KV4LQXE8sPw0uQ";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage()){
            Message message = update.getMessage();
            User user = message.getFrom();
            String userId = String.valueOf(user.getId());

            if(ComponentContainer.ADMIN.equals(userId)){
                AdminController.handleMessage(user,message);
            }else {
                AppController.handleMessage(userId,message);
            }
        }
        else if(update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Message message = callbackQuery.getMessage();
            User user = callbackQuery.getFrom();
            String data = callbackQuery.getData();
            System.out.println("data = " + data);
            log(user, data);

            if(ComponentContainer.ADMIN.equals(String.valueOf(user.getId()))){
                AdminController.handleCallback(user, message, data);
            }else{
                AppController.handleCallBack(user,message, data);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "http://t.me/oquv_markazi_Sh_bot";
    }

    public void log(User user, String text) {
        String info = String.format("ID: %s,  first name: %s,  last name: %s, text: %s",
                user.getId(), user.getFirstName(), user.getLastName(), text);
        System.out.println(info);
    }

    public void sendMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(DeleteMessage deleteMessage) {
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(SendDocument sendDocument) {
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
