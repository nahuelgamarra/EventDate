package com.eventdate.msreservationservice.controller;

import com.eventdate.msreservationservice.model.entity.Reservation;
import com.eventdate.msreservationservice.model.records.ReservationRequest;
import com.eventdate.msreservationservice.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/user/{userId}")
    public Flux<Reservation> getAllReservationsByUser(@PathVariable Long userId) {
        log.info("getAllReservationsByUser userId={}", userId);
        return reservationService.getReservationsByUserId(userId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("No reservations found for user with ID " + userId)));
    }


    @PostMapping
    public Mono<ResponseEntity<Reservation>> createReservation(@RequestBody @Valid ReservationRequest reservation) {
        log.debug("createReservation {}", reservation);
        return reservationService.create(reservation)
                .map(reservationResponse -> new ResponseEntity<>(reservationResponse, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}

