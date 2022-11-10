package com.company.service;

import com.company.database.Database;
import com.company.model.Mentor;

public class MentorService {
    public static Mentor getMentorById(String id) {
        return Database.MENTOR_LIST.stream().filter(mentor -> String.valueOf(mentor.getId()).equals(id)).toList().get(0);
    }

    public static Mentor getMentorByPhoneNumber(String text) {

        for (Mentor mentor : Database.MENTOR_LIST) {
            if(mentor.getPhoneNumber().equals(text)){
                return mentor;
            }
        }
        return null;
    }
}
