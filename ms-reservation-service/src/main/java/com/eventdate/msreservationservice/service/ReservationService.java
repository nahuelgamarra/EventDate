package com.eventdate.msreservationservice.service;

import com.eventdate.msreservationservice.model.entity.Reservation;
import com.eventdate.msreservationservice.model.records.ReservationRequest;
import com.eventdate.msreservationservice.model.records.TicketInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReservationService {
    Mono<Reservation> getById(Long id);
    Mono<Reservation> create(ReservationRequest reservation, String token);
    Mono<Void> update(Reservation reservation);
    Mono<Void> cancelReservation(Long id);
    Flux<Reservation> getReservationsByUserId(String token);
    Mono<TicketInfo> getTicketInfo(Long reservationId);
}
