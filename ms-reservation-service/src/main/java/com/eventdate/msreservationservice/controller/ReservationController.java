package com.eventdate.msreservationservice.controller;

import com.eventdate.msreservationservice.model.Reservation;
import com.eventdate.msreservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/user")
    public Flux<Reservation> getReservationService() {
      log.info("getReservation");
        return reservationService.getReservationsByUserId(1L)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Reservation not found")));
    }
}
