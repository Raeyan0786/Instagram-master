package com.example.instagram.service;

import com.example.instagram.dao.UserRepository;
import com.example.instagram.dto.SignInInput;
import com.example.instagram.dto.SignInOutput;
import com.example.instagram.dto.SignUpInput;
import com.example.instagram.dto.SignUpOutput;
import com.example.instagram.module.Token;
import com.example.instagram.module.User;
import jakarta.xml.bind.DatatypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService tokenService;

    public SignUpOutput signUp(SignUpInput signUpDto) {

        //check if user exists or not based on email
        User user = userRepository.findFirstByUserEmail(signUpDto.getUserEmail());

        if(user != null)
        {
            throw new IllegalStateException("Patient already exists!!!!...sign in instead");
        }


        //encryption
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptPassword(signUpDto.getUserPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

        //save the user

        user = new User(signUpDto.getFirstName(),
                signUpDto.getLastName(), signUpDto.getAge(), signUpDto.getUserEmail(),
                encryptedPassword, signUpDto.getPhoneNumber());

        userRepository.save(user);

        //token creation and saving

        Token token = new Token(user);

        tokenService.saveToken(token);

        return new SignUpOutput("Patient registered","Patient created successfully");


    }

    private String encryptPassword(String userPassword) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(userPassword.getBytes());
        byte[] digested =  md5.digest();

        String hash = DatatypeConverter.printHexBinary(digested);
        return hash;
    }

    public SignInOutput signIn(SignInInput signInDto) {

        //get email

        User user = userRepository.findFirstByUserEmail(signInDto.getUserEmail());

        if(user == null)
        {
            throw new IllegalStateException("User invalid!!!!...sign up instead");
        }

        //encrypt the password

        String encryptedPassword = null;

        try {
            encryptedPassword = encryptPassword(signInDto.getUserPassword());
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }

        System.out.println(encryptedPassword);

        //match it with database encrypted password

        boolean isPasswordValid = encryptedPassword.equals(user.getUserPassword());
        System.out.println(isPasswordValid);
        if(!isPasswordValid)
        {
            throw new IllegalStateException("User invalid!!!!...sign up instead");
        }

        //figure out the token

        Token authToken = tokenService.getToken(user);
        System.out.println(authToken);
        System.out.println(authToken.getToken());
        //set up output response

        return new SignInOutput("Authentication Successfull !!!",authToken.getToken());


    }

    public int saveUser(User user) {
        User userObj = userRepository.save(user);
        return userObj.getUserId();
    }

    public JSONArray getUser(String userId) {

        JSONArray userArray = new JSONArray();

        if( null != userId && userRepository.findById(Integer.valueOf(userId)).isPresent()) {

            User user = userRepository.findById(Integer.valueOf(userId)).get();
            JSONObject userObj = setUser(user);
            userArray.put(userObj);
        } else {
            List<User> userList = userRepository.findAll();
            for (User user: userList) {
                JSONObject userObj = setUser(user);
                userArray.put(userObj);
            }
        }
        return userArray;
    }


    private JSONObject setUser (User user) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", user.getUserId());
        jsonObject.put("firstName", user.getFirstName());
        jsonObject.put("lastName", user.getLastName());
        jsonObject.put("age", user.getAge());
        jsonObject.put("email", user.getUserEmail());
        jsonObject.put("password", user.getUserPassword());
        jsonObject.put("phoneNumber", user.getPhoneNumber());

        return jsonObject;

    }

    public void updateUser(User newUser, String userId) {

        if(userRepository.findById(Integer.valueOf(userId)).isPresent()) {
            User user = userRepository.findById(Integer.valueOf(userId)).get();
            newUser.setUserId(user.getUserId());
            userRepository.save(newUser);
        }

    }
}
