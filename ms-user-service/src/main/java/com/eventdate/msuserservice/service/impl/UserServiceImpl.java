package com.eventdate.msuserservice.service.impl;

import com.eventdate.msuserservice.exception.UserAlreadyExistsException;
import com.eventdate.msuserservice.model.entity.User;
import com.eventdate.msuserservice.model.recors.LoginDto;
import com.eventdate.msuserservice.model.recors.UserDto;
import com.eventdate.msuserservice.repository.UserRepository;
import com.eventdate.msuserservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Mono<Void> registerUser(UserDto user) {
        log.info("Registering user: {}", user);

        return userRepository.findByEmail(user.email())
                .flatMap(existingUser -> {
                    return Mono.error(new UserAlreadyExistsException("Email already registered"));
                })
                .switchIfEmpty(Mono.defer(() -> {
                    User newUser = mapToUserEntity(user);
                    return userRepository.save(newUser)
                            .doOnSuccess(savedUser -> log.info("User registered successfully: {}", savedUser))
                            .then();
                })).then();
    }

    private User mapToUserEntity(UserDto user) {
        log.info("Mapping userDto to user entity: {}", user);
        return User.builder()
                .name(user.name())
                .lastName(user.lastName())
                .email(user.email())
                .password(user.password())
                .birthday(user.birthDate())
                .newUser(true)
                .role("USER")
                .build();
    }

    @Override
    public Mono<String> login(LoginDto login) {
        return null;
    }


}
