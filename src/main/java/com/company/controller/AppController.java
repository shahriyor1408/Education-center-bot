package com.company.controller;

import com.company.container.ComponentContainer;
import com.company.database.Database;
import com.company.model.Course;
import com.company.model.Customer;
import com.company.model.Mentor;
import com.company.service.CourseService;
import com.company.service.CustomerService;
import com.company.util.InlineKeyboardUtil;
import com.company.util.KeyboardUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AppController {
    static Map<String, String> map = new TreeMap<>();

    public static void handleMessage(String userId, Message message) {
        if (message.hasText()) {
            String text = message.getText();
            handleText(text, userId, message);

        } else if (message.hasContact()) {
            Contact contact = message.getContact();
            handleContact(contact, message, userId);

        } else if (message.hasPhoto()) {
            List<PhotoSize> photoSizeList = message.getPhoto();
            handlePhoto(photoSizeList, message);
        }
    }

    private static void handlePhoto(List<PhotoSize> photoSizeList, Message message) {
        String fileId = photoSizeList.get(photoSizeList.size() - 1).getFileId();
        ComponentContainer.MY_TELEGRAM_BOT.log(message.getFrom(), fileId);
    }

    private static void handleContact(Contact contact, Message message, String userId) {
        CustomerService.addCustomer(contact, userId);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userId);

        SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()), new InputFile(ComponentContainer.START));

        sendPhoto.setCaption("Assalomu alaykum, " + message.getFrom().getFirstName() + " \nIlm - Ziyo o'quv markazimizga xush kelibsiz!" +
                "\nBu bot orqali siz markazimiz to'grisida barcha kerakli ma'lumotlarni olishingiz mumkin.");
        KeyboardButton button1 = KeyboardUtil.getKeyBoardButton("⚙️ MENU");
        KeyboardButton button2 = KeyboardUtil.getKeyBoardButton(" \uD83D\uDED2 Ro'yhatdan o'tgan kurslarim");
        KeyboardButton button3 = KeyboardUtil.getKeyBoardButton(" \uD83D\uDC6E\uD83C\uDFFC\u200D♂️ Admin bilan bog'lanish");

        KeyboardRow row1 = KeyboardUtil.getKeyboardRow(button1, button2);
        KeyboardRow row2 = KeyboardUtil.getKeyboardRow(button3);

        ReplyKeyboardMarkup keyboardMarkup = KeyboardUtil.getKeyboardMarkup(KeyboardUtil.getKeyboardRowList(row1, row2));
        sendMessage.setReplyMarkup(keyboardMarkup);

        sendMessage.setText("Bilimlar tomon olga!!!");
        ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);
        ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
    }

    private static void handleText(String text, String userId, Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(userId);
        User user = message.getFrom();

        ComponentContainer.MY_TELEGRAM_BOT.log(user, text);

        Customer customer = CustomerService.getCustomerByUserId(userId);

        if (text.equals("/start")) {
            if (customer == null) {
                sendMessage.setText(" Kontaktingizni yuboring: ");
                KeyboardButton contactButton = KeyboardUtil.getContactButton(" \uD83D\uDCDE Kontaktni yuborish");
                ReplyKeyboardMarkup markup = KeyboardUtil.getKeyboardMarkup(KeyboardUtil.getKeyboardRowList(KeyboardUtil.getKeyboardRow(contactButton)));
                sendMessage.setReplyMarkup(markup);

                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            } else {

                SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()), new InputFile(ComponentContainer.START));

                sendPhoto.setCaption("Assalomu alaykum, " + user.getFirstName() + " \nIlm - Ziyo o'quv markazimizga xush kelibsiz!" +
                        "\nBu bot orqali siz markazimiz to'grisida barcha kerakli ma'lumotlarni olishingiz mumkin.");
                KeyboardButton button1 = KeyboardUtil.getKeyBoardButton("⚙️ MENU");
                KeyboardButton button2 = KeyboardUtil.getKeyBoardButton("\uD83D\uDED2 Ro'yhatdan o'tgan kurslarim");
                KeyboardButton button3 = KeyboardUtil.getKeyBoardButton("\uD83D\uDC6E\uD83C\uDFFC\u200D♂️ Admin bilan bog'lanish");

                KeyboardRow row1 = KeyboardUtil.getKeyboardRow(button1, button2);
                KeyboardRow row2 = KeyboardUtil.getKeyboardRow(button3);

                ReplyKeyboardMarkup keyboardMarkup = KeyboardUtil.getKeyboardMarkup(KeyboardUtil.getKeyboardRowList(row1, row2));
                sendMessage.setReplyMarkup(keyboardMarkup);

                sendMessage.setText("bilimlar tomon olg'a!!!");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }

        } else if (text.equals("⚙️ MENU")) {
            sendMessage.setText("MENU: ");
            InlineKeyboardButton button1 = InlineKeyboardUtil.getButton("\uD83C\uDFE2 Markaz haqida ma'lumot", "menu/showInfo/" + userId);
            List<InlineKeyboardButton> row1 = InlineKeyboardUtil.getRow(button1);

            InlineKeyboardButton button2 = InlineKeyboardUtil.getButton("\uD83D\uDCD5 Kurslar bo'yicha ma'lumot", "menu/showCourses/" + userId);
            List<InlineKeyboardButton> row2 = InlineKeyboardUtil.getRow(button2);

            InlineKeyboardButton button3 = InlineKeyboardUtil.getButton("\uD83D\uDC68\u200D\uD83C\uDF93 Mentorlar haqida ma'lumot", "menu/showMentors/" + userId);
            List<InlineKeyboardButton> row3 = InlineKeyboardUtil.getRow(button3);

            InlineKeyboardButton button4 = InlineKeyboardUtil.getButton("✈️ Markazimiz yutuqlari", "menu/showAbout/" + userId);
            List<InlineKeyboardButton> row4 = InlineKeyboardUtil.getRow(button4);

            List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(row1, row2, row3, row4);
            InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(rowList);

            sendMessage.setReplyMarkup(markup);
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);

        } else if (text.equals("\uD83D\uDED2 Ro'yhatdan o'tgan kurslarim")) {
            if (Database.REGISTERED_COURSES.isEmpty()) {
                sendMessage.setText("Siz hali kurslarga ro'yhatdan o'tmagansiz!");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            } else {

                StringBuilder stringBuilder = new StringBuilder("");
                String s = "Ro'yhatdan o'tilgan kurslar: \n\n";

                for (String key : Database.REGISTERED_COURSES.keySet()) {
                    if (key.startsWith(userId)) {
                        Course course = Database.REGISTERED_COURSES.get(key);
                        stringBuilder.append("Nomi: ").append(course.getName()).append("\n");
                        stringBuilder.append("Narxi: ").append(course.getPrice()).append("\n");
                        stringBuilder.append("Davomiyligi: ").append(course.getValidityPeriod()).append("\n\n");
                    }
                }
                if (stringBuilder.toString().equals("")) {
                    sendMessage.setText("Siz hali kurslarga ro'yhatdan o'tmagansiz!");
                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
                } else {
                    sendMessage.setText(s + stringBuilder);
                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
                }
            }

        } else if (text.equals("\uD83D\uDC6E\uD83C\uDFFC\u200D♂️ Admin bilan bog'lanish")) {
            sendMessage.setText("Xabarni kiriting: ");

            map.put(String.valueOf(user.getId()), "sendMessageToAdmin/");

            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        } else if (map.get(userId).equals("sendMessageToAdmin/")) {
            sendMessage.setText("Xabar yuborildi javobni kuting!");
            map.remove(userId);

            SendMessage sendMessageToAdmin = new SendMessage();
            sendMessageToAdmin.setChatId(ComponentContainer.ADMIN);
            String str = user.getFirstName() + " dan xabar!\n\n";
            sendMessageToAdmin.setText("☎️ " + str + text);

            List<InlineKeyboardButton> row = InlineKeyboardUtil.getRow(InlineKeyboardUtil.getButton("\uD83D\uDCE4Javob yozish", "send_request_to_user/" + userId));
            sendMessageToAdmin.setReplyMarkup(InlineKeyboardUtil.getMarkup(InlineKeyboardUtil.getRowList(row)));
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessageToAdmin);

            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        } else {
            sendMessage.setText("Bunday amal mavjud emas!");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        }

    }

    public static void handleCallBack(User user, Message message, String data) {
        EditMessageText editMessageText = new EditMessageText(String.valueOf(message.getChatId()));
        editMessageText.setChatId(String.valueOf(user.getId()));
        editMessageText.setMessageId(message.getMessageId());

        if (data.equals("menu/showInfo/" + user.getId())) {
            DeleteMessage deleteMessage = new DeleteMessage(
                    String.valueOf(message.getChatId()), message.getMessageId()
            );
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()), new InputFile(ComponentContainer.INFO));
            sendPhoto.setCaption("""
                    O'quv markazimiz 2016-yil tashkil topgan bo'lib, shu yildan boshlab har yili 100 lab o'quvchilarni talabalik baxtiga erishishga muyassar qilmoqda.
                    Markazimizda hozirgi kunda ko'plab mentolar faoliyat olib borishadi va ular o'z fanlarining mutaxassislaridir. Markazimizda:\s

                    📌 Abituriyentlikka tayyorlash
                    📌 Til kurslari
                    📌 IT bo'yicha kurslar

                    mavjud va ishonamizki mentorlar tomonidan berilgan bilimlar sizga o'z soxangizda muvaffaqiyatga erishishingizga yordam bera oladi!""");

            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);
        } else if (data.equals("menu/showCourses/" + user.getId())) {
            if (Database.COURSE_LIST.isEmpty()) {
                editMessageText.setText("Markazda hali kurslar mavjud emas!");
                List<InlineKeyboardButton> row = InlineKeyboardUtil.getRow(InlineKeyboardUtil
                        .getButton("⬅️ Orqaga", "back_from_course_to_menu" + user.getId()));
                List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(row);
                InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(rowList);
                editMessageText.setReplyMarkup(markup);

                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            } else {
                editMessageText.setText("Sizga aynan qaysi yo'nalish bo'yicha kurslar kerak: ");
                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                for (Course course : Database.COURSE_LIST) {
                    List<InlineKeyboardButton> row = InlineKeyboardUtil.getRow(InlineKeyboardUtil
                            .getButton(course.getName(), "menu/showCourses/course/" + course.getId()));
                    rowList.add(row);
                }

                List<InlineKeyboardButton> row = InlineKeyboardUtil.getRow(InlineKeyboardUtil
                        .getButton("⬅️Orqaga", "back_to_menu" + user.getId()));
                rowList.add(row);

                editMessageText.setReplyMarkup(InlineKeyboardUtil.getMarkup(rowList));
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }

        } else if (data.equals("menu/showMentors/" + user.getId())) {
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(message.getChatId()), message.getMessageId());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));
            sendMessage.setText("Mentorlarimiz : ");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);

            if (Database.MENTOR_LIST.isEmpty()) {
                editMessageText.setText("Mentorlar mavjud emas!");
                List<InlineKeyboardButton> row = InlineKeyboardUtil.getRow(InlineKeyboardUtil
                        .getButton("⬅️Orqaga", "back_to_menu" + user.getId()));
                List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(row);
                InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(rowList);
                editMessageText.setReplyMarkup(markup);

                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            } else {
                if (Database.MENTOR_LIST.size() == 1) {
                    Mentor mentor = Database.MENTOR_LIST.get(0);
                    SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()), new InputFile(mentor.getImageUrl()));
                    sendPhoto.setCaption("Ismi familiyasi : " + mentor.getFirstName() + " " + mentor.getLastName()
                            + "\n" + "Fani : " + mentor.getSubject() + "\n" +
                            "Yoshi : " + mentor.getAge() + "\n"
                            + "Tel raqami : " + mentor.getPhoneNumber());

                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);
                } else {

                    Mentor mentor = Database.MENTOR_LIST.get(0);
                    SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()), new InputFile(mentor.getImageUrl()));
                    sendPhoto.setCaption("Ismi familiyasi : " + mentor.getFirstName() + " " + mentor.getLastName()
                            + "\n" + "Fani : " + mentor.getSubject() + "\n" +
                            "Yoshi : " + mentor.getAge() + "\n"
                            + "Tel raqami : " + mentor.getPhoneNumber());

                    Integer next = Database.MENTOR_LIST.get(1).getId();
                    InlineKeyboardButton button = InlineKeyboardUtil.getButton("Keyingisi ⏩", "show/mentors/next/" + next + "/" + user.getId());
                    List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(InlineKeyboardUtil.getRow(button));
                    InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(rowList);
                    sendPhoto.setReplyMarkup(markup);

                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);

                }
            }
        } else if (data.equals("menu/showAbout/" + user.getId())) {
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(message.getChatId()), message.getMessageId());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()), new InputFile(ComponentContainer.ABOUT));
            sendPhoto.setCaption("""
                    Bugungi kungacha o'quvchilarimiz yurtimiz va chet elning ko'plab nufuzli universitetlarida tahsil olishmoqda.\s
                    Bundan tashqari 2017-yil markazimiz boshqa o'quv markazlari orasida eng yaxshisi deb tan olindi.\s
                    Ingliz tili bo'yicha o'quvchilarimiz IELTS da yuqori ballarni qo'lga kiritishmoqda.""");

            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);
        } else if (data.equals("back_to_menu" + user.getId())) {
            editMessageText.setText("MENU: ");
            InlineKeyboardButton button1 = InlineKeyboardUtil.getButton("\uD83C\uDFE2 Markaz haqida ma'lumot", "menu/showInfo/" + user.getId());
            List<InlineKeyboardButton> row1 = InlineKeyboardUtil.getRow(button1);

            InlineKeyboardButton button2 = InlineKeyboardUtil.getButton("\uD83D\uDCD5 Kurslar bo'yicha ma'lumot", "menu/showCourses/" + user.getId());
            List<InlineKeyboardButton> row2 = InlineKeyboardUtil.getRow(button2);

            InlineKeyboardButton button3 = InlineKeyboardUtil.getButton("\uD83D\uDC68\u200D\uD83C\uDF93 Mentorlar haqida ma'lumot", "menu/showMentors/" + user.getId());
            List<InlineKeyboardButton> row3 = InlineKeyboardUtil.getRow(button3);

            InlineKeyboardButton button4 = InlineKeyboardUtil.getButton("✈️ Markazimiz yutuqlari", "menu/showAbout/" + user.getId());
            List<InlineKeyboardButton> row4 = InlineKeyboardUtil.getRow(button4);

            List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(row1, row2, row3, row4);
            InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(rowList);

            editMessageText.setReplyMarkup(markup);
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        } else if (data.startsWith("show/mentors/next/")) {
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(message.getChatId()), message.getMessageId());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            String next = data.split("/")[3];
            int id = Integer.parseInt(next);

            if (Database.MENTOR_LIST.get(Database.MENTOR_LIST.size() - 1).getId().equals(id)) {
                Mentor mentor = Database.MENTOR_LIST.stream().filter(mentor1 -> mentor1.getId().equals(id)).toList().get(0);
                SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()), new InputFile(mentor.getImageUrl()));
                sendPhoto.setCaption("Ismi familiyasi : " + mentor.getFirstName() + " " + mentor.getLastName()
                        + "\n" + "Fani : " + mentor.getSubject() + "\n" +
                        "Yoshi : " + mentor.getAge() + "\n"
                        + "Tel raqami : " + mentor.getPhoneNumber());

                Integer previous = Database.MENTOR_LIST.get(Database.MENTOR_LIST.size() - 2).getId();
                InlineKeyboardButton button = InlineKeyboardUtil.getButton("⏪Oldingisi", "show/mentors/previous/" + previous + "/" + user.getId());
                List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(InlineKeyboardUtil.getRow(button));
                InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(rowList);
                sendPhoto.setReplyMarkup(markup);

                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);

            } else {
                Mentor mentor = Database.MENTOR_LIST.stream().filter(mentor1 -> mentor1.getId().equals(id)).toList().get(0);
                SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()), new InputFile(mentor.getImageUrl()));
                sendPhoto.setCaption("Ismi familiyasi : " + mentor.getFirstName() + " " + mentor.getLastName()
                        + "\n" + "Fani : " + mentor.getSubject() + "\n" +
                        "Yoshi : " + mentor.getAge() + "\n"
                        + "Tel raqami : " + mentor.getPhoneNumber());

                mentor = Database.MENTOR_LIST.stream().filter(mentor1 -> mentor1.getId().equals(id)).toList().get(0);
                for (int i = 1; i < Database.MENTOR_LIST.size(); i++) {
                    if (Database.MENTOR_LIST.get(i - 1).getId().equals(mentor.getId())) {
                        next = String.valueOf(Database.MENTOR_LIST.get(i).getId());
                        break;
                    }
                }

                String previous = null;
                for (int i = 0; i < Database.MENTOR_LIST.size() - 1; i++) {
                    if (Database.MENTOR_LIST.get(i + 1).getId().equals(mentor.getId())) {
                        previous = String.valueOf(Database.MENTOR_LIST.get(i).getId());
                        break;
                    }
                }

                InlineKeyboardButton button = InlineKeyboardUtil.getButton("Keyingisi⏩", "show/mentors/next/" + next + "/" + user.getId());
                InlineKeyboardButton button1 = InlineKeyboardUtil.getButton("⏪Oldingisi", "show/mentors/previous/" + previous + "/" + user.getId());
                List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(InlineKeyboardUtil.getRow(button1, button));
                InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(rowList);
                sendPhoto.setReplyMarkup(markup);

                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);
            }
        } else if (data.startsWith("show/mentors/previous/")) {
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(message.getChatId()), message.getMessageId());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            String previous = data.split("/")[3];
            int id = Integer.parseInt(previous);

            if (Database.MENTOR_LIST.get(0).getId().equals(id)) {
                Mentor mentor = Database.MENTOR_LIST.stream().filter(mentor1 -> mentor1.getId().equals(id)).toList().get(0);
                SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()), new InputFile(mentor.getImageUrl()));
                sendPhoto.setCaption("Ismi familiyasi : " + mentor.getFirstName() + " " + mentor.getLastName()
                        + "\n" + "Fani : " + mentor.getSubject() + "\n" +
                        "Yoshi : " + mentor.getAge() + "\n"
                        + "Tel raqami : " + mentor.getPhoneNumber());

                Integer next = Database.MENTOR_LIST.get(1).getId();
                InlineKeyboardButton button = InlineKeyboardUtil.getButton("Keyingisi⏩", "show/mentors/next/" + next + "/" + user.getId());
                List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(InlineKeyboardUtil.getRow(button));
                InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(rowList);
                sendPhoto.setReplyMarkup(markup);

                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);

            } else {
                Mentor mentor = Database.MENTOR_LIST.stream().filter(mentor1 -> mentor1.getId().equals(id)).toList().get(0);
                SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()), new InputFile(mentor.getImageUrl()));
                sendPhoto.setCaption("Ismi familiyasi : " + mentor.getFirstName() + " " + mentor.getLastName()
                        + "\n" + "Fani : " + mentor.getSubject() + "\n" +
                        "Yoshi : " + mentor.getAge() + "\n"
                        + "Tel raqami : " + mentor.getPhoneNumber());

                mentor = Database.MENTOR_LIST.stream().filter(mentor1 -> mentor1.getId().equals(id)).toList().get(0);
                String next = null;
                for (int i = 1; i < Database.MENTOR_LIST.size(); i++) {
                    if (Database.MENTOR_LIST.get(i - 1).getId().equals(mentor.getId())) {
                        next = String.valueOf(Database.MENTOR_LIST.get(i).getId());
                        break;
                    }
                }

                for (int i = 0; i < Database.MENTOR_LIST.size() - 1; i++) {
                    if (Database.MENTOR_LIST.get(i + 1).getId().equals(mentor.getId())) {
                        previous = String.valueOf(Database.MENTOR_LIST.get(i).getId());
                        break;
                    }
                }

                InlineKeyboardButton button = InlineKeyboardUtil.getButton("Keyingisi⏩", "show/mentors/next/" + next + "/" + user.getId());
                InlineKeyboardButton button1 = InlineKeyboardUtil.getButton("⏪Oldingisi", "show/mentors/previous/" + previous + "/" + user.getId());
                List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(InlineKeyboardUtil.getRow(button1, button));
                InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(rowList);
                sendPhoto.setReplyMarkup(markup);

                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);
            }
        } else if (data.startsWith("menu/showCourses/course")) {
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(message.getChatId()), message.getMessageId());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            String id = data.split("/")[3];
            Course course = Database.COURSE_LIST.stream().filter(course1 -> course1.getId().equals(Integer.parseInt(id))).toList().get(0);

            SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()), new InputFile(course.getImageUrl()));
            String stringBuilder = "Nomi: " + course.getName() + "\n" +
                    "Narxi: " + course.getPrice() + "\n" +
                    "Davomiyligi: " + course.getValidityPeriod() + "\n\n";
            sendPhoto.setCaption(stringBuilder);

            InlineKeyboardButton button1 = InlineKeyboardUtil.getButton("\uD83D\uDDD3Ro'yhatdan o'tish", "menu/showCourses/register/" + course.getId());
            InlineKeyboardButton button2 = InlineKeyboardUtil.getButton("⬅️Orqaga", "back_to_menu/showCourses/" + user.getId());
            List<InlineKeyboardButton> row1 = InlineKeyboardUtil.getRow(button1);
            List<InlineKeyboardButton> row2 = InlineKeyboardUtil.getRow(button2);
            InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(InlineKeyboardUtil.getRowList(row1, row2));
            sendPhoto.setReplyMarkup(markup);

            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);

        } else if (data.equals("back_to_menu/showCourses/" + user.getId())) {
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(message.getChatId()), message.getMessageId());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));

            if (Database.COURSE_LIST.isEmpty()) {
                sendMessage.setText("Markazda hali kurslar mavjud emas!");
                List<InlineKeyboardButton> row = InlineKeyboardUtil.getRow(InlineKeyboardUtil
                        .getButton("⬅️Orqaga", "back_from_course_to_menu" + user.getId()));
                List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(row);
                InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(rowList);
                sendMessage.setReplyMarkup(markup);

                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            } else {
                sendMessage.setText("Sizga aynan qaysi yo'nalish bo'yicha kurslar kerak: ");
                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                for (Course course : Database.COURSE_LIST) {
                    List<InlineKeyboardButton> row = InlineKeyboardUtil.getRow(InlineKeyboardUtil
                            .getButton(course.getName(), "menu/showCourses/course/" + course.getId()));
                    rowList.add(row);
                }

                List<InlineKeyboardButton> row = InlineKeyboardUtil.getRow(InlineKeyboardUtil
                        .getButton("⬅️Orqaga", "back_to_menu" + user.getId()));
                rowList.add(row);

                sendMessage.setReplyMarkup(InlineKeyboardUtil.getMarkup(rowList));
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }
        } else if (data.startsWith("menu/showCourses/register")) {
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(message.getChatId()), message.getMessageId());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            String id = data.split("/")[3];

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(message.getChatId()));

            sendMessage.setText("Haqiqatdan ham shu kursga ro'yhatdan o'tmoqchimisiz!");
            InlineKeyboardButton button1 = InlineKeyboardUtil.getButton("✅", "register/yes/" + id);
            InlineKeyboardButton button2 = InlineKeyboardUtil.getButton("❌", "menu/showCourses/course/" + id);
            List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(InlineKeyboardUtil.getRow(button1, button2));
            sendMessage.setReplyMarkup(InlineKeyboardUtil.getMarkup(rowList));

            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        } else if (data.startsWith("register/yes")) {
            String id = data.split("/")[2];

            Course course = CourseService.getCourseById(id);
            Database.REGISTERED_COURSES.put(user.getId() + id, course);

            editMessageText.setText("Kursga ro'yhatdan o'tish uchun sorov yuborildi, adminlarimiz siz bilan tez orada bog'lanishadi!");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        } else if (data.equals("sendMessageToAdmin/" + user.getId())) {
            editMessageText.setText("Xabarni kiriting:");
            map.put(String.valueOf(user.getId()), "sendMessageToAdmin/");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
    }
}
