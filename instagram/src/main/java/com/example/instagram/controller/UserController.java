package com.example.instagram.controller;

import com.example.instagram.dto.SignInInput;
import com.example.instagram.dto.SignInOutput;
import com.example.instagram.dto.SignUpInput;
import com.example.instagram.dto.SignUpOutput;
import com.example.instagram.module.User;
import com.example.instagram.service.UserService;
import jakarta.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService service;


    @PostMapping("/signup")
    public SignUpOutput signup(@RequestBody SignUpInput signUpDto)
    {
        return service.signUp(signUpDto);
    }
    @PostMapping("/signin")
    public SignInOutput signin(@RequestBody SignInInput signInDto)
    {
        return service.signIn(signInDto);
    }
    @PostMapping(value="/add/user")
    public ResponseEntity<String> saveUser(@RequestBody String userData) {

        User user = setUser(userData);
        int userId = service.saveUser(user);
        return new ResponseEntity<>("user saved with id- " +userId, HttpStatus.CREATED);

    }

    @GetMapping(value = "/get/user")
    public ResponseEntity<String> getUser(@Nullable @RequestParam String userId) {

        JSONArray userDetails = service.getUser(userId);
        return new ResponseEntity<>(userDetails.toString(), HttpStatus.OK);
    }



    @PutMapping(value = "/userId/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable String userId, @RequestBody String userData) {

        User user = setUser(userData);
        service.updateUser(user, userId);

        return new ResponseEntity<>("user updated", HttpStatus.OK);

    }


    private User setUser(String userData) {

        JSONObject jsonObject = new JSONObject(userData);
        User user = new User();

        user.setAge(jsonObject.getInt("age"));
        user.setUserEmail(jsonObject.getString("email"));
        user.setUserPassword(jsonObject.getString("password"));
        user.setFirstName(jsonObject.getString("firstName"));
        user.setLastName(jsonObject.getString("lastName"));
        user.setPhoneNumber(jsonObject.getString("phoneNumber"));


        return user;

    }
}
