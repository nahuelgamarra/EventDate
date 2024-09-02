package com.eventdate.catalog.model.dto;

public record ReservationPending(Long reservationId,
                                 Long eventId,
                                 int numberOfTickets
                                 ) {
}
