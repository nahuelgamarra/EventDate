package com.eventdate.msuserservice.service;

import com.eventdate.msuserservice.model.recors.LoginDto;
import com.eventdate.msuserservice.model.recors.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<Void> registerUser(UserDto user);
    Mono<String> login(LoginDto login);
}
