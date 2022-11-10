package com.company.database;

import com.company.model.Course;
import com.company.model.Customer;
import com.company.model.Mentor;
import com.company.model.Pupil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Database {
    List<Customer> CUSTOMER_LIST = new ArrayList<>();
    Map<String, Course> REGISTERED_COURSES = new HashMap<>();
    List<Course> COURSE_LIST = new ArrayList<>();
    List<Mentor> MENTOR_LIST = new ArrayList<>();
    List<Pupil> PUPIL_LIST = new ArrayList<>();

    static void loadData(){
        COURSE_LIST.add(new Course(1,"Matematika","200 ming","9 oy","AgACAgIAAxkBAAIBv2J3rcZ1t1adO-yBznfhiID1f5XhAAIpuTEbrGDBS_t9Z8hq3UOeAQADAgADbQADJAQ"));
        COURSE_LIST.add(new Course(2,"Front end","700 ming","6 oy","AgACAgIAAxkBAAIBvmJ3raOkPcU4U__1tw5_WF5v2P5bAAIouTEbrGDBS0M4A2hqQ7vhAQADAgADbQADJAQ"));

        MENTOR_LIST.add(new Mentor(1,"Aliksey","Johnson","Matematika","AgACAgIAAxkBAAOwYnauM-R9I6h1H1-z5_dNHNZ45t0AArq8MRuM-bhL1aPkrLxanLcBAAMCAANtAAMkBA",24,"+998901254879"));
        MENTOR_LIST.add(new Mentor(2,"Salohiddin","Mo'ydinov","Fizika","AgACAgIAAxkBAAOxYnauXm00Z7COYlIq0yqTY_J0sgcAAru8MRuM-bhL0uCNMId31BMBAAMCAANtAAMkBA",22,"+998996083904"));
        MENTOR_LIST.add(new Mentor(3,"Scarlett","Karenina","Ingliz tili","AgACAgIAAxkBAAOyYnauc6KLFO7OhO3wjHleoThKM6cAAry8MRuM-bhL42GxDgfS99YBAAMCAANtAAMkBA",19,"+998939439923"));
        MENTOR_LIST.add(new Mentor(4,"Shohruh","Yo'ldoshev","Back end","AgACAgIAAxkBAAIBQmJ3TcErXC-QMXTqnzfnyEiFky-kAAIxuDEbrGDBSzRBlAPBGr05AQADAgADbQADJAQ",21,"+998338887766"));

        PUPIL_LIST.add(new Pupil(1,"Shahriyor","Sohidjonov",20,2));
        PUPIL_LIST.add(new Pupil(2,"Muhriddin","Rozimboyev",21,1));
        PUPIL_LIST.add(new Pupil(3,"Jahongir","Qayumjonov",19,1));
        PUPIL_LIST.add(new Pupil(4,"Otabek","Karimov",18,2));
    }
}
