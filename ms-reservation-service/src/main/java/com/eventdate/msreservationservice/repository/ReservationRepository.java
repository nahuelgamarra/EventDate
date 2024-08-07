package com.eventdate.msreservationservice.repository;

import com.eventdate.msreservationservice.model.Reservation;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;


public interface ReservationRepository extends ReactiveCrudRepository<Reservation, Long> {
    @Query("SELECT * FROM reservations WHERE user_id = :idUserId ")
    Flux<Reservation> findReservationByUserId(@Param("idUserId") Long idUserId);
}



