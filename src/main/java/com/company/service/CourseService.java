package com.company.service;

import com.company.database.Database;
import com.company.model.Course;

public class CourseService {
    public static Course getCourseById(String id) {
        return Database.COURSE_LIST.stream().filter(course -> id.equals(String.valueOf(course.getId()))).toList().get(0);
    }

    public static Course getCourseByName(String text) {
        for (Course course : Database.COURSE_LIST) {
            if(course.getName().equalsIgnoreCase(text)){
                return course;
            }
        }
        return null;
    }
}
