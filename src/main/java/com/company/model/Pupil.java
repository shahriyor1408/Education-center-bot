package com.company.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Pupil {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer age;
    private Integer courseId;
}
