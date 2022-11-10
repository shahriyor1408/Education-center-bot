package com.company.util;

import com.company.database.Database;
import com.company.model.Course;
import com.company.model.Mentor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InlineKeyboardUtil {

    public static InlineKeyboardButton getButton(String demo, String callBack) {
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(demo);
        inlineKeyboardButton.setCallbackData(callBack);

        return inlineKeyboardButton;
    }

    public static List<InlineKeyboardButton> getRow(InlineKeyboardButton ... row){
        return new ArrayList<>(Arrays.asList(row));
    }

    @SafeVarargs
    public static List<List<InlineKeyboardButton>> getRowList(List<InlineKeyboardButton>... rows){
        return new ArrayList<>(Arrays.asList(rows));
    }

    public static InlineKeyboardMarkup getMarkup(List<List<InlineKeyboardButton>> rowList){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public static InlineKeyboardMarkup getCourseCrudButtons() {
        InlineKeyboardButton button1 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.COURSE_ADD_DEMO, InlineKeyboardConstants.COURSE_ADD_DATA);
        InlineKeyboardButton button2 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.COURSE_EDIT_DEMO, InlineKeyboardConstants.COURSE_EDIT_DATA);
        InlineKeyboardButton button3 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.COURSE_DELETE_DEMO, InlineKeyboardConstants.COURSE_DELETE_DATA);
        InlineKeyboardButton button4 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.COURSE_SHOW_DEMO, InlineKeyboardConstants.COURSE_SHOW_DATA);
        InlineKeyboardButton button5 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.BACK_TO_MENU_DEMO, InlineKeyboardConstants.BACK_TO_MENU_DATA);

        List<InlineKeyboardButton> row1 = InlineKeyboardUtil.getRow(button1);
        List<InlineKeyboardButton> row2 = InlineKeyboardUtil.getRow(button2);
        List<InlineKeyboardButton> row3 = InlineKeyboardUtil.getRow(button3);
        List<InlineKeyboardButton> row4 = InlineKeyboardUtil.getRow(button4);
        List<InlineKeyboardButton> row5 = InlineKeyboardUtil.getRow(button5);

        return getMarkup(getRowList(row1,row2,row3,row4,row5));
    }

    public static InlineKeyboardMarkup getMentorCrudButtons() {
        InlineKeyboardButton button1 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.MENTOR_ADD_DEMO, InlineKeyboardConstants.MENTOR_ADD_DATA);
        InlineKeyboardButton button2 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.MENTOR_EDIT_DEMO, InlineKeyboardConstants.MENTOR_EDIT_DATA);
        InlineKeyboardButton button3 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.MENTOR_DELETE_DEMO, InlineKeyboardConstants.MENTOR_DELETE_DATA);
        InlineKeyboardButton button4 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.MENTOR_SHOW_DEMO, InlineKeyboardConstants.MENTOR_SHOW_DATA);
        InlineKeyboardButton button5 = InlineKeyboardUtil.getButton(InlineKeyboardConstants.BACK_TO_MENU_DEMO, InlineKeyboardConstants.BACK_TO_MENU_DATA);

        List<InlineKeyboardButton> row1 = InlineKeyboardUtil.getRow(button1);
        List<InlineKeyboardButton> row2 = InlineKeyboardUtil.getRow(button2);
        List<InlineKeyboardButton> row3 = InlineKeyboardUtil.getRow(button3);
        List<InlineKeyboardButton> row4 = InlineKeyboardUtil.getRow(button4);
        List<InlineKeyboardButton> row5 = InlineKeyboardUtil.getRow(button5);

        return getMarkup(getRowList(row1,row2,row3,row4,row5));
    }

    public static InlineKeyboardMarkup getCourseMarkup(String callBack) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (Course course : Database.COURSE_LIST) {
            InlineKeyboardButton button = getButton(course.getName(), callBack + course.getId());
            rowList.add(getRow(button));
        }
        rowList.add(getRow(getButton("⬅️Orqaga",InlineKeyboardConstants.COURSE_CRUD_DATA)));
        return InlineKeyboardUtil.getMarkup(rowList);
    }

    public static InlineKeyboardMarkup getMentorMarkup(String callBack) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (Mentor mentor : Database.MENTOR_LIST) {
            InlineKeyboardButton button = getButton(mentor.getFirstName() + " " + mentor.getLastName(), callBack + mentor.getId());
            rowList.add(getRow(button));
        }
        rowList.add(getRow(getButton("⬅️Orqaga",InlineKeyboardConstants.MENTOR_CRUD_DATA)));
        return InlineKeyboardUtil.getMarkup(rowList);
    }
}
