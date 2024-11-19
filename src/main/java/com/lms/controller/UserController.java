package com.lms.controller;

import com.lms.model.User;
import com.lms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {
    @Autowired
    UserService userService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/get/user")
    public ResponseEntity<Map<String, String>> getUser() {
        log.info("Attempting to GET /get/user");

        log.info("Calling user service");
        User user = userService.getUser();

        HashMap<String, String> returnJson = new HashMap<>();
        String message = "";
        int status;

        if (user == null) {
            log.info("Invalid User, or user not logged in");
            message = "User not logged in or invalid";
            status = 403;
        } else {
            log.info("User successfully fetched, returning object");
            message = "Succesfully pulled user";
            status = 200;
            returnJson.put("user", user.toString());
        }

        returnJson.put("message", message);

        return ResponseEntity.status(status).body(returnJson);
    }
}
