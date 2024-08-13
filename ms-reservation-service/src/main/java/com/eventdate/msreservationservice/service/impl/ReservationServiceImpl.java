package com.eventdate.msreservationservice.service.impl;

import com.eventdate.msreservationservice.model.entity.Reservation;
import com.eventdate.msreservationservice.model.enums.StatusOfReservation;
import com.eventdate.msreservationservice.model.records.ReservationRequest;
import com.eventdate.msreservationservice.repository.ReservationRepository;
import com.eventdate.msreservationservice.service.ReservationService;
import com.eventdate.msreservationservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final WebClient.Builder webClientBuilder;
    private final JwtUtils jwtUtils;

    @Override
    public Mono<Reservation> getById(Long id) {
        return null;
    }

    @Override
    public Mono<Reservation> create(ReservationRequest reservation, String token) {
        // TODO teniendo en cuenta que esta peticion viene del gateway y ya se sabe que es de un usuario verificado
        // TODO hay que verificar la disponibilidad de la cantidad de entradas
        // TODO que el eventId sea valido
        // TODO que no haya pasado la fecha y demas validaciones que se tienen que consultar a otro microservicio
        // TODO
        // TODO
        //return reservationRepository.save(convertToEntity(reservation));
//        return checkEventAvailability(reservation.eventId(), reservation.numberOfTickets(), token)
//                .flatMap(isAvailable -> {
//                    if (!isAvailable) {
//                        return Mono.error(new RuntimeException("Not available"));
//                    }
//                    return reservationRepository.save(convertToEntity(reservation, token));
//                });


        return checkEventAvailability(reservation.eventId(), reservation.numberOfTickets(), token)
                .flatMap(isAvailable -> {
                    if (!isAvailable) {
                        return Mono.error(new RuntimeException("Not available"));
                    }
                    return convertToEntity(reservation, token) // Este método ahora devuelve un Mono<Reservation>
                            .flatMap(reservationRepository::save);
                });
    }


    private Mono<Boolean> checkEventAvailability(Long eventId, int numberOfTickets, String token) {

        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8080/api/v1/tickets/events/{eventId}/buy?numberOfTickets={numberOfTickets}", eventId, numberOfTickets)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(e -> {
                    log.error("Error checking availability for event {}, {}", eventId, e.getMessage());
                    return Mono.just(false);
                });
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
