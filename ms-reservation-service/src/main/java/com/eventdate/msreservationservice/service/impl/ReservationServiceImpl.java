package com.eventdate.msreservationservice.service.impl;

import com.eventdate.msreservationservice.model.entity.Reservation;
import com.eventdate.msreservationservice.model.enums.StatusOfReservation;
import com.eventdate.msreservationservice.model.records.ReservationRequest;
import com.eventdate.msreservationservice.repository.ReservationRepository;
import com.eventdate.msreservationservice.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

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
    public Mono<Reservation> create(ReservationRequest reservation) {
        // TODO teniendo en cuenta que esta peticion viene del gateway y ya se sabe que es de un usuario verificado
        // TODO hay que verificar la disponibilidad de la cantidad de entradas
        // TODO que el eventId sea valido
        // TODO que no haya pasado la fecha y demas validaciones que se tienen que consultar a otro microservicio
        // TODO
        // TODO
        return reservationRepository.save(convertToEntity(reservation));
    }
    private Reservation convertToEntity(ReservationRequest request){
        return Reservation.builder()
                .newReservation(true)
                .userId(request.userId())
                .eventId(request.eventId())
                .reservationDate(LocalDate.now())
                .numberOfTickets(request.numberOfTickets())
                .status(StatusOfReservation.PENDING)
                .build();

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
