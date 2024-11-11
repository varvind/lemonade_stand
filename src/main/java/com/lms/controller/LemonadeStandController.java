package com.lms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class LemonadeStandController {
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/test")
    public ResponseEntity<Map<String, String>> helloWorld() {
        Map<String, String> returnJson = new HashMap<>();
        returnJson.put("message", "hello world");
        return ResponseEntity.status(201).body(returnJson);
    }
}
