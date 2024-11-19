package com.lms.controller;

import com.lms.model.Source;
import com.lms.model.User;
import com.lms.service.LemonadeStandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class LemonadeStandController {

    @Autowired
    LemonadeStandService lemonadeStandService;

    @RequestMapping(method = RequestMethod.POST, value = "/add/source",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, String>> getUser(@RequestBody Source source) {
        log.info("Attempting to POST /add/source");

        log.info("Calling user lms service");
        String returnMessage = lemonadeStandService.addSource(source);

        int status;

        if (returnMessage.isEmpty()) {
            status = 201;
            returnMessage = "Succesfully added source to net worth";
        } else {
            status = 400;
        }

        HashMap<String, String> returnJson = new HashMap<>();

        returnJson.put("message", returnMessage);

        return ResponseEntity.status(status).body(returnJson);
    }


}
