package com.company.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Mentor {
    private Integer id;
    private String firstName;
    private String lastName;
    private String subject;
    private String imageUrl;
    private Integer age;
    private String phoneNumber;
}
