package com.company.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Course {
    private Integer id;
    private String name;
    private String price;
    private String validityPeriod;
    private String imageUrl;
}
