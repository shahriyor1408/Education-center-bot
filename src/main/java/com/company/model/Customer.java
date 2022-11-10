package com.company.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Customer {
    private String firstName;
    private String lastName;
    private String userId;
    private String phoneNumber;
}
