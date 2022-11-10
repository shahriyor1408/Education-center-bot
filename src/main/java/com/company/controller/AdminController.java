package com.company.controller;

import com.company.container.ComponentContainer;
import com.company.database.Database;
import com.company.enums.AdminStatus;
import com.company.model.Course;
import com.company.model.Customer;
import com.company.model.Mentor;
import com.company.reports.GenerateFiles;
import com.company.service.CourseService;
import com.company.service.MentorService;
import com.company.util.InlineKeyboardConstants;
import com.company.util.InlineKeyboardUtil;
import com.company.util.KeyboardUtil;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController {
    static Map<String,AdminStatus> map = new HashMap<>();
    static Map<String,Course> courseMap = new HashMap<>();
    static Map<String,Mentor> mentorMap = new HashMap<>();
    static Map<String,AdminStatus> adminStatusMap = new HashMap<>();

    public static void handleMessage(User user, Message message) {
        if(message.hasText()){
            String text = message.getText();
            handleText(text,message);
        }
        if(message.hasPhoto()){
            List<PhotoSize> photoSizeList = message.getPhoto();
            handlePhoto(photoSizeList,user,message);
        }

    }

    private static void handlePhoto(List<PhotoSize> photoSizeList, User user,Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        String fileId = photoSizeList.get(photoSizeList.size() - 1).getFileId();
        if(adminStatusMap.get(String.valueOf(user.getId())).equals(AdminStatus.ADD_COURSE)){
            Course course = courseMap.get(String.valueOf(user.getId()));
            course.setImageUrl(fileId);

            SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()),new InputFile(course.getImageUrl()));

            String stringBuilder = "Nomi: " + course.getName() + "\n" +
                    "Narxi: " + course.getPrice() + "\n" +
                    "Davomiyligi: " + course.getValidityPeriod() + "\n\n";
            sendPhoto.setCaption(stringBuilder);
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);

            sendMessage.setText("Yangi kursni qo'shasizmi?");
            InlineKeyboardButton yes = InlineKeyboardUtil.getButton("✅", "add_course_yes");
            InlineKeyboardButton button = InlineKeyboardUtil.getButton("❌", InlineKeyboardConstants.COURSE_CRUD_DATA);
            List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(InlineKeyboardUtil.getRow(yes, button));
            sendMessage.setReplyMarkup(InlineKeyboardUtil.getMarkup(rowList));
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        }
        else if(adminStatusMap.get(String.valueOf(user.getId())).equals(AdminStatus.ADD_MENTOR)){
            Mentor mentor = mentorMap.get(String.valueOf(user.getId()));
            mentor.setImageUrl(fileId);
            SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()),new InputFile(mentor.getImageUrl()));
            String stringBuilder = "Ismi: " + mentor.getFirstName() + "\n" + "Familiyasi: " + mentor.getLastName() + "\n" +
                    "Fani: " + mentor.getSubject() + "\n" + "Yoshi: " + mentor.getAge() + "\n" +
                    "Telefon raqami: " + mentor.getPhoneNumber() + "\n\n";
            sendPhoto.setCaption(stringBuilder);
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);

            sendMessage.setText("Yangi mentorni qo'shasizmi?");
            InlineKeyboardButton yes = InlineKeyboardUtil.getButton("✅", "add_mentor_yes");
            InlineKeyboardButton button = InlineKeyboardUtil.getButton("❌", InlineKeyboardConstants.MENTOR_CRUD_DATA);
            List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(InlineKeyboardUtil.getRow(yes, button));
            sendMessage.setReplyMarkup(InlineKeyboardUtil.getMarkup(rowList));
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        }
        else if(adminStatusMap.get(String.valueOf(user.getId())).equals(AdminStatus.EDIT_COURSE)){
            Course course = courseMap.get(String.valueOf(user.getId()));
            course.setImageUrl(fileId);

            SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()),new InputFile(course.getImageUrl()));

            String stringBuilder = "Nomi: " + course.getName() + "\n" +
                    "Narxi: " + course.getPrice() + "\n" +
                    "Davomiyligi: " + course.getValidityPeriod() + "\n\n";
            sendPhoto.setCaption(stringBuilder);
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);

            sendMessage.setText("Kursni o'zgartirmoqchimisiz?");
            InlineKeyboardButton yes = InlineKeyboardUtil.getButton("✅", "edit_course_yes");
            InlineKeyboardButton button = InlineKeyboardUtil.getButton("❌", InlineKeyboardConstants.COURSE_CRUD_DATA);
            List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(InlineKeyboardUtil.getRow(yes, button));
            sendMessage.setReplyMarkup(InlineKeyboardUtil.getMarkup(rowList));
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        }
        else if(adminStatusMap.get(String.valueOf(user.getId())).equals(AdminStatus.EDIT_MENTOR)){
            Mentor mentor = mentorMap.get(String.valueOf(user.getId()));
            mentor.setImageUrl(fileId);
            SendPhoto sendPhoto = new SendPhoto(String.valueOf(message.getChatId()),new InputFile(mentor.getImageUrl()));
            String stringBuilder = "Ismi: " + mentor.getFirstName() + "\n" + "Familiyasi: " + mentor.getLastName() + "\n" +
                    "Fani: " + mentor.getSubject() + "\n" + "Yoshi: " + mentor.getAge() + "\n" +
                    "Telefon raqami: " + mentor.getPhoneNumber() + "\n\n";
            sendPhoto.setCaption(stringBuilder);
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendPhoto);

            sendMessage.setText("Mentorni o'zgartirmoqchimisiz?");
            InlineKeyboardButton yes = InlineKeyboardUtil.getButton("✅", "edit_mentor_yes");
            InlineKeyboardButton button = InlineKeyboardUtil.getButton("❌", InlineKeyboardConstants.MENTOR_CRUD_DATA);
            List<List<InlineKeyboardButton>> rowList = InlineKeyboardUtil.getRowList(InlineKeyboardUtil.getRow(yes, button));
            sendMessage.setReplyMarkup(InlineKeyboardUtil.getMarkup(rowList));
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        }
    }

    private static void handleText(String text, Message message) {
        String userId = String.valueOf(message.getFrom().getId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        if(text.equals("/start")){
            sendMessage.setText("Assalomu alaykum botga Xush kelibsiz!");

            KeyboardButton button1 = KeyboardUtil.getKeyBoardButton("⚙️MENU");
            KeyboardButton button2 = KeyboardUtil.getKeyBoardButton("\uD83D\uDCC8Umumiy o'quvchilar ro'yhati");
            KeyboardButton button3 = KeyboardUtil.getKeyBoardButton("\uD83D\uDCE9Barchaga xabar yozish");
            List<KeyboardRow> rowList = KeyboardUtil.getKeyboardRowList(KeyboardUtil.getKeyboardRow(button1, button2),KeyboardUtil.getKeyboardRow(button3));

            sendMessage.setReplyMarkup(KeyboardUtil.getKeyboardMarkup(rowList));
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);

        }
        else if(text.equals("⚙️MENU")){
            sendMessage.setText("MENU: ");
            InlineKeyboardButton button1 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.COURSE_CRUD_DEMO, InlineKeyboardConstants.COURSE_CRUD_DATA);
            InlineKeyboardButton button2 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.MENTOR_CRUD_DEMO, InlineKeyboardConstants.MENTOR_CRUD_DATA);
            InlineKeyboardButton button3 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.SHOW_PUPILS_EACH_COURSES_DEMO, InlineKeyboardConstants.SHOW_PUPILS_EACH_COURSES_DATA);

            List<InlineKeyboardButton> row1 = InlineKeyboardUtil.getRow(button1);
            List<InlineKeyboardButton> row2 = InlineKeyboardUtil.getRow(button2);
            List<InlineKeyboardButton> row3 = InlineKeyboardUtil.getRow(button3);

            InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(InlineKeyboardUtil.getRowList(row1, row2, row3));
            sendMessage.setReplyMarkup(markup);

            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);

        }
        else if(text.equals("\uD83D\uDCC8Umumiy o'quvchilar ro'yhati")){
            if(Database.PUPIL_LIST.isEmpty()){
                sendMessage.setText("O'quvchilar mavjud emas!");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }else {
                DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(message.getChatId()),message.getMessageId());
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

                File file = GenerateFiles.getAllPupil();
                SendDocument sendDocument = new SendDocument(String.valueOf(message.getChatId()),new InputFile(file));
                sendDocument.setCaption("O'quvchilar ro'yhati");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendDocument);
            }
        }
        else if(text.equals("\uD83D\uDCE9Barchaga xabar yozish")){

            if(Database.CUSTOMER_LIST.isEmpty()){
                sendMessage.setText("Foydalanuvchilar mavjud emas!");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }else {
                sendMessage.setText("Xabarni kiriting: ");
                map.put(String.valueOf(message.getFrom().getId()), AdminStatus.SEND_MESSAGE_TO_ALL);

                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }

        }
        else if(map.containsValue(AdminStatus.SEND_MESSAGE_TO_ALL)){
            for (Customer customer : Database.CUSTOMER_LIST) {
                SendMessage sendMessage1 = new SendMessage();
                sendMessage1.setChatId(customer.getUserId());
                sendMessage1.setText(text);
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage1);
            }
            map.remove(String.valueOf(message.getFrom().getId()));
            sendMessage.setText("Xabar yuborildi!");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        }
        else if(map.containsValue(AdminStatus.SEND_REQUEST_TO_USER)){

            for (String key : map.keySet()) {
                if(map.get(key).equals(AdminStatus.SEND_REQUEST_TO_USER)){
                    SendMessage sendMessageToUser = new SendMessage();
                    sendMessageToUser.setChatId(key.split("/")[1]);
                    sendMessageToUser.setText(text);
                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessageToUser);
                    map.remove(key);
                    break;
                }
            }
            sendMessage.setText("Xabar yuborildi!");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
        }
        else if(adminStatusMap.get(userId).equals(AdminStatus.ADD_COURSE)){
            Course course = courseMap.get(String.valueOf(userId));
            System.out.println(course);
            course.setId(Database.COURSE_LIST.get(Database.COURSE_LIST.size() - 1).getId() + 1);
            if(course.getName() == null){
                Course courseByName = CourseService.getCourseByName(text);
                if(courseByName == null){
                    course.setName(text);
                    sendMessage.setText("Narxini kiriting: ");
                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
                }else {
                    sendMessage.setText("Bunday nomdagi kurs mavjud!");
                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
                }

            }
            else if(course.getPrice() == null){
                course.setPrice(text);
                sendMessage.setText("Kurs davomiyligini kiriting:");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }
            else if(course.getValidityPeriod() == null){
                course.setValidityPeriod(text);
                sendMessage.setText("Kurs rasmini yuboring:");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }
        }
        else if(adminStatusMap.get(userId).equals(AdminStatus.ADD_MENTOR)){
            Mentor mentor = mentorMap.get(String.valueOf(userId));
            System.out.println(mentor);
            mentor.setId(Database.MENTOR_LIST.get(Database.MENTOR_LIST.size() - 1).getId() + 1);
            if(mentor.getFirstName() == null){
                mentor.setFirstName(text);
                sendMessage.setText("Familiyasini kiriting:");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }
            else if(mentor.getLastName() == null){
                mentor.setLastName(text);
                sendMessage.setText("Fani nomini kiriting:");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }
            else if(mentor.getSubject() == null){
                Course courseByName = CourseService.getCourseByName(text);
                if(courseByName == null){
                    sendMessage.setText("Mentor uchun bunday fan mavjud emas!");
                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
                }else {
                    mentor.setSubject(text);
                    sendMessage.setText("Yoshini kiriting:");
                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
                }
            }
            else if(mentor.getAge() == null){
                mentor.setAge(Integer.valueOf(text));
                sendMessage.setText("Telefon raqamini kiriting(+998XXXXXXXXX - ko'rinishda):");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }
            else if(mentor.getPhoneNumber() == null){
                Mentor mentorByPhoneNumber = MentorService.getMentorByPhoneNumber(text);
                if(mentorByPhoneNumber == null){
                    mentor.setPhoneNumber(text);
                    sendMessage.setText("Rasmini yuboring:");
                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
                }else {
                    sendMessage.setText("Bunday telefon raqamli mentor mavjud!");
                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
                }
            }
        }
        else if(adminStatusMap.get(userId).equals(AdminStatus.EDIT_COURSE)){
            Course course = courseMap.get(String.valueOf(userId));
            System.out.println(course);
            if(course.getName() == null){
                course.setName(text);
                sendMessage.setText("Narxini kiriting: ");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);

            }
            else if(course.getPrice() == null){
                course.setPrice(text);
                sendMessage.setText("Kurs davomiyligini kiriting:");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }
            else if(course.getValidityPeriod() == null){
                course.setValidityPeriod(text);
                sendMessage.setText("Kurs rasmini yuboring:");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }
        }
        else if(adminStatusMap.get(userId).equals(AdminStatus.EDIT_MENTOR)){
            Mentor mentor = mentorMap.get(String.valueOf(userId));
            System.out.println(mentor);
            if(mentor.getFirstName() == null){
                mentor.setFirstName(text);
                sendMessage.setText("Familiyasini kiriting:");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }
            else if(mentor.getLastName() == null){
                mentor.setLastName(text);
                sendMessage.setText("Fani nomini kiriting:");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }
            else if(mentor.getSubject() == null){
                Course courseByName = CourseService.getCourseByName(text);
                if(courseByName == null){
                    sendMessage.setText("Mentor uchun bunday fan mavjud emas!");
                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
                }else {
                    mentor.setSubject(text);
                    sendMessage.setText("Yoshini kiriting:");
                    ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
                }
            }
            else if(mentor.getAge() == null){
                mentor.setAge(Integer.valueOf(text));
                sendMessage.setText("Telefon raqamini kiriting(+998XXXXXXXXX - ko'rinishda):");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }
            else if(mentor.getPhoneNumber() == null){
                mentor.setPhoneNumber(text);
                sendMessage.setText("Rasmini yuboring:");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendMessage);
            }
        }
    }

    public static void handleCallback(User user, Message message, String data) {
        EditMessageText editMessageText = new EditMessageText(String.valueOf(message.getChatId()));
        editMessageText.setChatId(String.valueOf(user.getId()));
        editMessageText.setMessageId(message.getMessageId());

        if(data.startsWith("send_request_to_user/")){
            editMessageText.setText("Xabarni kiriting:");

            String id = data.split("/")[1];

            map.put((user.getId() + "/" + id),AdminStatus.SEND_REQUEST_TO_USER);
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);

        }
        else if(data.equals(InlineKeyboardConstants.COURSE_CRUD_DATA)){
            editMessageText.setText("Kurslar ustida amallar:");
            InlineKeyboardMarkup markup = InlineKeyboardUtil.getCourseCrudButtons();
            editMessageText.setReplyMarkup(markup);

            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.equals(InlineKeyboardConstants.MENTOR_CRUD_DATA)){
            editMessageText.setText("Mentorlar ustida amallar:");
            InlineKeyboardMarkup markup = InlineKeyboardUtil.getMentorCrudButtons();
            editMessageText.setReplyMarkup(markup);

            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.equals(InlineKeyboardConstants.SHOW_PUPILS_EACH_COURSES_DATA)){
            DeleteMessage deleteMessage = new DeleteMessage(String.valueOf(message.getChatId()),message.getMessageId());
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(deleteMessage);

            File file = GenerateFiles.getAllPupilBuCourse();
            SendDocument sendDocument = new SendDocument(String.valueOf(message.getChatId()),new InputFile(file));
            sendDocument.setCaption("Kurslar bo'yicha ro'yhat");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(sendDocument);
        }
        else if(data.equals(InlineKeyboardConstants.BACK_TO_MENU_DATA)){
            editMessageText.setText("MENU: ");
            InlineKeyboardButton button1 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.COURSE_CRUD_DEMO, InlineKeyboardConstants.COURSE_CRUD_DATA);
            InlineKeyboardButton button2 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.MENTOR_CRUD_DEMO, InlineKeyboardConstants.MENTOR_CRUD_DATA);
            InlineKeyboardButton button3 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.SHOW_PUPILS_EACH_COURSES_DEMO, InlineKeyboardConstants.SHOW_PUPILS_EACH_COURSES_DATA);

            List<InlineKeyboardButton> row1 = InlineKeyboardUtil.getRow(button1);
            List<InlineKeyboardButton> row2 = InlineKeyboardUtil.getRow(button2);
            List<InlineKeyboardButton> row3 = InlineKeyboardUtil.getRow(button3);

            InlineKeyboardMarkup markup = InlineKeyboardUtil.getMarkup(InlineKeyboardUtil.getRowList(row1, row2, row3));
            editMessageText.setReplyMarkup(markup);

            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.equals(InlineKeyboardConstants.COURSE_DELETE_DATA)){
            if(Database.COURSE_LIST.isEmpty()){
                editMessageText.setText("O'chirish uchun kurslar mavjud emas!");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }else {
                InlineKeyboardMarkup markup = InlineKeyboardUtil.getCourseMarkup("select_deleted_course/");

                editMessageText.setText("Kursni tanlang:");
                editMessageText.setReplyMarkup(markup);
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }
        }
        else if(data.equals(InlineKeyboardConstants.MENTOR_DELETE_DATA)){
            if(Database.MENTOR_LIST.isEmpty()){
                editMessageText.setText("O'chirish uchun mentorlar mavjud emas!");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }else {
                InlineKeyboardMarkup markup = InlineKeyboardUtil.getMentorMarkup("select_deleted_mentor/");

                editMessageText.setText("Mentorni tanlang:");
                editMessageText.setReplyMarkup(markup);
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }
        }
        else if(data.startsWith("select_deleted_course/")){
            String id = data.split("/")[1];
            Course course = CourseService.getCourseById(id);

            editMessageText.setText(course.getName() + " ni o'chirmoqchimisiz?");
            InlineKeyboardButton button1 = InlineKeyboardUtil.getButton("✅", "delete_course_yes/" + id);
            InlineKeyboardButton button2 = InlineKeyboardUtil.getButton("❌", InlineKeyboardConstants.COURSE_CRUD_DATA);
            List<InlineKeyboardButton> row = InlineKeyboardUtil.getRow(button1, button2);

            editMessageText.setReplyMarkup(InlineKeyboardUtil.getMarkup(InlineKeyboardUtil.getRowList(row)));
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.startsWith("delete_course_yes/")){
            String id = data.split("/")[1];

            Database.COURSE_LIST.remove(CourseService.getCourseById(id));
            editMessageText.setText("Kurs o'chirildi!");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.startsWith("select_deleted_mentor/")){
            String id = data.split("/")[1];

            editMessageText.setText("Siz shu mentorni haqiqatdan ham o'chirmoqchimisiz?");
            InlineKeyboardButton button1 = InlineKeyboardUtil.getButton("✅", "delete_mentor_yes/" + id);
            InlineKeyboardButton button2 = InlineKeyboardUtil.getButton("❌", InlineKeyboardConstants.MENTOR_CRUD_DATA);
            List<InlineKeyboardButton> row = InlineKeyboardUtil.getRow(button1, button2);

            editMessageText.setReplyMarkup(InlineKeyboardUtil.getMarkup(InlineKeyboardUtil.getRowList(row)));
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.startsWith("delete_mentor_yes/")){
            String id = data.split("/")[1];

            Database.MENTOR_LIST.remove(MentorService.getMentorById(id));
            editMessageText.setText("Mentor o'chirildi!");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.equals(InlineKeyboardConstants.COURSE_SHOW_DATA)){
            if(Database.COURSE_LIST.isEmpty()){
                editMessageText.setText("Kurslar mavjud emas!");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }else {
                StringBuilder stringBuilder = new StringBuilder("Kurslar: \n\n");

                for (Course course : Database.COURSE_LIST) {
                    stringBuilder.append("Nomi: ").append(course.getName()).append("\n");
                    stringBuilder.append("Narxi: ").append(course.getPrice()).append("\n");
                    stringBuilder.append("Davomiyligi: ").append(course.getValidityPeriod()).append("\n\n");
                }
                List<InlineKeyboardButton> row = InlineKeyboardUtil.getRow(InlineKeyboardUtil.getButton("⬅️Orqaga", InlineKeyboardConstants.COURSE_CRUD_DATA));
                editMessageText.setReplyMarkup(InlineKeyboardUtil.getMarkup(InlineKeyboardUtil.getRowList(row)));

                editMessageText.setText(stringBuilder.toString());
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }

        }
        else if(data.equals(InlineKeyboardConstants.MENTOR_SHOW_DATA)){
            if(Database.MENTOR_LIST.isEmpty()){
                editMessageText.setText("Kurslar mavjud emas!");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }else {
                StringBuilder stringBuilder = new StringBuilder("Mentorlar: \n\n");

                for (Mentor mentor : Database.MENTOR_LIST) {
                    stringBuilder.append("Ismi: ").append(mentor.getFirstName()).append("\n");
                    stringBuilder.append("Familiyasi: ").append(mentor.getLastName()).append("\n");
                    stringBuilder.append("Fani: ").append(mentor.getSubject()).append("\n");
                    stringBuilder.append("Yoshi: ").append(mentor.getAge()).append("\n");
                    stringBuilder.append("Telefon: ").append(mentor.getPhoneNumber()).append("\n\n");
                }

                List<InlineKeyboardButton> row = InlineKeyboardUtil.getRow(InlineKeyboardUtil.getButton("⬅️Orqaga", InlineKeyboardConstants.MENTOR_CRUD_DATA));
                editMessageText.setReplyMarkup(InlineKeyboardUtil.getMarkup(InlineKeyboardUtil.getRowList(row)));

                editMessageText.setText(stringBuilder.toString());
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }
        }
        else if(data.equals(InlineKeyboardConstants.COURSE_ADD_DATA)){
            editMessageText.setText("Kurs nomini kiriting: ");
            courseMap.put(String.valueOf(user.getId()),new Course());
            adminStatusMap.put(String.valueOf(user.getId()),AdminStatus.ADD_COURSE);
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.equals("add_course_yes")){
            Course course = courseMap.get(String.valueOf(user.getId()));
            Database.COURSE_LIST.add(course);
            courseMap.remove(String.valueOf(user.getId()));
            adminStatusMap.remove(String.valueOf(user.getId()));
            editMessageText.setText("Kurs qo'shildi!");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.equals(InlineKeyboardConstants.MENTOR_ADD_DATA)){
            editMessageText.setText("Mentor ismini kiriting: ");
            mentorMap.put(String.valueOf(user.getId()),new Mentor());
            adminStatusMap.put(String.valueOf(user.getId()),AdminStatus.ADD_MENTOR);
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.equals("add_mentor_yes")){
            Mentor mentor = mentorMap.get(String.valueOf(user.getId()));
            Database.MENTOR_LIST.add(mentor);
            adminStatusMap.remove(String.valueOf(user.getId()));
            mentorMap.remove(String.valueOf(user.getId()));
            editMessageText.setText("Mentor qo'shildi!");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.equals(InlineKeyboardConstants.COURSE_EDIT_DATA)){
            if(Database.COURSE_LIST.isEmpty()){
                editMessageText.setText("O'zgartirish uchun kurslar mavjud emas!");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }else {
                editMessageText.setText("Kurslardan birini tanlang:");
                editMessageText.setReplyMarkup(InlineKeyboardUtil.getCourseMarkup("select_edited_course/"));
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }
        }
        else if(data.startsWith("select_edited_course/")){
            String id = data.split("/")[1];
            courseMap.put(String.valueOf(user.getId()),new Course());
            Course course = courseMap.get(String.valueOf(user.getId()));
            course.setId(Integer.valueOf(id));
            adminStatusMap.put(String.valueOf(user.getId()),AdminStatus.EDIT_COURSE);

            editMessageText.setText("Kurs nomini kiriting:");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.equals("edit_course_yes")){
            Course course = courseMap.get(String.valueOf(user.getId()));
            Course courseById = CourseService.getCourseById(String.valueOf(course.getId()));
            Database.COURSE_LIST.remove(courseById);

            Database.COURSE_LIST.add(course);
            courseMap.remove(String.valueOf(user.getId()));
            adminStatusMap.remove(String.valueOf(user.getId()));

            editMessageText.setText("Kurs o'zgartirildi!");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.equals(InlineKeyboardConstants.MENTOR_EDIT_DATA)){
            if(Database.MENTOR_LIST.isEmpty()){
                editMessageText.setText("O'zgartirish uchun mentorlar mavjud emas!");
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }else {
                editMessageText.setText("Mentorlardan birini tanlang:");
                editMessageText.setReplyMarkup(InlineKeyboardUtil.getMentorMarkup("select_edited_mentor/"));
                ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
            }
        }
        else if(data.startsWith("select_edited_mentor/")){
            String id = data.split("/")[1];
            editMessageText.setText("Mentor ismini kiriting: ");
            mentorMap.put(String.valueOf(user.getId()),new Mentor());
            Mentor mentor = mentorMap.get(String.valueOf(user.getId()));
            mentor.setId(Integer.valueOf(id));

            adminStatusMap.put(String.valueOf(user.getId()),AdminStatus.EDIT_MENTOR);
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }
        else if(data.equals("edit_mentor_yes")){
            Mentor mentor = mentorMap.get(String.valueOf(user.getId()));
            Mentor mentorById = MentorService.getMentorById(String.valueOf(mentor.getId()));

            Database.MENTOR_LIST.remove(mentorById);
            Database.MENTOR_LIST.add(mentor);

            adminStatusMap.remove(String.valueOf(user.getId()));
            mentorMap.remove(String.valueOf(user.getId()));

            editMessageText.setText("Mentor o'zgartirildi!");
            ComponentContainer.MY_TELEGRAM_BOT.sendMsg(editMessageText);
        }

    }
}
