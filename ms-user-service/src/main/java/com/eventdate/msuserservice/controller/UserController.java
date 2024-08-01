package com.eventdate.msuserservice.controller;

import com.eventdate.msuserservice.model.recors.LoginDto;
import com.eventdate.msuserservice.model.recors.UserDto;
import com.eventdate.msuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Mono<Void>> createUser(@RequestBody UserDto userDto) {
        log.info("Create user: {}", userDto);
        return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Mono<String>> loginUser(@RequestBody LoginDto loginDto) {
        log.info("Login user: {}", loginDto);

        return new ResponseEntity<>(userService.login(loginDto), HttpStatus.OK);
    }
}
