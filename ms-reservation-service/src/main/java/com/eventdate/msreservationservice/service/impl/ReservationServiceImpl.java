package com.eventdate.msreservationservice.service.impl;

import com.eventdate.msreservationservice.model.Reservation;
import com.eventdate.msreservationservice.repository.ReservationRepository;
import com.eventdate.msreservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    @Override
    public Mono<Reservation> getById(Long id) {
        return null;
    }

    @Override
    public Mono<Reservation> create(Reservation reservation) {
        return null;
    }

    @Override
    public Mono<Void> update(Reservation reservation) {
        return null;
    }

    @Override
    public Mono<Void> cancelReservation(Long id) {
        return null;
    }

    @Override
    public Flux<Reservation> getReservationsByUserId(Long idUserId) {
      log.info("search by user {}", idUserId);
        return reservationRepository.findReservationByUserId(idUserId);
    }
}
