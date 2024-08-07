package com.eventdate.msreservationservice.model;

import com.eventdate.msreservationservice.model.enums.StatusOfReservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(value = "reservations")
public class Reservation implements Persistable<Long> {
    @Id
    private Long id;
    @Column(value = "user_id")
    private Long userId;
    @Column(value = "event_id")
    private Long eventId;
    @Column(value = "number_of_tickets")
    private Integer numberOfTickets;
    @Column(value = "reservation_date")
    private LocalDate reservationDate;
    @Column(value = "status")
    private StatusOfReservation status;
    @Transient
    private boolean newReservation;

    @Override
    public boolean isNew() {
        return newReservation;
    }
}
