package com.example.instagram.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpInput {

    private String firstName;
    private String lastName;
    private int Age;
    private String userEmail;
    private String userPassword;
    private String phoneNumber;
}
