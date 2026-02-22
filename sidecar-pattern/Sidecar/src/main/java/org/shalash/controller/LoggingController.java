package org.shalash.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//in this case we talk to order service and it will talk to sidecar to log
@RestController
@RequestMapping("/log")
public class LoggingController {

    @PostMapping
    public String log(@RequestBody String message) {
        System.out.println("SIDE CAR LOG: " + message);
        return "Logged!";
    }
}