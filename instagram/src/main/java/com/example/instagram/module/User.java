package com.example.instagram.module;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Table(name = "tbl_user")
public class User {
    @Id//primary key
    @Column(name = "user_id") // to create column name
    @GeneratedValue(strategy = GenerationType.IDENTITY) // To auto generate primary key
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "email")
    private String userEmail;
    @Column(name = "password")
    private String userPassword;

    @Column(name = "phone_number")
    private String phoneNumber;

    public User(String firstName, String lastName, Integer age, String userEmail, String userPassword, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.phoneNumber = phoneNumber;
    }
}
