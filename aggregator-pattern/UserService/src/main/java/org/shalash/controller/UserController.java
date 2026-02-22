package org.shalash.controller;

import lombok.RequiredArgsConstructor;
import org.shalash.object.User;
import org.shalash.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User hello(@PathVariable String id) {
        return userService.getUser(id);
    }
}
