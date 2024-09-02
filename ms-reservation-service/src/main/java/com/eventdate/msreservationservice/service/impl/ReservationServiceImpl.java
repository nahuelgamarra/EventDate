package com.eventdate.msreservationservice.service.impl;

import com.eventdate.msreservationservice.exception.ReservationNotFoundException;
import com.eventdate.msreservationservice.model.entity.Reservation;
import com.eventdate.msreservationservice.model.enums.StatusOfReservation;
import com.eventdate.msreservationservice.model.records.EventInfo;
import com.eventdate.msreservationservice.model.records.ReservationPending;
import com.eventdate.msreservationservice.model.records.ReservationRequest;
import com.eventdate.msreservationservice.model.records.TicketInfo;
import com.eventdate.msreservationservice.repository.ReservationRepository;
import com.eventdate.msreservationservice.service.ReservationService;
import com.eventdate.msreservationservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final WebClient.Builder webClientBuilder;
    private final JwtUtils jwtUtils;

    private final KafkaTemplate<String, ReservationPending> kafkaTemplate;


    @Override
    public Mono<Reservation> getById(Long id) {
        return reservationRepository.findById(id)
                .switchIfEmpty(Mono.error(new ReservationNotFoundException("Reservation not found")));
    }

    @Override
    public Mono<Reservation> create(ReservationRequest reservationRequest, String token) {
        return convertToEntity(reservationRequest, token)
                .flatMap(reservationEntity -> reservationRepository.save(reservationEntity)
                        .doOnSuccess(savedReservation -> {
                                    ReservationPending reservationPending = new ReservationPending(savedReservation.getId(),
                                            reservationRequest.eventId(),
                                            reservationRequest.numberOfTickets()
                                    );
                                    kafkaTemplate.send("event-reservation", reservationPending);
                                }
                        )
                );
    }

    private Mono<Reservation> convertToEntity(ReservationRequest request, String jwt) {
        return jwtUtils.getUserId(jwt)
                .map(userId -> Reservation.builder()
                        .newReservation(true)
                        .userId(userId)
                        .eventId(request.eventId())
                        .reservationDate(LocalDate.now())
                        .numberOfTickets(request.numberOfTickets())
                        .status(StatusOfReservation.PENDING)
                        .build());
    }

    @KafkaListener(topics = "reservation-confirmed", groupId = "reservation-confirmed")
    public void confirmReservation(Long reservationId) {
        getById(reservationId)
                .flatMap(reservation -> {
                    reservation.setStatus(StatusOfReservation.CONFIRMED);
                    return reservationRepository.save(reservation)
                            .doOnSuccess(savedReservation -> log.info("Reservation confirmed: {}", savedReservation))
                            .doOnError(error -> log.error("Error confirming reservation: ", error));
                })
                .subscribe();
    }

    @KafkaListener(topics = "reservation-rejected", groupId = "reservation-rejected")
    public void rejectReservation(Long reservationId) {
        getById(reservationId)
                .flatMap(reservation -> {
                    reservation.setStatus(StatusOfReservation.REJECTED);
                    return reservationRepository.save(reservation)
                            .doOnSuccess(savedReservation -> log.info("Reservation rejected: {}", savedReservation))
                            .doOnError(error -> log.error("Error confirming reservation: ", error));
                })
                .subscribe();
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
    public Flux<Reservation> getReservationsByUserId( String token) {
        return jwtUtils.getUserId(token)
                .flatMapMany(reservationRepository::findReservationByUserId)
                .onErrorResume(e -> {
                    log.error("Error fetching reservations", e);
                    return Flux.empty(); // Return an empty Flux in case of error
                });

    }

    @Override
    public Mono<TicketInfo> getTicketInfo(Long reservationId) {
        return getById(reservationId)
                .flatMap(reservation -> {
                    if (reservation.getStatus().equals(StatusOfReservation.CONFIRMED)) {
                        return webClientBuilder.build()
                                .get()
                                .uri("http://ms-event-catalog-service/api/v1/catalog/event?id={id}", reservation.getEventId())
                                .retrieve()
                                .bodyToMono(EventInfo.class)
                                .mapNotNull(eventInfo -> createTicketInfo(reservation, eventInfo));
                    }
                    return Mono.just(null);
                });
    }

    private TicketInfo createTicketInfo(Reservation reservation, EventInfo eventInfo) {
        return new TicketInfo(
                reservation.getId(),
                eventInfo.name(),
                eventInfo.eventDate(),
                eventInfo.startTime(),
                eventInfo.endTime(),
                reservation.getNumberOfTickets(),
                (eventInfo.price().multiply(BigDecimal.valueOf(reservation.getNumberOfTickets())))
        );
    }
}
