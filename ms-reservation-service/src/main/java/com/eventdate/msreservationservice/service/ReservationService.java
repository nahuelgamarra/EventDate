package com.eventdate.msreservationservice.service;

import com.eventdate.msreservationservice.model.Reservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReservationService {
    Mono<Reservation> getById(Long id);
    Mono<Reservation> create(Reservation reservation);
    Mono<Void> update(Reservation reservation);
    Mono<Void> cancelReservation(Long id);
    Flux<Reservation> getReservationsByUserId(Long idUserId);
}
