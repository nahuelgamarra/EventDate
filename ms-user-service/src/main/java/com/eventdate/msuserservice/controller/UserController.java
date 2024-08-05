package com.eventdate.msuserservice.controller;

import com.eventdate.msuserservice.model.recors.LoginDto;
import com.eventdate.msuserservice.model.recors.UserDto;
import com.eventdate.msuserservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public Mono<ResponseEntity<Void>> register(@RequestBody @Valid UserDto userDto) {
        log.info("Create user: {}", userDto);
        return userService.registerUser(userDto)
                .then(Mono.fromCallable(() -> new ResponseEntity<>(HttpStatus.CREATED)));
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> loginUser(@RequestBody @Valid LoginDto loginDto) {
        log.info("Login user: {}", loginDto);
        return userService.login(loginDto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.status(401).build());
    }


}
